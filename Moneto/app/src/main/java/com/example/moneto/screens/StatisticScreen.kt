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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StatisticScreen(navController: NavController, statisticsViewModel: StatisticsViewModel = viewModel()) {
    val state by statisticsViewModel.state.collectAsState()
    val pageScrollState = rememberScrollState()
    var timeRangeOpened by remember {
        mutableStateOf(false)
    }
    val timeRanges = listOf(TimeRange.Day, TimeRange.Week, TimeRange.Month, TimeRange.Year)
    val transactionListScrollState = rememberScrollState()
    Scaffold(modifier = Modifier.fillMaxHeight(),content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxHeight() // Ensure the Column takes up all available height
                .verticalScroll(rememberScrollState()), // Add scrolling
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(260.dp)
                    .padding(vertical = 16.dp)
                    //.verticalScroll(rememberScrollState())
            ){
                when (state.timeRange) {
                    TimeRange.Day -> DayChart()
                    TimeRange.Week -> WeekChart()
                    TimeRange.Month -> MonthChart(month = LocalDate.now())
                    TimeRange.Year -> YearChart()
                    else -> Unit
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Total for: ",
                    style = Typography.titleLarge, color = Purple80
                )
                Picker(state.timeRange.name, { timeRangeOpened = !timeRangeOpened })
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
            Spacer(modifier = Modifier.height(5.dp)) // Add space between chart and button
            Box(/*modifier = Modifier.verticalScroll(rememberScrollState())*/) {
                Column(
                    modifier = Modifier/*.verticalScroll(transactionListScrollState)*/.fillMaxHeight()
                ) {
                    TransactionList(transactions = state.transactions)
                }
            }
        }
        /*Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                ,//.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

                WeekChart()
            //BarChart()
            *//*Box(modifier = Modifier
                //.fillMaxSize()
                .background(Background).padding(10.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    WeekChart()
                }

            }
            Button(
                onClick = {},
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightPurple
                )
            ) {
                Text("Add expense", color = Purple80)
            }
*//*
        }
        Button(
            onClick = {},
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightPurple
            )
        ) {
            Text("Add expense", color = Purple80)
        }*/
    })



}