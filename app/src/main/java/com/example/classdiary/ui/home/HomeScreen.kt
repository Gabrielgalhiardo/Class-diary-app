package com.example.classdiary.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    vm: HomeViewModel = hiltViewModel()
)
{
    val state by vm.state.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.loading -> CircularProgressIndicator()
            state.error != null -> Text("Erro: ${state.error}")
            else -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Bem-vindo, ${state.nome}", style = MaterialTheme.typography.headlineMedium)
                Text(state.email)
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    vm.logout()
                    onLogout()
                }) { Text("Sair") }
            }
        }
    }
}
