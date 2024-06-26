@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moneto.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneto.components.CustomRow
import com.example.moneto.components.CustomTextField
import com.example.moneto.data.TransactionType
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography
import com.example.moneto.view_models.AddTransactionViewModel
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
/**
 * Composable funkcia, ktorá zobrazuje obrazovku pridania transakcie v aplikácii Moneto.
 * Táto obrazovka umožňuje používateľom vkladať a odosielať nové transakcie, vrátane popisu, sumy, typu, dátumu a kategórie.
 * Na zber údajov od používateľa využíva rôzne komponenty užívateľského rozhrania, ako sú textové polia, tlačidlá a rozbaľovacie menu.
 *
 * @param addViewModel ViewModel, ktorý riadi a poskytuje stav pre operácie súvisiace s transakciami.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddTransaction(addViewModel: AddTransactionViewModel = viewModel()) {
    val state by addViewModel.state.collectAsState()
    val transactionTypes = listOf(
        TransactionType.Income,
        TransactionType.Expense
    )
    val openDialog = remember { mutableStateOf(false) }

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
                    .fillMaxHeight().verticalScroll(rememberScrollState()),
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

                        CustomRow(label = "Description") {
                            CustomTextField(
                                value = state.description,
                                onValueChange = addViewModel::setDescription,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.Right,
                                ),
                                placeholder = {
                                    Text("Big Mac Menu etc.")
                                },
                                arrangement = Arrangement.End,
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        CustomRow(
                            label = "Amount *"
                        ) {
                            CustomTextField(
                                value = state.amount,
                                onValueChange = addViewModel::setAmount,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.Right,
                                ),
                                placeholder = { Text(text = "0") },
                                arrangement = Arrangement.End,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                ),
                                maxLines = 1
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        CustomRow(label = "Type *") {
                            var typeMenuOpened by remember {
                                mutableStateOf(false)
                            }
                            TextButton(onClick = { typeMenuOpened = true }) {
                                Text(state.type.name, color = Purple80)
                                DropdownMenu(
                                    expanded = typeMenuOpened,
                                    onDismissRequest = { typeMenuOpened = false }) {
                                    transactionTypes.forEach { type ->
                                        DropdownMenuItem(text = {
                                            Text(
                                                type.name,
                                                color = Purple80
                                            )
                                        },
                                            onClick = {
                                                addViewModel.setType(type)
                                                typeMenuOpened = false
                                            })
                                    }
                                }
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        var datePickerShowing by remember {
                            mutableStateOf(false)
                        }
                        CustomRow(
                            label = "Date"
                        ) {
                            TextButton(onClick = { datePickerShowing = true }) {
                                Text(state.date.toString(), color = Purple80)
                            }
                            if (datePickerShowing) {
                                DatePickerDialog(
                                    colors = DatePickerDefaults.colors(
                                        Purple80, yearMonthTextColor = Purple80,
                                        monthDayLabelSelectedBackgroundColor = Purple80,
                                        todayLabelTextColor = Purple80,
                                        previousNextMonthIconColor = Purple80,
                                        headlineSingleSelectionTextColor = Purple80,
                                        weekDayLabelTextColor = Purple80
                                    ),
                                    buttonColors = ButtonDefaults.textButtonColors(
                                        contentColor = Purple80,
                                    ),
                                    onDismissRequest = { datePickerShowing = false },
                                    onDateChange = {
                                        addViewModel.setDate(it)
                                        datePickerShowing = false
                                    },
                                    initialDate = state.date,

                                    title = { Text("Select date", style = Typography.titleMedium) }
                                )
                            }

                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )

                        CustomRow(label = "Category *") {
                            var categoriesMenuOpen by remember {
                                mutableStateOf(false)
                            }
                            TextButton(onClick = { categoriesMenuOpen = true }) {
                                Text(state.category?.name ?: "Select category", color = Purple80)
                                DropdownMenu(
                                    expanded = categoriesMenuOpen,
                                    onDismissRequest = { categoriesMenuOpen = false }) {
                                    state.categories?.forEach { category ->
                                        DropdownMenuItem(text = {
                                            Text(
                                                category.name,
                                                color = Purple80
                                            )
                                        },
                                            onClick = {
                                                addViewModel.setCategory(category)
                                                categoriesMenuOpen = false
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {if (state.category == null || state.amount == "") {
                        openDialog.value = true
                    } else {
                        addViewModel.addTransaction()
                    } },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightPurple
                    )
                ) {
                    Text("Add transaction", color = Purple80)
                }
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Fill Required Data", color = Purple80) },
                        text = { Text("Please fill all required information. Required information is marked with *") },
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