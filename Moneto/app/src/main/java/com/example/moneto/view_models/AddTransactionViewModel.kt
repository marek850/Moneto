package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Category
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
/**
 * Reprezentuje stav obrazovky pre pridávanie nových transakcií, uchováva informácie potrebné na vytvorenie transakcie.
 *
 * @param amount Suma transakcie ako textový reťazec.
 * @param description Popis transakcie.
 * @param type Typ transakcie (príjem alebo výdavok).
 * @param date Dátum transakcie.
 * @param category Kategória transakcie, môže byť null ak nie je špecifikovaná.
 * @param categories Zoznam možných kategórií načítaných z databázy, môže byť null ak nie sú načítané.
 */
data class AddScreenState(
    val amount: String = "",
    val description: String = "",
    val type: TransactionType,
    val date: LocalDate,
    val category: Category? = null,
    val categories: RealmResults<Category>? = null

)
/**
 * ViewModel pre obrazovku pridania novej transakcie, zodpovedný za správu a aktualizáciu stavu transakcie.
 * Táto trieda spravuje údaje o transakcii a interaguje s databázou pre ukladanie transakcií.
 */
class AddTransactionViewModel : ViewModel(){
    private val _state = MutableStateFlow(AddScreenState(
        description = "",
        type = TransactionType.Expense,
        date = LocalDate.now()
    ))
    val uiState: StateFlow<AddScreenState> = _state.asStateFlow()
    init {
        _state.update { currentState ->
            currentState.copy(
                categories = monetoDb.query<Category>().find()
            )
        }
    }
    /**
     * Nastaví sumu transakcie v stave.
     * @param amount Suma transakcie ako textový reťazec.
     */
    fun setAmount(amount: String) {
        var parsed = amount.toDoubleOrNull()
        if (amount.isEmpty()) {
            parsed = 0.0
        }
        if (parsed != null) {
            _state.update { currentState ->
                currentState.copy(
                    amount = amount.trim().ifEmpty { "0" },
                )
            }
        }
    }
    /**
     * Nastaví popis transakcie v stave.
     * @param description Popis transakcie.
     */
    fun setDescription(description: String) {
        _state.update { currentState -> currentState.copy(description = description) }
    }
    /**
     * Nastaví dátum transakcie v stave.
     * @param date Dátum transakcie.
     */
    fun setDate(date: LocalDate) {
        _state.update { currentState -> currentState.copy(date = date) }
    }
    /**
     * Nastaví kategóriu transakcie v stave.
     * @param category Kategória transakcie.
     */
    fun setCategory(category: Category) {
        _state.update { currentState -> currentState.copy(category = category) }
    }
    /**
     * Vytvorí a uloží transakciu do databázy na základe aktuálnych údajov v stave.
     */
    fun addTransaction() {
        if (_state.value.category != null && _state.value.amount != "") {
            viewModelScope.launch(Dispatchers.IO) {
                val now = LocalDateTime.now()
                monetoDb.write {
                    this.copyToRealm(
                        Transaction(
                            description = _state.value.description,
                            amount = _state.value.amount.toDouble(),
                            type = _state.value.type,
                            date = _state.value.date.atTime(now.hour, now.minute, now.second),
                            category =this.query<Category>("_id == $0", _state.value.category!!._id)
                                .find().first(),
                        )
                    )
                }
                _state.update { currentState ->
                    currentState.copy(
                        description = "",
                        amount = "",
                        type = TransactionType.Expense,
                        date = LocalDate.now(),
                        category = null,
                        categories = null
                    )
                }
            }
        }
    }
    /**
     * Nastaví typ transakcie v stave.
     * @param transactionType Typ transakcie.
     */
    fun setType(transactionType: TransactionType){
        _state.update { currentState ->
            currentState.copy(
                type = transactionType,
            )
        }
    }

}