package com.example.classdiary.ui.cadastro

data class CadastroUIState (
    val emailInvalido: Boolean = false,
    val senhaInvalida: Boolean = false,
    val nomeInvalido: Boolean = false,
    val fotoInvalida: Boolean = false,
    val cadastroSucesso: Boolean = false,

    val labelNome:String = "Nome",
    val labelEmail:String = "Email",
    val labelSenha:String = "Senha",
    val labelFoto:String = "Foto",



    )
