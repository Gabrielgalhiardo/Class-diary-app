package com.example.classdiary.data

import android.net.Uri

data class Student(
    val nome: String,
    val email: String,
    val senha: String,
    val curso: String,
    val fotoUri: Uri? = null
)