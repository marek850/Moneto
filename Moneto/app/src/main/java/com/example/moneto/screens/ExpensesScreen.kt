package com.example.moneto.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneto.components.TransactionList
import com.example.moneto.testData.mockExpenses
import com.example.moneto.view_models.ExpensesViewModel
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExpensesScreen(navController: NavController,expensesViewModel: ExpensesViewModel = viewModel()) {
    val uiState by expensesViewModel.uiState.collectAsState()

    val colorPickerController = rememberColorPickerController()
    TransactionList(expenses = mockExpenses)

}