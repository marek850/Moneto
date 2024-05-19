package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginScreenState(
    val email: String = "",
    val password: String = ""
)
class  LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState(
        email = "",
        password = ""
    ))
    val uiState: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun setEmail(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    fun setPassword(pass: String) {
        _state.update { currentState ->
            currentState.copy(
                password = pass,
            )
        }
    }
}