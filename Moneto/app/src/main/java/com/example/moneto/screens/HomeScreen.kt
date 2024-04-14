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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.components.TransactionChart
import com.example.moneto.components.TransactionList
import com.example.moneto.testData.mockExpenses
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.Purple80
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeScreen() {
    val incomes = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Purple80)) {
            append("Income: ")
        }
        withStyle(style = SpanStyle(color = Color.Green)) {
            append("20$")
        }
    }
    val expenses = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.LightGray)) {
            append("Expenses:")
        }
        withStyle(style = SpanStyle(color = Color.Red)) {
            append("10$")
        }
    }
    Scaffold(modifier = Modifier.fillMaxHeight(),content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxHeight()
                .fillMaxWidth() // Ensure the Column takes up all available height
                .verticalScroll(rememberScrollState()), // Add scrolling
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
                        TransactionChart()
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Total balance: 10$", color = Purple80)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Add space between chart and button
            Column(/*modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxHeight()*/) {
                TransactionList(expenses = mockExpenses)
            }

        }
    })
}
