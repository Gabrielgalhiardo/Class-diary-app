package com.example.classdiary.data

import android.content.Context
import kotlinx.coroutines.delay

class DataSource(private val context: Context) {
    suspend fun loadAStudents(): List<Student>{
        delay(1000)

            return listOf(
                Student(
                    nome = "Gabriel",
                    email = "gabriel@dominio.com",
                    senha = "senha123",
                    curso = "Desenvolvimento de Sistemas",
                    fotoUri = null
                ),
                Student(
                    nome = "Igor",
                    email = "igor@dominio.com",
                    senha = "igor456",
                    curso = "Desenvolvimento de Sistemas",
                    fotoUri = null
                ),
                Student(
                    nome = "Gustavo",
                    email = "gustavo@dominio.com",
                    senha = "guga789",
                    curso = "Desenvolvimento de Sistemas",
                    fotoUri = null
                ),
                Student(
                    nome = "Eric",
                    email = "eric@dominio.com",
                    senha = "eric000",
                    curso = "Desenvolvimento de Sistemas",
                    fotoUri = null
                )
            )
    }
}