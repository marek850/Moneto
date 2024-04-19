package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneto.data.Transaction
import com.example.moneto.data.groupedByMonth

@Composable
fun TransactionList(transactions: List<Transaction>) {
    val groupedTransactions = transactions.groupedByMonth()

    Column() {
        if (groupedTransactions.isEmpty()) {
            Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
        } else {
            groupedTransactions.keys.forEach { date ->
                if (groupedTransactions[date] != null) {
                    DayGroupedTransactions(oneDayTransactions = groupedTransactions[date]!!
                    )
                }
            }
        }
    }

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