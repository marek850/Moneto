package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LimitsScreenState(
    val dailyLimit: String = "",
    val monthlyLimit: String = "",

)
class LimitsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LimitsScreenState())
    val uiState: StateFlow<LimitsScreenState> = _state.asStateFlow()
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
}