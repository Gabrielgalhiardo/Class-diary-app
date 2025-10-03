package com.rafaelcosta.diariodeclasse.ui.login

data class LoginUIState (
    val errouLoginOuSenha: Boolean = false,
    val labelLogin:String = "Login",
    val labelSenha:String = "Senha",
    val loginSucesso: Boolean = false
)