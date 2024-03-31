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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.moneto.components.CustomRow
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(navController: NavController) {
    val currencies = listOf("EUR", "USD", "GBP")
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
            Row() {
                var currenciesMenuOpen by remember {
                    mutableStateOf(false)
                }
                var setCurrency: String? = null
                Text(text = "Used Currency:", modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),style = Typography.titleLarge,)
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = LightPurple
                ),modifier = Modifier
                    .clip(
                        RoundedCornerShape(10.dp)
                    ).fillMaxWidth(),onClick = {currenciesMenuOpen = true}) {
                    Row {
                        Text(setCurrency ?: "Select category", color = Purple80)
                        Icon(Icons.Default.KeyboardArrowDown ,contentDescription = null,
                            tint = Purple80,
                            modifier = Modifier.size(23.dp))
                    }
                        DropdownMenu(
                            expanded = currenciesMenuOpen,
                            onDismissRequest = { currenciesMenuOpen = false }) {
                            currencies?.forEach { currency ->
                                DropdownMenuItem(text = { Text(currency, color = Purple80) },
                                    onClick = {
                                        setCurrency = currency
                                        currenciesMenuOpen = false
                                    })
                            }
                        }
                }
            }
            Button(
                onClick = {  },
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


