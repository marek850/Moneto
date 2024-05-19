package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Limit
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
/**
 * Reprezentuje stav obrazovky limitov, uchováva informácie o denných a mesačných limitoch.
 * Táto dátová trieda sa používa na sledovanie a zobrazenie limitov v užívateľskom rozhraní.
 *
 * @param dailyLimit Textový reťazec reprezentujúci nastavený denný limit.
 * @param monthlyLimit Textový reťazec reprezentujúci nastavený mesačný limit.
 */
data class LimitsScreenState(
    val dailyLimit: String = "",
    val monthlyLimit: String = "",

)
/**
 * ViewModel pre obrazovku limitov, ktorý spravuje stav a interakcie súvisiace s limitmi výdavkov.
 * Táto trieda zodpovedá za načítanie, aktualizáciu a ukladanie denných a mesačných limitov.
 */
class LimitsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LimitsScreenState())
    val state: StateFlow<LimitsScreenState> = _state.asStateFlow()
    init {
        val limits = monetoDb.query<Limit>().find()
        if (!limits.isEmpty()){
            _state.update { currentState ->
                currentState.copy(
                    dailyLimit = limits[0].dailyLimit.toString(),
                    monthlyLimit = limits[0].monthlyLimit.toString()
                )
            }
        }
    }
    /**
     * Nastaví nový denný limit.
     * @param newLimit Nový limit ako textový reťazec.
     */
    fun setDailyLimit(newLimit: String) {
        var parsed = newLimit.toDoubleOrNull()

        if (newLimit.isEmpty()) {
            parsed = 0.0
        }

        if (parsed != null) {

            _state.update { currentState ->
                currentState.copy(
                    dailyLimit = newLimit.trim().ifEmpty { "0" },
                )
            }
        }
    }
    /**
     * Nastaví nový mesačný limit.
     * @param newLimit Nový limit ako textový reťazec.
     */
    fun setMonthlyLimit(newLimit: String) {
        var parsed = newLimit.toDoubleOrNull()

        if (newLimit.isEmpty()) {
            parsed = 0.0
        }

        if (parsed != null) {
            _state.update { currentState ->
                currentState.copy(
                    monthlyLimit = newLimit.trim().ifEmpty { "0" },
                )
            }
        }
    }
    /**
     * Pridá alebo aktualizuje limity v databáze.
     * Metóda odstráni existujúce limity a vloží nové s aktuálnymi hodnotami z UI stavu.
     */
    fun addLimits() {
        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletedLimit = this.query<Limit>().find()
                if (deletedLimit.isNotEmpty())
                    delete(deletedLimit[0])
                this.copyToRealm(
                    Limit(
                        1,
                        dailyLimit =  _state.value.dailyLimit.trim().ifEmpty { "0" }.toDouble(),
                        monthlyLimit = _state.value.monthlyLimit.trim().ifEmpty { "0" }.toDouble()
                    )
                )
            }
        }
    }
}