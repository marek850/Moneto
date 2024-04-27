package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moneto.data.Currency
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.view_models.HomeViewModel
import com.example.moneto.view_models.StatisticsViewModel
import com.example.moneto.view_models.TransactionsBaseViewModel
import java.text.DecimalFormat

@Composable
fun TransactionElement(transaction: Transaction, currency: Currency?,viewModel: TransactionsBaseViewModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightPurple
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(5.dp)) {
                    IconButton(
                        onClick = {
                            if (viewModel is HomeViewModel) {
                                viewModel.removeTransaction(transaction)
                            } else if (viewModel is StatisticsViewModel) {
                                viewModel.removeTransaction(transaction)
                            } },
                        modifier = Modifier.size(30.dp).padding(0.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Delete,
                            tint = Purple80,
                            contentDescription = "Delete transaction",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(Modifier.weight(0.1f))
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = transaction.description, color = Purple80)
                }
                Spacer(Modifier.weight(1f))
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "${currency?.code ?: "USD"} ${ if(transaction.type == TransactionType.Expense){
                        DecimalFormat("-0.#").format(transaction.amount)
                    }
                            else { DecimalFormat("0.#").format(transaction.amount)}} ${currency?.symbol ?: "$"}", color = if (transaction.type == TransactionType.Expense){
                                Color.Red
                    } else { Color.Green}
                        
                       )
                }
            }
        }
    }
}