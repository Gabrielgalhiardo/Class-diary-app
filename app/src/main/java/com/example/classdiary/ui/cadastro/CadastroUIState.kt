package com.example.classdiary.ui.cadastro

import android.net.Uri
import com.example.classdiary.data.Student
data class CadastroUIState (
    val emailInvalido: Boolean = false,
    val senhaInvalida: Boolean = false,
    val nomeInvalido: Boolean = false,
    val fotoInvalida: Boolean = false,
    val cursoInvalido: Boolean = false,
    val cadastroSucesso: Boolean = false,

    val labelNome:String = "Nome",
    val labelEmail:String = "Email",
    val labelSenha:String = "Senha",
    val labelCurso:String = "Curso",

    val nome: String = "",
    val login: String = "",
    val senha: String = "",
    val email: String = "",
    val curso: String = "",
    val fotoUri: Uri? = null,
    val students: List<Student> = emptyList(),
    val isLoading: Boolean = false


    )
