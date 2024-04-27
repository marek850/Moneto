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
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography
import com.example.moneto.view_models.StatisticsViewModel
import java.time.LocalDate

@Composable
fun StatisticScreen(statisticsViewModel: StatisticsViewModel = viewModel()) {
    val state by statisticsViewModel.state.collectAsState()
    var timeRangeOpened by remember {
        mutableStateOf(false)
    }
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
                    "Total for: ",
                    style = Typography.titleLarge, color = Purple80
                )
                Picker(state.timeRange.name) { timeRangeOpened = !timeRangeOpened }
                DropdownMenu(expanded = timeRangeOpened,
                    onDismissRequest = { timeRangeOpened = false }) {
                    timeRanges.forEach { timeRange ->
                        DropdownMenuItem(text = { Text(timeRange.name) }, onClick = {
                            statisticsViewModel.updateTimeRange(timeRange)
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