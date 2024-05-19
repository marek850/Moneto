package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneto.charts.TransactionChart
import com.example.moneto.components.Picker
import com.example.moneto.components.TransactionList
import com.example.moneto.data.TimeRange
import com.example.moneto.data.TransactionType
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography
import com.example.moneto.view_models.HomeViewModel
/**
 * Composable funkcia, ktorá slúži ako hlavný panel aplikácie Moneto. Táto obrazovka zobrazuje
 * finančné súhrny, ako sú príjmy, výdavky a celkový zostatok, a umožňuje používateľom filtrovať
 * transakcie podľa typu a časového rozsahu. Okrem toho poskytuje dynamické grafické znázornenie
 * transakcií a zoznam detailov o transakciách.
 *
 * @param homeViewModel ViewModel, ktorý riadi a poskytuje stav pre domovskú obrazovku,
 * vrátane údajov o transakciách a preferencií používateľov pre zobrazenie konkrétnych typov transakcií alebo
 * časových rozsahov.
 */
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val state by homeViewModel.state.collectAsState()
    var timeRangeOpened by remember {
        mutableStateOf(false)
    }
    var transactionTypesOpened by remember {
        mutableStateOf(false)
    }
    val incomes = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Purple80)) {
            append("Income: ")
        }
        withStyle(style = SpanStyle(color = Color.Green)) {
            append("${String.format("%.2f", state.incomeValue)}${state.currency?.symbol}")
        }
    }
    val expenses = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Purple80)) {
            append("Expenses: ")
        }
        withStyle(style = SpanStyle(color = Color.Red)) {
            append("-${String.format("%.2f", state.expensesValue)}${state.currency?.symbol}")
        }
    }
    val totalBalance = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Purple80)) {
            append("Total balance: ")
        }
        if (state.totalSum >= 0){
            withStyle(style = SpanStyle(color = Color.Green)) {
                append("${String.format("%.2f", state.totalSum)}${state.currency?.symbol}")
            }
        } else {
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("${String.format("%.2f", state.totalSum)}${state.currency?.symbol}")
            }
        }

    }
    val transactionTypes = listOf(TransactionType.All, TransactionType.Expense,  TransactionType.Income)
    val timeRanges = listOf(TimeRange.Day, TimeRange.Week, TimeRange.Month, TimeRange.Year)
    Scaffold(modifier = Modifier.fillMaxHeight(),content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(LightBackground)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = incomes)
                        }
                        Spacer(Modifier.weight(1f))
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = expenses)
                        }
                    }
                    Row(modifier = Modifier
                        .size(200.dp)
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .align(alignment = Alignment.CenterHorizontally)) {
                        TransactionChart(state.expensesValue, state.incomeValue)
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.Center) {
                        Text(text = totalBalance)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Transaction Type: ",
                    style = Typography.titleSmall, color = Purple80
                )
                Picker(state.typeOfTransaction.name) { transactionTypesOpened = !transactionTypesOpened }
                DropdownMenu(expanded = transactionTypesOpened,
                    onDismissRequest = { transactionTypesOpened = false }) {
                    transactionTypes.forEach { transactionType ->
                        DropdownMenuItem(text = { Text(transactionType.name) }, onClick = {
                            homeViewModel.updateTimeRange(state.timeRange, transactionType  )
                            transactionTypesOpened = false
                        })
                    }
                }
                Text(
                    "For:  ",
                    style = Typography.titleSmall, color = Purple80
                )
                Picker(state.timeRange.name) { timeRangeOpened = !timeRangeOpened }
                DropdownMenu(expanded = timeRangeOpened,
                    onDismissRequest = { timeRangeOpened = false }) {
                    timeRanges.forEach { timeRange ->
                        DropdownMenuItem(text = { Text(timeRange.name) }, onClick = {
                            homeViewModel.updateTimeRange(timeRange, state.typeOfTransaction)
                            timeRangeOpened = false
                        })
                    }
                }

            }
            Spacer(modifier = Modifier.height(5.dp))
            Column {
               TransactionList(state.transactions, state.currency, homeViewModel)
            }

        }
    })
}
