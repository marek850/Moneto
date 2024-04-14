package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(/*navController: NavController*/) {

    Scaffold(topBar = {
        MediumTopAppBar(
            title = { Text("Add", color = Purple80) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
            )
        )
    }, modifier = Modifier.fillMaxHeight(),content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxHeight(),//.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            //BarChart()
            /*Box(modifier = Modifier
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
*/
        }
    })



}