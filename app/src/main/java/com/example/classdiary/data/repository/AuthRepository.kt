package com.example.classdiary.data.repository


import com.example.classdiary.data.Student
import com.example.classdiary.data.local.TokenStore
import com.example.classdiary.data.local.UserStore
import com.example.classdiary.data.remote.ApiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val store: TokenStore,
    private val userStore: UserStore
) {

    suspend fun login(email: String, senha: String): Boolean {
        val users = userStore.users.first()
        val user = users.find { it.email == email && it.senha == senha }
        
        if (user != null) {
            // Salva token mock e usuário atual
            store.saveToken("mock_token_${user.email}")
            userStore.setCurrentUser(user)
            return true
        }
        return false
    }

    suspend fun me(): Student {
        val currentUser = userStore.currentUser.first()
        return currentUser ?: Student(
            nome = "Usuário",
            email = "",
            senha = "",
            curso = ""
        )
    }

    suspend fun logout() {
        store.clearToken()
        userStore.clearCurrentUser()
    }

    fun tokenFlow() = store.token
}