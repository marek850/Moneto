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

data class LimitsScreenState(
    val dailyLimit: String = "",
    val monthlyLimit: String = "",

)
class LimitsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LimitsScreenState())
    val uiState: StateFlow<LimitsScreenState> = _state.asStateFlow()
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