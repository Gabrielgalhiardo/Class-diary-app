package com.example.classdiary.ui.home

data class HomeUiState(
    val nome: String = "",
    val email: String = "",
    val loading: Boolean = true,
    val error: String? = null
)