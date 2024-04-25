package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneto.data.Currency
import com.example.moneto.data.OneDaySumTransactions
import com.example.moneto.data.TimeRange
import com.example.moneto.data.Transaction
import com.example.moneto.view_models.TransactionsBaseViewModel
import java.time.LocalDate

@Composable
fun TransactionList(transactions: List<Transaction>, timeRange: TimeRange, currency: Currency?,viewModel: TransactionsBaseViewModel) {
    val groupedYearTransactions: Map<String, OneDaySumTransactions>
    val groupedWeekTransactions: Map<LocalDate, OneDaySumTransactions>
    val groupedMonthTransactions: Map<Int, OneDaySumTransactions>
    Column {
        if (transactions.isEmpty()) {
            Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
        } else {
            transactions.forEach { transaction ->
                TransactionElement(transaction = transaction, currency, viewModel)
            }
        }
    }
    /*when(timeRange) {
        TimeRange.Day -> {

        }
        TimeRange.Week -> {
            groupedWeekTransactions = transactions.groupedByDay()
            Column {
                if (groupedWeekTransactions.isEmpty()) {
                    Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
                } else {
                    groupedWeekTransactions.keys.forEach { date ->
                        if (groupedWeekTransactions[date] != null) {
                            DayGroupedTransactions(oneDayTransactions = groupedWeekTransactions[date]!!, groupedWeekTransactions[date]!!.transactions[0].date
                            )
                        }
                    }
                }
            }
        }
        TimeRange.Month -> {
            groupedMonthTransactions = transactions.groupedByDayOfMonth()
            Column {
                if (groupedMonthTransactions.isEmpty()) {
                    Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
                } else {
                    groupedMonthTransactions.keys.forEach { date ->
                        if (groupedMonthTransactions[date] != null) {
                            DayGroupedTransactions(oneDayTransactions = groupedMonthTransactions[date]!!,groupedMonthTransactions[date]!!.transactions[0].date
                            )
                        }
                    }
                }
            }
        }
        TimeRange.Year -> {
            groupedYearTransactions = transactions.groupedByMonth()
            Column {
                if (groupedYearTransactions.isEmpty()) {
                    Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
                } else {
                    groupedYearTransactions.keys.forEach { date ->
                        if (groupedYearTransactions[date] != null) {
                            DayGroupedTransactions(oneDayTransactions = groupedYearTransactions[date]!!,groupedYearTransactions[date]!!.transactions[0].date
                            )
                        }
                    }
                }
            }
        }
    }*/




    /*Column() {
        if (expenses.isEmpty()) {
            Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
        } else {
            expenses.forEach { expense ->
                TransactionElement(
                    transaction = expense
                )
            }
        }
    }*/
}