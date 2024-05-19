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
 * Reprezentuje stav zobrazenia štatistík, ktorý obsahuje rôzne parametre pre filtrovanie a zobrazovanie transakcií.
 *
 * @param transactions Zmeniteľný zoznam transakcií, ktoré sú aktuálne zobrazené.
 * @param timeRange Časový rozsah vybraný pre filtrovanie transakcií. Prednastavené na [TimeRange.Day].
 * @param currency Vybraná mena pre sumy transakcií, môže byť null ak nie je špecifikovaná.
 * @param typeOfTransaction Typ filtrovania transakcií; prednastavené na [TransactionType.All] pre zobrazenie všetkých transakcií.
 */
data class StatisticsViewState(
    val transactions: MutableList<Transaction> = mutableListOf(),
    val timeRange: TimeRange = TimeRange.Day,
    val currency: Currency? = null,
    val typeOfTransaction: TransactionType = TransactionType.All
)
/**
 * ViewModel podporujúci zobrazenie štatistík, ktorý spravuje stav a interakcie s užívateľom.
 *
 * Dedičnosť z [TransactionsBaseViewModel] umožňuje využitie základných operácií s transakciami.
 */
class StatisticsViewModel : TransactionsBaseViewModel(){
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
        // Inicializuje stav s prvou dostupnou menou a načítanými transakciami.
        _state.update { currentState ->
            currentState.copy(
                transactions = mutableTransactions,
                currency = currency
            )
        }
        // Aktualizuje časový rozsah a typ transakcií pri inicializácii.
        viewModelScope.launch(Dispatchers.IO) {
            updateTimeRange(_state.value.timeRange, _state.value.typeOfTransaction)
        }
    }
    /**
     * Odstráni transakciu z databázy a aktualizuje stav zobrazenia.
     * @param tranToRemove Transakcia, ktorá má byť odstránená.
     */
    override fun removeTransaction(tranToRemove: Transaction) {

        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletingTransaction =
                    this.query<Transaction>("_id == $0", tranToRemove._id).find().firstOrNull()
                deletingTransaction?.let {
                    delete(it)
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
    /**
     * Aktualizuje stav zobrazenia na základe vybraného časového rozsahu a typu transakcií.
     * @param range Nový časový rozsah, ktorý sa má uplatniť.
     * @param transactionType Typ transakcií, ktoré majú byť filtrované.
     */
    override fun updateTimeRange(range:TimeRange, transactionType: TransactionType) {
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
        _state.update { currentState ->
            currentState.copy(
                timeRange = range,
                typeOfTransaction = transactionType,
                transactions = mutableTransactions
            )
        }
    }
}

