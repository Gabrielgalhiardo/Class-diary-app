package com.example.classdiary.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.classdiary.ui.cadastro.CadastroScreen
import com.example.classdiary.ui.home.HomeScreen
import com.example.classdiary.ui.login.LoginScreen
import com.example.classdiary.ui.session.AuthState
import com.example.classdiary.ui.session.AuthStateViewModel


@Composable
fun AppNavGraph(nav: NavHostController) {
    val vm: AuthStateViewModel = hiltViewModel()
    val authState = vm.state.collectAsState().value

    NavHost(nav, startDestination = when (authState) {
        AuthState.Authenticated -> "cadastro"
        else -> "login"
    }) {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                nav.navigate(route = "home") {
                    popUpTo(route = "login") { inclusive = true }
                }
            },
                onCadastro = {
                    nav.navigate("cadastro"){
                        popUpTo("login"){
                            inclusive = true
                        }
                    }
                }

            )
        }
        composable("cadastro") {
            CadastroScreen(onCadastro = {
                nav.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("home") {
            HomeScreen(onLogout = {
                nav.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            })
        }

    }
}
