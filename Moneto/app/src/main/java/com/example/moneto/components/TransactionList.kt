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
/**
 * Composable funkcia, ktorá zobrazuje zoznam transakcií v aplikácii Moneto.
 * Táto funkcia zobrazuje jednotlivé transakcie pomocou komponenty `TransactionElement` a poskytuje kontext o použitej mene.
 * Ak zoznam transakcií neobsahuje žiadne položky, zobrazí sa správa o absencii dát.
 *
 * @param transactions Zoznam transakcií na zobrazenie.
 * @param currency Mena, ktorá sa použije pre zobrazenie čiastok transakcií.
 * @param viewModel ViewModel, ktorý poskytuje základné operácie a správu pre transakcie.
 */
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