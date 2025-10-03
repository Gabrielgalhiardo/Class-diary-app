package com.example.classdiary.ui.cadastro

// Imports para Layout e Componentes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classdiary.R // Lembre-se de importar o R do seu projeto

@Composable
fun CadastroScreen(cadastroViewModel: CadastroViewModel = viewModel()) {
    val cadastroUIState by cadastroViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 33.sp,
            text = "Cadastrar"
        )
        Spacer(Modifier.height(30.dp))

        // Chamada para o componente da Foto com o ícone de edição
        Foto(
            onEditClick = {
                // Lógica para quando o usuário clicar em editar a foto
                println("Clicou para editar a foto!")
            }
        )
        Spacer(Modifier.height(30.dp))

        // Campos de Texto
        CampoTexto(
            value = cadastroViewModel.nome,
            onValueChange = { cadastroViewModel.mudarTextoNome(it) },
            isError = cadastroUIState.nomeInvalido,
            label = cadastroUIState.labelNome
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            value = cadastroViewModel.email,
            onValueChange = { cadastroViewModel.mudarTextoEmail(it) },
            isError = cadastroUIState.emailInvalido,
            label = cadastroUIState.labelEmail
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            value = cadastroViewModel.senha,
            onValueChange = { cadastroViewModel.mudarTextoSenha(it) },
            isError = cadastroUIState.senhaInvalida,
            label = cadastroUIState.labelSenha
        )
        Spacer(Modifier.height(30.dp))

        // Botão de Cadastro
        BotaoCadastrar(
            onClick = {
                cadastroViewModel.cadastrar()
            }
        )

        // Mensagem de sucesso
        if (cadastroUIState.cadastroSucesso) {
            Spacer(Modifier.height(16.dp))
            Text("Cadastro realizado com sucesso!")
        }
    }
}

@Composable
fun Foto(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    Box(modifier = modifier) {
        // Camada 1: A Imagem de Perfil
        Image(
            painter = painterResource(R.drawable.eric), // Sua imagem de exemplo
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        // Camada 2: O Ícone de Edição
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Editar foto",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd) // Alinha no canto inferior direito
                .background(MaterialTheme.colorScheme.primary, CircleShape) // Fundo circular
                .clip(CircleShape)
                .clickable { onEditClick() } // Ação de clique
                .padding(8.dp) // Espaçamento interno (dentro do círculo)
                .size(24.dp)
        )
    }
}

@Composable
fun CampoTexto(
    value: String = "",
    onValueChange: (String) -> Unit,
    label: String = "",
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        isError = isError,
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun BotaoCadastrar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = "Cadastrar"
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewCadastroScreen() {
    CadastroScreen()
}