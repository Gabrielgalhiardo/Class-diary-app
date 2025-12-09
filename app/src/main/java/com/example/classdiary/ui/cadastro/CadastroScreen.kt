package com.example.classdiary.ui.cadastro

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.classdiary.R

@Composable
fun CadastroScreen(
    onCadastro: () -> Unit,
    cadastroViewModel: CadastroViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val cadastroUIState by cadastroViewModel.uiState.collectAsState()
    
    // LaunchedEffect para chamar onCadastro quando o cadastro for bem-sucedido
    LaunchedEffect(cadastroUIState.cadastroSucesso) {
        if (cadastroUIState.cadastroSucesso) {
            kotlinx.coroutines.delay(1500) // Aguarda 1.5s para mostrar a mensagem
            onCadastro()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        cadastroViewModel.onFotoChange(uri)
    }

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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Criar Conta",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Preencha os dados para se cadastrar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
            }

        item {
            Card(
                modifier = Modifier.padding(vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Foto(
                    uri = cadastroUIState.fotoUri,
                    onEditClick = { launcher.launch("image/*") },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CampoTexto(
                        value = cadastroUIState.nome,
                        onValueChange = { cadastroViewModel.mudarTextoNome(it) },
                        isError = cadastroUIState.nomeInvalido,
                        label = cadastroUIState.labelNome,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CampoTexto(
                        value = cadastroUIState.email,
                        onValueChange = { cadastroViewModel.mudarTextoEmail(it) },
                        isError = cadastroUIState.emailInvalido,
                        label = cadastroUIState.labelEmail,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CampoTexto(
                        value = cadastroUIState.senha,
                        onValueChange = { cadastroViewModel.mudarTextoSenha(it) },
                        isError = cadastroUIState.senhaInvalida,
                        label = cadastroUIState.labelSenha,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CampoTexto(
                        value = cadastroUIState.curso,
                        onValueChange = { cadastroViewModel.mudarTextoCurso(it) },
                        isError = cadastroUIState.cursoInvalido,
                        label = cadastroUIState.labelCurso,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    cadastroViewModel.cadastrar()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (cadastroUIState.cadastroSucesso) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "✓ Cadastro realizado com sucesso!",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        item {
            // Botão para voltar ao login
            OutlinedButton(
                onClick = onCadastro,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Voltar ao Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    }
}


@Composable
fun Foto(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    uri: Uri?
) {
    val imagePainter = if (uri != null) {
        rememberAsyncImagePainter(uri)
    } else {
        painterResource(R.drawable.avatar_icon)
    }

    Box(
        modifier = modifier
            .size(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

        FloatingActionButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(40.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Editar foto",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun CampoTexto(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = isError,
        singleLine = true,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = if (isError) {
            OutlinedTextFieldDefaults.colors(
                errorContainerColor = MaterialTheme.colorScheme.errorContainer
            )
        } else {
            OutlinedTextFieldDefaults.colors()
        }
    )
}


//@Composable
//@Preview(showSystemUi = true)
//fun PreviewCadastroScreen() {
//    // Para o preview funcionar, você pode precisar envolver em um Theme
//    // Ex: SeuAppTheme { CadastroScreen() }
//    CadastroScreen(onCadastro = )
//}