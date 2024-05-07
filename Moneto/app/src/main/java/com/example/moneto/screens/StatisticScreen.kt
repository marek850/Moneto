package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneto.charts.DayChart
import com.example.moneto.charts.MonthChart
import com.example.moneto.charts.WeekChart
import com.example.moneto.charts.YearChart
import com.example.moneto.components.Picker
import com.example.moneto.components.TransactionList
import com.example.moneto.data.TimeRange
import com.example.moneto.data.TransactionType
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography
import com.example.moneto.view_models.StatisticsViewModel
import java.time.LocalDate
/**
 * Composable funkcia, ktorá zobrazuje obrazovku štatistík v aplikácii Moneto.
 *  * Táto obrazovka obsahuje grafy reprezentujúce údaje o transakciách za rôzne časové rozsahy a umožňuje používateľovi filtrovať transakcie podľa typu a časového rozsahu.
 *  *
 *  * @param statisticsViewModel ViewModel, ktorý poskytuje stav a správanie pre obrazovku štatistík.
 *  * Tento ViewModel spravuje načítanie a aktualizáciu údajov o transakciách na základe interakcií s používateľom.
 */
@Composable
fun StatisticScreen(statisticsViewModel: StatisticsViewModel = viewModel()) {
    val state by statisticsViewModel.state.collectAsState()
    var timeRangeOpened by remember {
        mutableStateOf(false)
    }
    var transactionTypesOpened by remember {
        mutableStateOf(false)
    }
    val transactionTypes = listOf(TransactionType.All, TransactionType.Expense,  TransactionType.Income)
    val timeRanges = listOf(TimeRange.Day, TimeRange.Week, TimeRange.Month, TimeRange.Year)
    Scaffold(modifier = Modifier.fillMaxHeight(),content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(260.dp)
                    .padding(vertical = 16.dp)
            ){
                when (state.timeRange) {
                    TimeRange.Day -> DayChart(state.transactions)
                    TimeRange.Week -> WeekChart(state.transactions)
                    TimeRange.Month -> MonthChart(state.transactions, LocalDate.now())
                    TimeRange.Year -> YearChart(state.transactions)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Filter transactions: ",
                    style = Typography.titleSmall, color = Purple80
                )
                Picker(state.typeOfTransaction.name) { transactionTypesOpened = !transactionTypesOpened }
                DropdownMenu(expanded = transactionTypesOpened,
                    onDismissRequest = { transactionTypesOpened = false }) {
                    transactionTypes.forEach { transactionType ->
                        DropdownMenuItem(text = { Text(transactionType.name) }, onClick = {
                            statisticsViewModel.updateTimeRange(state.timeRange, transactionType )
                            transactionTypesOpened = false
                        })
                    }
                }
                Text(
                    "For: ",
                    style = Typography.titleSmall, color = Purple80
                )
                Picker(state.timeRange.name) { timeRangeOpened = !timeRangeOpened }
                DropdownMenu(expanded = timeRangeOpened,
                    onDismissRequest = { timeRangeOpened = false }) {
                    timeRanges.forEach { timeRange ->
                        DropdownMenuItem(text = { Text(timeRange.name) }, onClick = {
                            statisticsViewModel.updateTimeRange(timeRange, state.typeOfTransaction)
                            timeRangeOpened = false
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box {
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    TransactionList(state.transactions, state.currency, statisticsViewModel)
                }
            }
        }
    })
}