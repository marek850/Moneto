package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneto.data.Currency
import com.example.moneto.data.Transaction
import com.example.moneto.view_models.TransactionsBaseViewModel

@Composable
fun TransactionList(
    transactions: List<Transaction>,
    currency: Currency?,
    viewModel: TransactionsBaseViewModel
) {
    Column {
        if (transactions.isEmpty()) {
            Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
        } else {
            transactions.forEach { transaction ->
                TransactionElement(transaction = transaction, currency, viewModel)
            }
        }
    }
}