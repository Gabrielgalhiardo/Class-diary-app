package com.example.classdiary.ui.cadastro

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.classdiary.data.DataSource
import com.example.classdiary.data.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CadastroViewModel(application: Application) : AndroidViewModel(application) {

    private val dataSource = DataSource(application)
    private val _uiState = MutableStateFlow(CadastroUIState())
    val uiState: StateFlow<CadastroUIState> = _uiState.asStateFlow()

    fun carregarStudents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val students = dataSource.loadAStudents()
            _uiState.update {
                it.copy(
                    students = students,
                    isLoading = false
                )
            }
        }
    }

    fun mudarTextoEmail(textoEmail: String) {
        _uiState.update { it.copy(email = textoEmail, emailInvalido = false) }
    }

    fun mudarTextoSenha(textoSenha: String) {
        _uiState.update { it.copy(senha = textoSenha, senhaInvalida = false) }
    }

    fun mudarTextoNome(textoNome: String) {
        _uiState.update { it.copy(nome = textoNome, nomeInvalido = false) }
    }

    fun mudarTextoCurso(textoCurso: String) {
        _uiState.update { it.copy(curso = textoCurso, cursoInvalido = false) }
    }

    fun onFotoChange(newUri: Uri?) {
        _uiState.update { it.copy(fotoUri = newUri) }
    }

    fun cadastrar() {
        _uiState.update { it.copy(cadastroSucesso = false) }

        val stateAtual = _uiState.value
        val nomeInvalido = stateAtual.nome.isBlank()
        val emailInvalido = !stateAtual.email.contains("@") || stateAtual.email.isBlank()
        val senhaInvalida = stateAtual.senha.length < 3
        val cursoInvalido = stateAtual.curso.isBlank()

        _uiState.update {
            it.copy(
                nomeInvalido = nomeInvalido,
                emailInvalido = emailInvalido,
                senhaInvalida = senhaInvalida,
                cursoInvalido = cursoInvalido
            )
        }

        val temErro = nomeInvalido || emailInvalido || senhaInvalida || cursoInvalido
        if (temErro) {
            return
        }

        val novoStudent = Student(
            nome = stateAtual.nome,
            email = stateAtual.email,
            senha = stateAtual.senha,
            curso = stateAtual.curso,
            fotoUri = stateAtual.fotoUri
        )

        _uiState.update {
            it.copy(
                students = it.students + novoStudent,
                cadastroSucesso = true,
                nome = "",
                email = "",
                senha = "",
                curso = "",
                fotoUri = null
            )
        }
    }

}