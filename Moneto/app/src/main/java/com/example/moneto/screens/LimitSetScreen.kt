package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneto.components.CustomRow
import com.example.moneto.components.CustomTextField
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.view_models.LimitsViewModel

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitSetScreen(navController: NavController, limitViewModel: LimitsViewModel = viewModel()) {
    val state by limitViewModel.uiState.collectAsState()
    Scaffold(topBar = {
        MediumTopAppBar(
            title = { Text("Limits", color = Purple80) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
            ),
            navigationIcon = {
                Surface(
                    onClick = navController::popBackStack,
                    color = Color.Transparent,
                ) {
                    Row(modifier = Modifier.padding(vertical = 10.dp)) {
                        Icon(
                            Icons.Rounded.KeyboardArrowLeft,
                            tint = Purple80,
                            contentDescription = "Settings"
                        )
                        Text("Settings", color = Purple80,)
                    }
                }
            }
        )
    }, modifier = Modifier.fillMaxHeight(),content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Box (modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(LightBackground)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    CustomRow(label = "Daily Limit",) {
                        CustomTextField(
                            value = state.dailyLimit,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            onValueChange = limitViewModel::setDailyLimit,
                            placeholder = {
                                Text("Not set")
                            },
                            arrangement = Arrangement.End,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                    CustomRow(label = "Monthly Limit",) {
                        CustomTextField(
                            value = state.monthlyLimit,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            onValueChange = limitViewModel::setMonthlyLimit,
                            placeholder = {
                                Text("Not set")
                            },
                            arrangement = Arrangement.End,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
            Button(
                onClick = {  },//TODO uprava limitu v DB
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightPurple
                )
            ) {
                Text("Save", color = Purple80)
            }
        }
    })
}