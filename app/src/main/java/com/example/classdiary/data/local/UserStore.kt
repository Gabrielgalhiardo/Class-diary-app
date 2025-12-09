package com.example.classdiary.data.local

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.classdiary.data.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

// Classe auxiliar para serialização sem Uri
private data class StudentData(
    val nome: String,
    val email: String,
    val senha: String,
    val curso: String,
    val fotoUriString: String? = null
) {
    fun toStudent(): Student = Student(
        nome = nome,
        email = email,
        senha = senha,
        curso = curso,
        fotoUri = fotoUriString?.let { Uri.parse(it) }
    )
    
    companion object {
        fun fromStudent(student: Student): StudentData = StudentData(
            nome = student.nome,
            email = student.email,
            senha = student.senha,
            curso = student.curso,
            fotoUriString = student.fotoUri?.toString()
        )
    }
}

val Context.userDataStore by preferencesDataStore("users_prefs")

class UserStore(private val context: Context) {
    companion object {
        private val USERS_KEY = stringPreferencesKey("users_list")
        private val CURRENT_USER_KEY = stringPreferencesKey("current_user")
    }

    private val gson = Gson()

    val users: Flow<List<Student>> = context.userDataStore.data.map { prefs ->
        val usersJson = prefs[USERS_KEY] ?: "[]"
        try {
            val type = object : TypeToken<List<StudentData>>() {}.type
            val studentsData = gson.fromJson<List<StudentData>>(usersJson, type) ?: emptyList()
            studentsData.map { it.toStudent() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    val currentUser: Flow<Student?> = context.userDataStore.data.map { prefs ->
        val userJson = prefs[CURRENT_USER_KEY]
        if (userJson != null) {
            try {
                val studentData = gson.fromJson<StudentData>(userJson, StudentData::class.java)
                studentData.toStudent()
            } catch (e: Exception) {
                null
            }
        } else null
    }

    suspend fun saveUsers(users: List<Student>) {
        context.userDataStore.edit { prefs ->
            val studentsData = users.map { StudentData.fromStudent(it) }
            val usersJson = gson.toJson(studentsData)
            prefs[USERS_KEY] = usersJson
        }
    }

    suspend fun addUser(user: Student) {
        val currentUsers = users.first()
        val updatedUsers = currentUsers + user
        saveUsers(updatedUsers)
    }

    suspend fun setCurrentUser(user: Student?) {
        context.userDataStore.edit { prefs ->
            if (user != null) {
                val studentData = StudentData.fromStudent(user)
                val userJson = gson.toJson(studentData)
                prefs[CURRENT_USER_KEY] = userJson
            } else {
                prefs.remove(CURRENT_USER_KEY)
            }
        }
    }

    suspend fun clearCurrentUser() {
        context.userDataStore.edit { prefs ->
            prefs.remove(CURRENT_USER_KEY)
        }
    }
}

