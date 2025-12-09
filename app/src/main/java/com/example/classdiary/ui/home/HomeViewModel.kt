package com.example.classdiary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classdiary.data.DataSource
import com.example.classdiary.data.Student
import com.example.classdiary.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val dataSource: DataSource
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val me = repo.me()
                val users = dataSource.loadAStudents()
                _state.value = HomeUiState(
                    nome = me.nome,
                    email = me.email,
                    loading = false,
                    error = null,
                    users = users
                )
            } catch (e: Exception) {
                _state.value = HomeUiState(error = e.message, loading = false)
            }
        }
    }

    fun logout() = viewModelScope.launch {
        repo.logout()
    }
}
