package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CurrenciesScreenState(
    val symbol: String = "",
    val shortName: String = "",

    //val currencies: RealmResults<Currency>? = null //TODO po implementacii DB upravit

)
class CurrenciesViewModel : ViewModel() {
    private val _state = MutableStateFlow(CurrenciesScreenState())
    val uiState: StateFlow<CurrenciesScreenState> = _state.asStateFlow()

}