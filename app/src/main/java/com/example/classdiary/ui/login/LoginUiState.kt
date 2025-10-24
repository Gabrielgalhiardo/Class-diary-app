package com.example.classdiary.ui.login

data class LoginUiState(
    val email: String = "",
    val senha: String = "",
    val loading: Boolean = false,
    val error: String? = null
)