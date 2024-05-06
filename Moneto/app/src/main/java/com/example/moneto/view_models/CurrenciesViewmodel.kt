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
/**
 * Reprezentuje stav obrazovky meny, obsahujúci informácie o aktuálne vybranej mene.
 *
 * @param symbol Symbol meny, napríklad € pre Euro.
 * @param shortName Skratka meny, napríklad EUR pre Euro.
 * @param currency Enum reprezentujúci typ meny, predvolene nastavené na Euro.
 */
data class CurrenciesScreenState(
    val symbol: String = "",
    val shortName: String = "",
    val currency: Curr = Curr.Euro
)
/**
 * ViewModel pre obrazovku mien, ktorý spravuje a uchováva stav súvisiaci s výberom a správou mien.
 * Umožňuje aktualizáciu a ukladanie vybranej meny do databázy.
 */
class CurrenciesViewModel : ViewModel() {
    private val _state = MutableStateFlow(CurrenciesScreenState())
    val uiState: StateFlow<CurrenciesScreenState> = _state.asStateFlow()
    init {
        val currencies = monetoDb.query<Currency>().find()
        if (!currencies.isEmpty()){
            // Inicializuje stav s prvou nájdenou menou v databáze.
            _state.update { currentState ->
                currentState.copy(
                    symbol = currencies[0].symbol,
                    shortName = currencies[0].code
                )
            }
        }
    }
    /**
     * Nastaví nové hodnoty pre symbol a skratku meny v stave.
     * @param symbol Nový symbol meny, napríklad $.
     * @param code Nová skratka meny, napríklad USD.
     */
    fun setCurrency(symbol: String, code: String) {
        _state.update { currentState ->
            currentState.copy(
                symbol = symbol,
                shortName = code
            )
        }
    }
    /**
     * Uloží aktuálnu vybranú menu do databázy. Existujúce záznamy o mene sú vymazané a nahradené novými údajmi.
     */
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