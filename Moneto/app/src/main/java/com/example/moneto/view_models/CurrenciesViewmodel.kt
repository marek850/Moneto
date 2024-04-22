package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Curr
import com.example.moneto.data.Currency
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CurrenciesScreenState(
    val symbol: String = "",
    val shortName: String = "",
    val currency: Curr = Curr.Euro
    //val currencies: RealmResults<Currency>? = null //TODO po implementacii DB upravit

)
class CurrenciesViewModel : ViewModel() {
    private val _state = MutableStateFlow(CurrenciesScreenState())
    val uiState: StateFlow<CurrenciesScreenState> = _state.asStateFlow()
    init {
        val currencies = monetoDb.query<Currency>().find()
        if (!currencies.isEmpty()){
            _state.update { currentState ->
                currentState.copy(
                    symbol = currencies[0].symbol,
                    shortName = currencies[0].code
                )
            }
        }
    }
    fun setCurrency(symbol: String, code: String) {
        _state.update { currentState ->
            currentState.copy(
                symbol = symbol,
                shortName = code
            )
        }
    }
    fun saveCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletedCurrency = this.query<Currency>().find()
                if (deletedCurrency.isNotEmpty())
                    delete(deletedCurrency[0])
                this.copyToRealm(
                    Currency(1, _state.value.shortName, _state.value.symbol)
                )
            }
        }
    }
}