package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.TimeRange
import com.example.moneto.data.Transaction
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StatisticsViewState(
    val transactions: List<Transaction> = listOf(),
    val timeRange: TimeRange = TimeRange.Day
)

class StatisticsViewModel : ViewModel(){
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state.asStateFlow()

    init {
        _state.update { currentState ->
            currentState.copy(
                transactions = monetoDb.query<Transaction>().find()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
    fun updateTimeRange(range:TimeRange) {
        _state.update { currentState ->
            currentState.copy(
                timeRange = range
            )
        }
    }
}

