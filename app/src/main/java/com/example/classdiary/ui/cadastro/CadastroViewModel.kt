package com.example.classdiary.ui.cadastro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CadastroViewModel: ViewModel(){

    private val _uiState = MutableStateFlow(CadastroUIState())
    val uiState: StateFlow<CadastroUIState> = _uiState.asStateFlow()


    var email by mutableStateOf("")
        private set

    var senha by mutableStateOf("")
        private set

    var nome by mutableStateOf("")
        private set

    var caminhoFoto by mutableStateOf("")
        private set

    fun mudarTextoEmail(textoEmail: String) {
        email = textoEmail
        reset()
    }
    fun mudarTextoSenha(textoSenha: String) {
        senha = textoSenha
        reset()
    }

    fun mudarTextoNome(textoNome: String) {
        nome = textoNome
        reset()
    }


    fun mudarTextoFoto(textoFoto: String) {
        caminhoFoto = textoFoto
        reset()
    }


    fun cadastrar() {
        if (!(email.contains("@gmail.com")) || email.isEmpty() || senha.isEmpty() || nome.isEmpty() || caminhoFoto.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(cadastroSucesso = false)
            }
        }
        else{
            _uiState.update { currentState ->
                currentState.copy(cadastroSucesso = true )
            }
        }
    }


    fun reset(){
        _uiState.update { currentState ->
            currentState.copy(
                emailInvalido = false,
                cadastroSucesso = false
            )
        }
    }
}