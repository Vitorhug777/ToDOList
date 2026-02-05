package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.todolist.navigation.TodoNavHost
import com.example.todolist.ui.theme.ToDOListTheme
import com.example.todolist.viewmodel.AuthViewModel // Importe o seu ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializa o AuthViewModel usando o delegate 'viewModels()'
        // Isso garante que o ViewModel sobreviva a mudanças de configuração (como girar a tela)
        val authViewModel: AuthViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
            ) {
                ToDOListTheme {
                    // 2. Passa o authViewModel para o seu NavHost
                    TodoNavHost(authViewModel = authViewModel)
                }
            }
        }
    }
}