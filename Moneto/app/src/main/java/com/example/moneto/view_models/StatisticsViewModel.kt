package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Currency
import com.example.moneto.data.TimeRange
import com.example.moneto.data.Transaction
import com.example.moneto.data.monetoDb
import com.example.moneto.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StatisticsViewState(
    val transactions: List<Transaction> = listOf(),
    val timeRange: TimeRange = TimeRange.Day,
    val currency: Currency? = null
)

class StatisticsViewModel : ViewModel(){
    private val _state = MutableStateFlow(StatisticsViewState())
    val state: StateFlow<StatisticsViewState> = _state.asStateFlow()

    init {
        val currencies = monetoDb.query<Currency>().find()
        val currency = currencies[0]
        _state.update { currentState ->
            currentState.copy(
                transactions = monetoDb.query<Transaction>().find(),
                currency = currency
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            updateTimeRange(TimeRange.Day)
        }
    }
    fun updateTimeRange(range:TimeRange) {
        val (start, end) = calculateDateRange(range)
        val transactions = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                .isBefore(end)) || transaction.date.toLocalDate()
                .isEqual(start) || transaction.date.toLocalDate().isEqual(end)
        }
        _state.update { currentState ->
            currentState.copy(
                timeRange = range,
                transactions = transactions
            )
        }
    }
}

