package com.example.classdiary.ui.cadastro

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Criar Conta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        item {
            Foto(
                uri = cadastroUIState.fotoUri,
                onEditClick = { launcher.launch("image/*") },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            CampoTexto(
                value = cadastroUIState.nome,
                onValueChange = { cadastroViewModel.mudarTextoNome(it) },
                isError = cadastroUIState.nomeInvalido,
                label = cadastroUIState.labelNome,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            CampoTexto(
                value = cadastroUIState.email,
                onValueChange = { cadastroViewModel.mudarTextoEmail(it) },
                isError = cadastroUIState.emailInvalido,
                label = cadastroUIState.labelEmail,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            CampoTexto(
                value = cadastroUIState.senha,
                onValueChange = { cadastroViewModel.mudarTextoSenha(it) },
                isError = cadastroUIState.senhaInvalida,
                label = cadastroUIState.labelSenha,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            CampoTexto(
                value = cadastroUIState.curso,
                onValueChange = { cadastroViewModel.mudarTextoCurso(it) },
                isError = cadastroUIState.cursoInvalido,
                label = cadastroUIState.labelCurso,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(Modifier.height(8.dp))
        }

        item {
            Button(
                onClick = {
                    cadastroViewModel.cadastrar()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    fontSize = 16.sp
                )
            }
        }

        if (cadastroUIState.cadastroSucesso) {
            item {
                Text(
                    text = "✓ Cadastro realizado com sucesso!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        item {
            OutlinedButton(
                onClick = onCadastro,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Voltar ao Login",
                    fontSize = 16.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
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

        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Editar foto",
                tint = MaterialTheme.colorScheme.onPrimary,
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
        shape = RoundedCornerShape(8.dp)
    )
}


//@Composable
//@Preview(showSystemUi = true)
//fun PreviewCadastroScreen() {
//    // Para o preview funcionar, você pode precisar envolver em um Theme
//    // Ex: SeuAppTheme { CadastroScreen() }
//    CadastroScreen(onCadastro = )
//}