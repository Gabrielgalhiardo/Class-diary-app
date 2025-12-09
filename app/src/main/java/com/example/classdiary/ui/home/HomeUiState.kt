package com.example.classdiary.ui.home

import com.example.classdiary.data.Student

data class HomeUiState(
    val nome: String = "",
    val email: String = "",
    val loading: Boolean = true,
    val error: String? = null,
    val users: List<Student> = emptyList()
)