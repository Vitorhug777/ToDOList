package com.example.todolist.ui.pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.navigation.ListRoute
import com.example.todolist.navigation.SignupRoute
import com.example.todolist.viewmodel.AuthState
import com.example.todolist.viewmodel.AuthViewModel

@Composable
fun LoginPage(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Estados para os campos de entrada
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observa o estado de autenticação do ViewModel
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // Gerenciamento de efeitos colaterais (Navegação e Erros)
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                // Navega para a lista e limpa a pilha para o usuário não voltar ao login ao apertar "voltar"
                navController.navigate(ListRoute) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bem-vindo", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de Senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Login
        Button(
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            // Desabilita o botão enquanto carrega para evitar cliques duplos
            enabled = authState.value != AuthState.Loading
        ) {
            if (authState.value is AuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(text = "Entrar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Navegação para a tela de cadastro
        TextButton(onClick = { navController.navigate(SignupRoute) }) {
            Text(text = "Não tem uma conta? Cadastre-se aqui")
        }
    }
}