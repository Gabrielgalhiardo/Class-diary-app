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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.classdiary.R

@Composable
fun CadastroScreen(
    onCadastro: () -> Unit,
    cadastroViewModel: CadastroViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val cadastroUIState by cadastroViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        cadastroViewModel.carregarStudents()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        cadastroViewModel.onFotoChange(uri)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                fontSize = 33.sp,
                text = "Cadastrar"
            )
            Spacer(Modifier.height(30.dp))
        }

        item {
            Foto(
                uri = cadastroUIState.fotoUri,
                onEditClick = { launcher.launch("image/*") }
            )
            Spacer(Modifier.height(30.dp))
        }

        item {
            CampoTexto(
                value = cadastroUIState.nome,
                onValueChange = { cadastroViewModel.mudarTextoNome(it) },
                isError = cadastroUIState.nomeInvalido,
                label = cadastroUIState.labelNome,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        item {
            CampoTexto(
                value = cadastroUIState.email,
                onValueChange = { cadastroViewModel.mudarTextoEmail(it) },
                isError = cadastroUIState.emailInvalido,
                label = cadastroUIState.labelEmail,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        item {
            CampoTexto(
                value = cadastroUIState.senha,
                onValueChange = { cadastroViewModel.mudarTextoSenha(it) },
                isError = cadastroUIState.senhaInvalida,
                label = cadastroUIState.labelSenha,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        item {
            CampoTexto(
                value = cadastroUIState.curso,
                onValueChange = { cadastroViewModel.mudarTextoCurso(it) },
                isError = cadastroUIState.cursoInvalido,
                label = cadastroUIState.labelCurso,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(30.dp))
        }

        item {
            BotaoCadastrar(
                onCadastro = { onCadastro },
                onClick = { cadastroViewModel.cadastrar() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        if (cadastroUIState.cadastroSucesso) {
            item {
                Text(
                    "Cadastro realizado com sucesso!",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
            }
        }

        if (cadastroUIState.isLoading) {
            item {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Carregando usuários...")
            }
        } else {
            if (cadastroUIState.students.isNotEmpty()) {
                item {
                    Text("Usuários cadastrados:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            items(cadastroUIState.students) { user ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Image(
                        painter = if (user.fotoUri != null) rememberAsyncImagePainter(user.fotoUri) else painterResource(R.drawable.avatar_icon),
                        contentDescription = "Foto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${user.nome} - ${user.email}")
                }
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
            .size(150.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Editar foto",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .clickable { onEditClick() }
                .padding(8.dp)
                .size(24.dp)
        )
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
        modifier = modifier
    )
}

@Composable
fun BotaoCadastrar(
    onCadastro: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            onClick
            onCadastro
        },
        modifier =
            modifier
                .padding(20.dp)
                .height(38.dp)
    ) {
        Text(text = "Cadastrar", fontSize = 16.sp)
    }
}

//@Composable
//@Preview(showSystemUi = true)
//fun PreviewCadastroScreen() {
//    // Para o preview funcionar, você pode precisar envolver em um Theme
//    // Ex: SeuAppTheme { CadastroScreen() }
//    CadastroScreen(onCadastro = )
//}