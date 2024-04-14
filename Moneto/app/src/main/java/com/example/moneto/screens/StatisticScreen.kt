package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.components.TransactionList
import com.example.moneto.components.WeekChart
import com.example.moneto.testData.mockExpenses
import com.example.moneto.ui.theme.Background

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StatisticScreen(/*navController: NavController*/) {

    val pageScrollState = rememberScrollState()

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
                WeekChart()
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between chart and button
            Box(/*modifier = Modifier.verticalScroll(rememberScrollState())*/) {
                Column(
                    modifier = Modifier/*.verticalScroll(transactionListScrollState)*/.fillMaxHeight()
                ) {
                    TransactionList(expenses = mockExpenses)
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