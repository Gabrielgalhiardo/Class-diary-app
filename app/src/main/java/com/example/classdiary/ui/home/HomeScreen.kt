package com.example.classdiary.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.classdiary.R

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        when {
            state.loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 4.dp
                    )
                }
            }
            state.error != null -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Erro: ${state.error}",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(24.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(Modifier.height(32.dp))

                    // Card de boas-vindas
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Avatar
                            Surface(
                                modifier = Modifier.size(100.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Avatar",
                                        modifier = Modifier.size(60.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Text(
                                text = "Bem-vindo!",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = state.nome.ifBlank { "Usuário" },
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium
                            )

                            if (state.email.isNotBlank()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Email",
                                        modifier = Modifier.size(18.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = state.email,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // Lista de usuários cadastrados
                    if (state.users.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Usuários Cadastrados",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.heightIn(max = 300.dp)
                                ) {
                                    items(state.users) { user ->
                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp)
                                            ) {
                                                Image(
                                                    painter = if (user.fotoUri != null) 
                                                        rememberAsyncImagePainter(user.fotoUri) 
                                                    else 
                                                        painterResource(R.drawable.avatar_icon),
                                                    contentDescription = "Foto",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clip(CircleShape)
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = user.nome,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                    Text(
                                                        text = user.email,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                    if (user.curso.isNotBlank()) {
                                                        Text(
                                                            text = user.curso,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.primary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Botão de logout
                    Button(
                        onClick = {
                            vm.logout()
                            onLogout()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sair",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Sair",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
