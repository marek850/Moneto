package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.data.OneDaySumTransactions

@Composable
@Preview
fun DayGroupedTransactions(
    /*date: LocalDate,*/
    oneDayTransactions: OneDaySumTransactions,
) {
    Column() {

        Divider(modifier = Modifier.padding(top = 10.dp, bottom = 4.dp))
        oneDayTransactions.transactions.forEach { transaction ->
            TransactionElement(
                transaction = transaction,
            )
        }
    }
}