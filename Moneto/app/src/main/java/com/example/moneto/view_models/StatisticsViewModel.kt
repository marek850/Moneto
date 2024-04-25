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
    val transactions: MutableList<Transaction> = mutableListOf(),
    val timeRange: TimeRange = TimeRange.Day,
    val currency: Currency? = null
)

class StatisticsViewModel : ViewModel(), TransactionsBaseViewModel{
    private val _state = MutableStateFlow(StatisticsViewState())
    val state: StateFlow<StatisticsViewState> = _state.asStateFlow()

    init {
        val currencies = monetoDb.query<Currency>().find()
        val currency = currencies[0]
        val transactions = monetoDb.query<Transaction>().find()
        val mutableTransactions = mutableListOf<Transaction>()
        for (transaction in transactions){
            mutableTransactions.add(transaction)
        }
        _state.update { currentState ->
            currentState.copy(
                transactions = mutableTransactions,
                currency = currency
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            updateTimeRange(TimeRange.Day)
        }
    }
    fun removeTransaction(tranToRemove: Transaction) {

        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletingTransaction =
                    this.query<Transaction>("_id == $0", tranToRemove._id).find().firstOrNull()
                deletingTransaction?.let {
                    delete(it)
                    // Perform the state update only after successful deletion
                    _state.update { currentState ->
                        val updatedTransactions = currentState.transactions.filter { transaction ->
                            transaction._id != tranToRemove._id
                        }.toMutableList()
                        currentState.copy(transactions = updatedTransactions)
                    }
                }
            }
        }
    }
    fun updateTimeRange(range:TimeRange) {
        val (start, end) = calculateDateRange(range)
        val transactions = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                .isBefore(end)) || transaction.date.toLocalDate()
                .isEqual(start) || transaction.date.toLocalDate().isEqual(end)
        }
        val mutableTransactions = mutableListOf<Transaction>()
        for (transaction in transactions){
            mutableTransactions.add(transaction)
        }
        _state.update { currentState ->
            currentState.copy(
                timeRange = range,
                transactions = mutableTransactions
            )
        }
    }
}

