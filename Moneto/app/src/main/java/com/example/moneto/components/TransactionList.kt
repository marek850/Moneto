package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneto.data.Expense

@Composable
fun TransactionList(expenses: List<Expense>) {


    Column() {
        if (expenses.isEmpty()) {
            Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
        } else {
            expenses.forEach { expense ->
                TransactionElement(
                    expense = expense
                )
            }
        }
    }
}