package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import com.example.moneto.data.Category
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class AddScreenState(
    val amount: String = "",
    val description: String = "",
    val date: LocalDate,
    val category: String? = null,
    val categories: RealmResults<Category>? = null

)
class AddExpenseViewModel : ViewModel(){
    private val _state = MutableStateFlow(AddScreenState(
        description = "",
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
    fun setDescription(description: String) {
        _state.update { currentState -> currentState.copy(description = description) }
    }
    fun setDate(date: LocalDate) {
        _state.update { currentState -> currentState.copy(date = date) }
    }
    fun setCategory(category: String) {
        _state.update { currentState -> currentState.copy(category = category) }
    }
    fun addExpense() {
           // TODO dorobit po implementacii DB
    }

}