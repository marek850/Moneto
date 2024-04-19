package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeViewState(
    val transactions: List<Transaction> = listOf(),
    val expensesValue: Double = 0.0,
    val incomeValue: Double = 0.0,
    val totalSum: Double = 0.0
)

class HomeViewModel : ViewModel(){
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state.asStateFlow()

    init {
        _state.update { currentState ->
            currentState.copy(
                transactions = monetoDb.query<Transaction>().find()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            setTotalValues()
        }
    }
    fun setTotalValues() {
        val expenses = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.type == TransactionType.Expense)
        }
        val income = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.type == TransactionType.Income)
        }
        val expenseTotal = expenses.sumOf { it.amount }
        val incomeTotal = income.sumOf { it.amount }
        val totalSum = incomeTotal - expenseTotal
        _state.update { currentState ->
            currentState.copy(
                expensesValue = expenseTotal,
                incomeValue = incomeTotal,
                totalSum = totalSum
            )
        }
    }
}

