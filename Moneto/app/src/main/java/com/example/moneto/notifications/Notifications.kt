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
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
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

class TransactionCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

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

// Scheduling method to enqueue transaction checks
fun scheduleChecks(applicationContext: Context) {
    val workManager = WorkManager.getInstance(applicationContext)

    val noonRequest = OneTimeWorkRequestBuilder<TransactionCheckWorker>()
        .setInitialDelay(getInitialDelay(12, 0), TimeUnit.SECONDS)
        .addTag("transactionCheckNoon")
        .build()

    val eveningRequest = OneTimeWorkRequestBuilder<TransactionCheckWorker>()
        .setInitialDelay(getInitialDelay(17, 5), TimeUnit.SECONDS)
        .addTag("transactionCheckEvening")
        .build()

    val dailyLimitCheckRequest = PeriodicWorkRequestBuilder<DailyLimitCheckWorker>(3, TimeUnit.HOURS)
        .setInitialDelay(getInitialDelay(12, 0), TimeUnit.SECONDS)
        .addTag("dailyLimitCheck")
        .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
        .build()

    val monthlyLimitCheckRequest = OneTimeWorkRequestBuilder<MonthlyLimitCheckWorker>()
        .setInitialDelay(getInitialDelay(12, 0), TimeUnit.SECONDS)
        .addTag("monthlyLimitCheck")
        .build()

    workManager.enqueueUniquePeriodicWork("dailyLimitCheck", ExistingPeriodicWorkPolicy.REPLACE, dailyLimitCheckRequest)
    workManager.enqueueUniqueWork("noonCheck", ExistingWorkPolicy.REPLACE, noonRequest)
    workManager.enqueueUniqueWork("eveningCheck", ExistingWorkPolicy.REPLACE, eveningRequest)
    workManager.enqueueUniqueWork("monthlyLimitCheck", ExistingWorkPolicy.REPLACE, monthlyLimitCheckRequest)
}

// Helper function to calculate the initial delay for the work request based on target hour and minute
private fun getInitialDelay(targetHour: Int, targetMinute: Int): Long {
    val currentTime = LocalTime.now()
    val targetTime = LocalTime.of(targetHour, targetMinute)
    return Duration.between(currentTime, targetTime).seconds.coerceAtLeast(0)
}


class DailyLimitCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

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

class MonthlyLimitCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

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