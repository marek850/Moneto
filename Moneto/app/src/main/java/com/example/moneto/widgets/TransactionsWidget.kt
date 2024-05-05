
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.moneto.data.Category
import com.example.moneto.data.Currency
import com.example.moneto.data.Limit
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Purple80
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

val config = RealmConfiguration.create(schema = setOf(Category::class, Transaction::class, Limit::class, Currency::class))
class TransactionsWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val monetoDb: Realm = Realm.open(config)
        // Initialize totals
        var totalIncome = 0.0
        var totalExpenses = 0.0

        // Get the current date for filtering
        val currentDate = LocalDate.now()
        val currentMonth = currentDate.monthValue
        val currentYear = currentDate.year

        // Fetch transactions and calculate totals
        val transactions = withContext(Dispatchers.IO) {
            monetoDb.query<Transaction>().find().filter { transaction ->
                transaction.date.monthValue == currentMonth &&
                        transaction.date.year == currentYear
            }
        }

        // Process transactions based on type
        transactions.forEach { transaction ->
            if (transaction.type == TransactionType.Income) {
                totalIncome += transaction.amount
            } else if (transaction.type == TransactionType.Expense) {
                totalExpenses += transaction.amount
            }
        }

        // Close the Realm instance
        monetoDb.close()

        provideContent {
            Column(
                modifier = GlanceModifier
                    .background(Background)
                    .fillMaxSize()
            ) {
                Text(
                    text = "This month: ",
                    style = TextStyle(
                        color = ColorProvider(Purple80),
                        fontSize = 18.sp
                    )
                )
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Income: ",
                        style = TextStyle(
                            color = ColorProvider(Purple80),
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = "${String.format("%.2f", totalIncome)}$",
                        style = TextStyle(
                            color = ColorProvider(Color.Green),
                            fontSize = 18.sp
                        )
                    )
                }
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Expenses: ",
                        style = TextStyle(
                            color = ColorProvider(Purple80),
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = "${String.format("%.2f", totalExpenses)}$",
                        style = TextStyle(
                            color = ColorProvider(Color.Red),
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }

}

class TransactionsWidgetReceiver : GlanceAppWidgetReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    override val glanceAppWidget: GlanceAppWidget
        get() = TransactionsWidget()

}