package com.rafaelcosta.diariodeclasse.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    var login by mutableStateOf("")
        private set

    var senha by mutableStateOf("")
        private set

    fun mudarTextoLogin(textoLogin: String) {
        login = textoLogin
        reset()
    }

    fun mudarTextoSenha(textoSenha: String) {
        senha = textoSenha
        reset()
    }

    fun logar() {
        if (senha.equals("1234") && login.equals("rafa")) {
            _uiState.update { currentState ->
                currentState.copy(loginSucesso = true)
            }
        }else{
            _uiState.update { currentState ->
                currentState.copy(errouLoginOuSenha = true )
            }
        }
    }

    fun reset(){
        _uiState.update { currentState ->
            currentState.copy(
                errouLoginOuSenha = false,
                loginSucesso = false
            )
        }
    }
}