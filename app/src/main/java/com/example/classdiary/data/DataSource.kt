package com.example.classdiary.data

import android.content.Context
import com.example.classdiary.data.local.UserStore
import kotlinx.coroutines.flow.first

class DataSource(private val userStore: UserStore) {
    
    suspend fun loadAStudents(): List<Student> {
        val users = userStore.users.first()
        // Se não houver usuários, inicializa com usuários mock
        if (users.isEmpty()) {
            val mockUsers = listOf(
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
            userStore.saveUsers(mockUsers)
            return mockUsers
        }
        return users
    }
    
    suspend fun saveStudent(student: Student) {
        val currentUsers = userStore.users.first().toMutableList()
        // Verifica se o email já existe
        val existingIndex = currentUsers.indexOfFirst { it.email == student.email }
        if (existingIndex >= 0) {
            currentUsers[existingIndex] = student
        } else {
            currentUsers.add(student)
        }
        userStore.saveUsers(currentUsers)
    }
}