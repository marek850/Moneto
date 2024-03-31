package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ExpensesState(
    val name: String = "",
) {


}

class ExpensesViewModel(): ViewModel() {
    private val _state = MutableStateFlow(ExpensesState())
    val uiState: StateFlow<ExpensesState> = _state.asStateFlow()


}