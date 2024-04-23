package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneto.components.CustomRow
import com.example.moneto.data.Curr
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography
import com.example.moneto.view_models.CurrenciesViewModel
import com.example.moneto.view_models.LimitsViewModel

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(navController: NavController,currenciesViewModel: CurrenciesViewModel = viewModel()) {
    val state by currenciesViewModel.uiState.collectAsState()
    val currencies = listOf(Curr.Euro, Curr.UnitedStatesDollar, Curr.AustralianDollar, Curr.JappaneseYen)
    val openDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = {
        MediumTopAppBar(
            title = { Text("Currencies", color = Purple80) },
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
            Row {
                var currenciesMenuOpen by remember {
                    mutableStateOf(false)
                }
                Text(text = "Used Currency:", modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),style = Typography.titleLarge,)
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = LightPurple
                ),modifier = Modifier
                    .clip(
                        RoundedCornerShape(10.dp)
                    ).fillMaxWidth(),onClick = {currenciesMenuOpen = true}) {
                    Row {
                        Text(state.shortName ?: "Select currency", color = Purple80)
                        Icon(Icons.Default.KeyboardArrowDown ,contentDescription = null,
                            tint = Purple80,
                            modifier = Modifier.size(23.dp))
                    }
                        DropdownMenu(
                            expanded = currenciesMenuOpen,
                            onDismissRequest = { currenciesMenuOpen = false }) {
                            currencies.forEach { currency ->
                                DropdownMenuItem(text = { Text(currency.code, color = Purple80) },
                                    onClick = {
                                        currenciesViewModel.setCurrency(currency.symbol, currency.code)
                                        currenciesMenuOpen = false
                                    })
                            }
                        }
                }
            }
            Button(
                onClick = {
                    currenciesViewModel.saveCurrency()
                    openDialog.value = true
                },
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
                    title = { Text(text = "Saved Currency", color = Purple80) },
                    text = { Text("Your currency has been saved") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false // Close dialog on confirm
                            }
                        ) {
                            Text("OK",color = Purple80)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false // Close dialog on dismiss
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


