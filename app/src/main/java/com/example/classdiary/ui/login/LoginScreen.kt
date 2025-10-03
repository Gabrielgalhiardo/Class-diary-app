package com.rafaelcosta.diariodeclasse.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    val loginUIState by loginViewModel.uiState.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            fontSize = 33.sp,
            text = "Logar")
        Spacer(Modifier.size(30.dp))
        CampoTextoLoginSenha(
            value = loginViewModel.login,
            onValueChange = { loginViewModel.mudarTextoLogin(it) },
            isError = loginUIState.errouLoginOuSenha,
            label = loginUIState.labelLogin
        )
        Spacer(Modifier.size(30.dp))
        CampoTextoLoginSenha(
            value = loginViewModel.senha,
            onValueChange = { loginViewModel.mudarTextoSenha(it) },
            isError = loginUIState.errouLoginOuSenha,
            label = loginUIState.labelSenha
        )
        Spacer(Modifier.size(30.dp))

        BotaoLogar(
            onClick = {
                loginViewModel.logar()
            }
        )
        if(loginUIState.loginSucesso)
            Text("Logoooou!!!!!")

    }
}

@Composable
fun CampoTextoLoginSenha(
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
fun BotaoLogar(
    onClick:()-> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
       Text(
           text =  "Entrar"
       )
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewLogin(){
    LoginScreen()
}