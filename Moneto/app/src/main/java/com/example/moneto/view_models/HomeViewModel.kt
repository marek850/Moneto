package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Currency
import com.example.moneto.data.TimeRange
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.data.monetoDb
import com.example.moneto.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeViewState(
    val transactions: MutableList<Transaction> = mutableListOf(),
    val expensesValue: Double = 0.0,
    val incomeValue: Double = 0.0,
    val totalSum: Double = 0.0,
    val timeRange: TimeRange = TimeRange.Day,
    val currency: Currency? = null
)

class HomeViewModel : ViewModel(), TransactionsBaseViewModel{
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state.asStateFlow()

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
            updateTimeRangeAndSums(TimeRange.Day)
        }
    }
    fun removeTransaction(tranToRemove: Transaction){

        viewModelScope.launch(Dispatchers.IO) {
            // This block handles the database write operation
            monetoDb.write {
                // Attempt to find and delete the transaction from the database.
                val deletingTransaction = this.query<Transaction>("_id == $0", tranToRemove._id).find().firstOrNull()
                if (deletingTransaction != null) {
                    delete(deletingTransaction)
                }
            }

            // After deleting, fetch data again and update UI state. Since `viewModelScope.launch` defaults to Main
            // if we specify `launch(Dispatchers.Main)` explicitly here, it ensures the UI updates are done on the main thread.
            viewModelScope.launch(Dispatchers.Main) {
                // Fetch the current state range
                val (start, end) = calculateDateRange(_state.value.timeRange)
                val transactions = monetoDb.query<Transaction>().find()

                val expenses = transactions.filter {
                    it.type == TransactionType.Expense && (it.date.toLocalDate().isAfter(start) &&
                            it.date.toLocalDate().isBefore(end) || it.date.toLocalDate().isEqual(start) || it.date.toLocalDate().isEqual(end))
                }
                val income = transactions.filter {
                    it.type == TransactionType.Income && (it.date.toLocalDate().isAfter(start) &&
                            it.date.toLocalDate().isBefore(end) || it.date.toLocalDate().isEqual(start) || it.date.toLocalDate().isEqual(end))
                }
                val expenseTotal = expenses.sumOf { it.amount }
                val incomeTotal = income.sumOf { it.amount }
                val totalSum = incomeTotal - expenseTotal

                // Update the UI state
                _state.update { currentState ->
                    val updatedTransactions = transactions.filter { it._id != tranToRemove._id }
                    currentState.copy(
                        transactions = updatedTransactions.toMutableList(),
                        expensesValue = expenseTotal,
                        incomeValue = incomeTotal,
                        totalSum = totalSum
                    )
                }
            }
        }
        /*
            monetoDb.write {
                // First, attempt to find and delete the transaction from the database.
                val deletingTransaction = this.query<Transaction>("_id == $0", tranToRemove._id).find().first()
                deletingTransaction.let {
                    delete(it)
                    val (start, end) = calculateDateRange(_state.value.timeRange)
                    val expenses = monetoDb.query<Transaction>().find().filter { transaction ->
                        (transaction.type == TransactionType.Expense) && ((transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                            .isBefore(end)) || transaction.date.toLocalDate()
                            .isEqual(start) || transaction.date.toLocalDate().isEqual(end))
                    }
                    val income = monetoDb.query<Transaction>().find().filter { transaction ->
                        (transaction.type == TransactionType.Income) && ((transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                            .isBefore(end)) || transaction.date.toLocalDate()
                            .isEqual(start) || transaction.date.toLocalDate().isEqual(end))
                    }
                    val expenseTotal = expenses.sumOf { it.amount }
                    val incomeTotal = income.sumOf { it.amount }
                    val totalSum = incomeTotal - expenseTotal
                    // Only proceed to update the UI and sums if the deletion is successful.
                    // Update the local transactions list and then recalculate sums.
                    _state.update { currentState ->
                        val updatedTransactions = currentState.transactions.filter { transaction ->
                            transaction._id != tranToRemove._id
                        }.toMutableList()

                        // Perform a copy to update the state with the new list without the removed transaction.
                        currentState.copy(transactions = updatedTransactions,expensesValue = expenseTotal,
                            incomeValue = incomeTotal,
                            totalSum = totalSum)
                    }

                    // Now call updateTimeRangeAndSums to recalculate based on the updated list.
                    // This should be done after the state is set with the updated transaction list.
                    // Pass the current time range from the state to reflect recent changes.
                }
            }
        }*/
    }
    fun updateTimeRangeAndSums(range:TimeRange) {
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
        val expenses = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.type == TransactionType.Expense) && ((transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                .isBefore(end)) || transaction.date.toLocalDate()
                .isEqual(start) || transaction.date.toLocalDate().isEqual(end))
        }
        val income = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.type == TransactionType.Income) && ((transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                .isBefore(end)) || transaction.date.toLocalDate()
                .isEqual(start) || transaction.date.toLocalDate().isEqual(end))
        }
        val expenseTotal = expenses.sumOf { it.amount }
        val incomeTotal = income.sumOf { it.amount }
        val totalSum = incomeTotal - expenseTotal
        _state.update { currentState ->
            currentState.copy(
                transactions = mutableTransactions,
                expensesValue = expenseTotal,
                incomeValue = incomeTotal,
                totalSum = totalSum,
                timeRange = range
            )
        }
    }
}

