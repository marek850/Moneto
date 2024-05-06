package com.example.moneto.view_models

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
/**
 * Reprezentuje stav hlavnej obrazovky, uchováva údaje o transakciách a súhrne finančných ukazovateľov.
 *
 * @param transactions Zoznam všetkých transakcií pre zvolené obdobie.
 * @param expensesValue Celková hodnota výdavkov v zvolenom období.
 * @param incomeValue Celková hodnota príjmov v zvolenom období.
 * @param totalSum Čistá suma (príjmy mínus výdavky).
 * @param timeRange Časové rozpätie, pre ktoré sa zobrazujú transakcie (napr. deň, mesiac).
 * @param currency Menová jednotka, v ktorej sa zobrazujú finančné sumy.
 * @param typeOfTransaction Typ transakcií, ktoré sa zobrazujú (napr. všetky, len príjmy alebo len výdavky).
 */
data class HomeViewState(
    val transactions: MutableList<Transaction> = mutableListOf(),
    val expensesValue: Double = 0.0,
    val incomeValue: Double = 0.0,
    val totalSum: Double = 0.0,
    val timeRange: TimeRange = TimeRange.Day,
    val currency: Currency? = null,
    val typeOfTransaction: TransactionType = TransactionType.All
)
/**
 * ViewModel pre hlavnú obrazovku, zodpovedný za spracovanie údajov a aktualizácie stavu transakcií a finančných súhrnov.
 * Dedí od [TransactionsBaseViewModel] na zdieľanie základných funkcionalít pre správu transakcií.
 */
class HomeViewModel :  TransactionsBaseViewModel(){
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state.asStateFlow()

    init {
        val currencies = monetoDb.query<Currency>().find()
        val currency = currencies[0]// Predpokladá sa, že databáza obsahuje aspoň jednu menu.
        val transactions = monetoDb.query<Transaction>().find()
        val mutableTransactions = mutableListOf<Transaction>()
        for (transaction in transactions){
            mutableTransactions.add(transaction)
        }
        // Inicializácia stavu s načítanými transakciami a predvolenou menou.
        _state.update { currentState ->
            currentState.copy(
                transactions = mutableTransactions,
                currency = currency
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            updateTimeRangeAndSums(_state.value.timeRange, _state.value.typeOfTransaction)
        }
    }
    /**
     * Odstraňuje transakciu a aktualizuje súhrnné finančné ukazovatele.
     * @param tranToRemove Transakcia, ktorá má byť odstránená.
     */
    override fun removeTransaction(tranToRemove: Transaction){

        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletingTransaction = this.query<Transaction>("_id == $0", tranToRemove._id).find().firstOrNull()
                if (deletingTransaction != null) {
                    delete(deletingTransaction)
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
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

                _state.update { currentState ->
                    val updatedTransactions = transactions.filter { it._id != tranToRemove._id }
                    currentState.copy(
                        transactions = updatedTransactions.toMutableList(),
                        expensesValue = expenseTotal,
                        incomeValue = incomeTotal,
                        totalSum = totalSum
                    )
                }
                updateTimeRangeAndSums(_state.value.timeRange, _state.value.typeOfTransaction)
            }
        }
    }
    /**
     * Aktualizuje obdobie a súhrnné ukazovatele pre zobrazené transakcie na základe vybraného časového rozpätia a typu transakcií.
     * @param range Nové časové rozpätie.
     * @param transactionType Typ transakcií na zobrazenie.
     */
    fun updateTimeRangeAndSums(range:TimeRange,transactionType: TransactionType) {
        val (start, end) = calculateDateRange(range)
        val transactions = monetoDb.query<Transaction>().find().filter { transaction ->
            (transaction.date.toLocalDate().isAfter(start) && transaction.date.toLocalDate()
                .isBefore(end)) || transaction.date.toLocalDate()
                .isEqual(start) || transaction.date.toLocalDate().isEqual(end)
        }
        val mutableTransactions = mutableListOf<Transaction>()
        if (transactionType != TransactionType.All){
            for (transaction in transactions){
                if (transaction.type == transactionType) {
                    mutableTransactions.add(transaction)
                }
            }
        } else {
            for (transaction in transactions){
                mutableTransactions.add(transaction)
            }
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
                timeRange = range,
                typeOfTransaction = transactionType
            )
        }
    }
}

