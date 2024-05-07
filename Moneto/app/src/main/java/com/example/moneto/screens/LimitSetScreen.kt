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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneto.components.CustomRow
import com.example.moneto.components.CustomTextField
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.view_models.LimitsViewModel
/**
 * Composable funkcia, ktorá zobrazuje obrazovku nastavenia limitov v aplikácii Moneto.
 * Táto obrazovka umožňuje používateľom nastaviť a uložiť denné a mesačné limity výdavkov. Poskytuje spätnú väzbu
 * prostredníctvom dialógového okna po uložení limitov.
 *
 * @param navController NavController, ktorý zabezpečuje navigáciu v aplikácii.
 * @param limitViewModel ViewModel, ktorý poskytuje a spravuje stav súvisiaci s nastaveniami limitov.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitSetScreen(navController: NavController, limitViewModel: LimitsViewModel = viewModel()) {
    val state by limitViewModel.uiState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
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
                        Text("Settings", color = Purple80)
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
                    CustomRow(label = "Daily Expense Limit") {
                        CustomTextField(
                            value = state.dailyLimit,
                            onValueChange = limitViewModel::setDailyLimit,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            placeholder = {
                                Text("Not Set")
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
                    CustomRow(label = "Monthly Expense Limit") {
                        CustomTextField(
                            value = state.monthlyLimit,
                            onValueChange = limitViewModel::setMonthlyLimit,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            placeholder = {
                                Text("Not Set")
                            },
                            arrangement = Arrangement.End,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
            Button(
                onClick = {limitViewModel.addLimits()
                    openDialog.value = true },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightPurple
                )
            ) {
                Text("Save", color = Purple80)
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = { Text(text = "Limits Updated", color = Purple80) },
                    text = { Text("Your limits have been updated") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("OK",color = Purple80)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("Cancel",color = Purple80)
                        }
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    ),
                    containerColor = Background
                )
            }
        }
    })
}