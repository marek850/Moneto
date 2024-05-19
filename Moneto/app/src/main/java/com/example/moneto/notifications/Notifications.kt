package com.example.moneto.notifications

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.moneto.MainActivity
import com.example.moneto.R
import com.example.moneto.data.Limit
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.ceil
/**
 * Worker pre kontrolu transakcií, ktorý kontroluje transakcie vykonané v aktuálny deň a posiela notifikácie, ak nie sú zaznamenané žiadne transakcie.
 *
 * @param appContext Kontext aplikácie.
 * @param workerParams Parametre pracovníka.
 */
class TransactionCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    /**
     * Vykoná kontrolu transakcií a pošle notifikáciu, ak neexistujú žiadne transakcie v daný deň.
     * @return Výsledok práce, úspech ak bol proces dokončený bez problémov.
     */
    override suspend fun doWork(): Result {
        val today = LocalDate.now()
        val transactions = monetoDb.query<Transaction>()
            .find()
            .filter { it.date.toLocalDate().isEqual(today) }

        if (transactions.isEmpty()) {
            sendNotification(applicationContext)
        }

        return Result.success()
    }
    /**
     * Posiela notifikáciu upozorňujúcu na neprítomnosť transakcií.
     * @param context Kontext, v ktorom sa notifikácia posiela.
     */
    private fun sendNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.moneyicon)
            .setContentTitle("Add your transactions")
            .setContentText("You still haven't added any transactions? Add your transactions now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(context).notify(1234, builder.build())
    }
}

/**
 * Plánuje kontrolné úlohy na overenie transakcií, dennej a mesačnej kontroly limitu.
 * @param applicationContext Kontext aplikácie pre správu plánovania úloh.
 */
fun scheduleChecks(applicationContext: Context) {
    val workManager = WorkManager.getInstance(applicationContext)
    // Rôzne požiadavky na prácu s nastavením oneskorenia podľa času
    val noonRequest = PeriodicWorkRequestBuilder<TransactionCheckWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(getInitialDelay(12, 0), TimeUnit.SECONDS)
        .addTag("transactionCheckNoon")
        .build()

    val eveningRequest = PeriodicWorkRequestBuilder<TransactionCheckWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(getInitialDelay(17, 0), TimeUnit.SECONDS)
        .addTag("transactionCheckEvening")
        .build()

    val dailyLimitCheckRequest = PeriodicWorkRequestBuilder<DailyLimitCheckWorker>(3, TimeUnit.HOURS)
        .setInitialDelay(getInitialDelay(12, 0), TimeUnit.SECONDS)
        .addTag("dailyLimitCheck")
        .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
        .build()

    val monthlyLimitCheckRequest = PeriodicWorkRequestBuilder<MonthlyLimitCheckWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(getInitialDelay(12, 0), TimeUnit.SECONDS)
        .addTag("monthlyLimitCheck")
        .build()
    // Plánovanie jednotlivých úloh
    workManager.enqueueUniquePeriodicWork("dailyLimitCheck", ExistingPeriodicWorkPolicy.REPLACE, dailyLimitCheckRequest)
    workManager.enqueueUniquePeriodicWork("noonCheck", ExistingPeriodicWorkPolicy.REPLACE, noonRequest)
    workManager.enqueueUniquePeriodicWork("eveningCheck", ExistingPeriodicWorkPolicy.REPLACE, eveningRequest)
    workManager.enqueueUniquePeriodicWork("monthlyLimitCheck", ExistingPeriodicWorkPolicy.REPLACE, monthlyLimitCheckRequest)
}

/**
 * Pomocná funkcia na výpočet počiatočného oneskorenia úlohy založené na cieľovej hodine a minúte.
 * @param targetHour Cieľová hodina pre spustenie úlohy.
 * @param targetMinute Cieľová minúta pre spustenie úlohy.
 * @return Počet sekúnd do spustenia úlohy.
 */
private fun getInitialDelay(targetHour: Int, targetMinute: Int): Long {
    val currentTime = LocalTime.now()
    val targetTime = LocalTime.of(targetHour, targetMinute)
    return Duration.between(currentTime, targetTime).seconds.coerceAtLeast(0)
}

/**
 * Worker pre dennú kontrolu limitu výdavkov. Overuje, či denné výdavky neprekračujú nastavený limit.
 *
 * @param appContext Kontext aplikácie.
 * @param workerParams Parametre pracovníka.
 */
class DailyLimitCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    /**
     * Vykonáva dennú kontrolu limitu výdavkov a podľa výsledkov posiela notifikácie.
     * @return Výsledok práce, úspech ak bola úloha spracovaná bez problémov.
     */
    override suspend fun doWork(): Result {
        val today = LocalDate.now()
        val limits = monetoDb.query<Limit>().find()
        val dailyLimit : Double
        if (!limits.isEmpty()) {
            dailyLimit = limits[0].dailyLimit

            if (dailyLimit > 0) {
                val totalExpenses = monetoDb.query<Transaction>()
                    .find()
                    .filter { it.type == TransactionType.Expense && it.date.toLocalDate().isEqual(today) }
                    .sumOf { abs(it.amount) }

                if (totalExpenses < dailyLimit) {
                    sendEncouragementNotification(applicationContext)
                }
                if (totalExpenses > dailyLimit){
                    sendReachedDailyLimitNotification(applicationContext)
                }
            }
        }

        return Result.success()
    }
    /**
     * Posiela notifikáciu o dosiahnutí denného limitu výdavkov.
     * @param context Kontext, v ktorom sa notifikácia posiela.
     */
    private fun sendReachedDailyLimitNotification(context: Context) {

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.moneyicon)
            .setContentTitle("Daily limit reached")
            .setContentText("You've reached your daily spending limit")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(context).notify(1235, builder.build())
    }
    /**
     * Posiela povzbudzujúcu notifikáciu, ak výdavky zostávajú pod denným limitom.
     * @param context Kontext, v ktorom sa notifikácia posiela.
     */
    private fun sendEncouragementNotification(context: Context) {

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.moneyicon)
            .setContentTitle("Great Job!")
            .setContentText("You are doing great with your spending. Keep it up!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(context).notify(1235, builder.build())
    }
}
/**
 * Worker pre mesačnú kontrolu limitu výdavkov. Overuje, či mesačné výdavky neprekračujú nastavený limit.
 *
 * @param appContext Kontext aplikácie.
 * @param workerParams Parametre pracovníka.
 */
class MonthlyLimitCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    /**
     * Vykonáva mesačnú kontrolu limitu výdavkov a posiela relevantné notifikácie v závislosti od stavu výdavkov.
     * @return Výsledok práce, úspech ak bola úloha spracovaná bez problémov.
     */
    override suspend fun doWork(): Result {
        val today = LocalDate.now()
        val limits = monetoDb.query<Limit>().find()
        val monthlyLimit : Double
        val monthEnd = today.withDayOfMonth(today.lengthOfMonth())
        val monthStart = today.withDayOfMonth(1)
        if (!limits.isEmpty()) {
            monthlyLimit = limits[0].monthlyLimit

            if (monthlyLimit > 0) {
                val totalExpenses = monetoDb.query<Transaction>()
                    .find()
                    .filter { it.type == TransactionType.Expense && it.date.toLocalDate().isAfter(monthStart) && it.date.toLocalDate().isBefore(monthEnd) }
                    .sumOf { abs(it.amount) }

                if (monthlyLimit - totalExpenses >= monthlyLimit / 2 && ceil(monthEnd.dayOfMonth.toDouble() / 2.0) == today.dayOfMonth.toDouble()) {
                    sendHalfMarkNotification(applicationContext)
                }
                if (totalExpenses < monthlyLimit && monthEnd.dayOfMonth - today.dayOfMonth == 3) {
                    sendEndOfMonthNotification(applicationContext)
                }
                if (totalExpenses >= monthlyLimit) {
                    sendReachedMonthlyLimitNotification(applicationContext)
                }
            }
        }

        return Result.success()
    }
    /**
     * Posiela notifikáciu o polovici mesiaca, ak výdavky vyzerajú dobre.
     * @param context Kontext, v ktorom sa notifikácia posiela.
     */
    private fun sendHalfMarkNotification(context: Context) {

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.moneyicon)
            .setContentTitle("Half of the month")
            .setContentText("You're past the half and your expenses are looking good. Keep it up!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(context).notify(1235, builder.build())
    }
    /**
     * Posiela notifikáciu o dosiahnutí mesačného limitu výdavkov.
     * @param context Kontext, v ktorom sa notifikácia posiela.
     */
    private fun sendReachedMonthlyLimitNotification(context: Context) {

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.moneyicon)
            .setContentTitle("Monthly limit reached")
            .setContentText("You've reached your monthly spending limit")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(context).notify(1235, builder.build())
    }
    /**
     * Posiela notifikáciu o blížiacom sa konci mesiaca, ak výdavky ešte nedosiahli limit.
     * @param context Kontext, v ktorom sa notifikácia posiela.
     */
    private fun sendEndOfMonthNotification(context: Context) {

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.moneyicon)
            .setContentTitle("3 days left")
            .setContentText("Only 3 days left and expenses haven't reached the limit. You can do it! Keep up the good work")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        NotificationManagerCompat.from(context).notify(1235, builder.build())
    }
}