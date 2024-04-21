package com.example.moneto.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import java.text.DecimalFormat

@Composable
@Preview
fun TransactionElement(transaction: Transaction) {
    Card(
        shape = RoundedCornerShape(12.dp), // Adjust corner shape as needed
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
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = transaction.description ?:transaction.category!!.name, color = Purple80)
                    //Text(text = transaction.date.toString(), color = Purple80)
                }
                Spacer(Modifier.weight(1f))
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "USD ${ if(transaction.type == TransactionType.Expense){
                        DecimalFormat("-0.#").format(transaction.amount)
                    }
                            else { DecimalFormat("0.#").format(transaction.amount)}}", color = if (transaction.type == TransactionType.Expense){
                                Color.Red
                    } else { Color.Green}
                        
                       )
                }
            }
           /* Column(
                modifier = Modifier.weight(1f) // This makes the column take up all available space except for the image
            ) {
                Text(
                    text = expense.description ?:expense.category!!.name,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "USD ${DecimalFormat("0.#").format(expense.amount)}",
                )
            }*/
        }
    }

    /*Column() {
        Row() {
            Text(expense.category!!.name)
            Text(
                "USD ${DecimalFormat("0.#").format(expense.amount)}",
            )
        }
    }*/
}