package com.rafaelcosta.app_modelo_login_jwt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.rememberNavController
import com.example.classdiary.ui.navigation.AppNavGraph
import com.example.classdiary.ui.theme.ClassDiaryTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClassDiaryTheme {
                val nav = rememberNavController()
                AppNavGraph(nav)
            }
        }
    }
}