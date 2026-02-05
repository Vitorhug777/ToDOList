package com.example.todolist.ui.feature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist.Domain.ToDo
import com.example.todolist.Domain.ToDo1
import com.example.todolist.Domain.ToDo2
import com.example.todolist.Domain.ToDo3
import com.example.todolist.navigation.LoginRoute
import com.example.todolist.ui.Components.TodoItem
import com.example.todolist.ui.theme.ToDOListTheme
import com.example.todolist.viewmodel.AuthState
import com.example.todolist.viewmodel.AuthViewModel

@Composable
fun ListScreen(
    authViewModel: AuthViewModel, // Parâmetro para gerenciar login/logout
    navController: NavController,  // Parâmetro para navegação
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    val authState = authViewModel.authState.observeAsState()

    // Lógica de Redirecionamento: Se o usuário deslogar, volta para a tela de Login
    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate(LoginRoute) {
                popUpTo(0) // Limpa o histórico para impedir o usuário de voltar
            }
        }
    }

    // Nota: Aqui você deve conectar o seu ViewModel de tarefas para substituir o 'emptyList()'
    ListContent(
        todos = emptyList(), // Substitua pelos dados do seu Room/Database futuramente
        onAddItemClick = navigateToAddEditScreen,
        onSignOutClick = { authViewModel.signOut() } // Passa a ação de logout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    todos: List<ToDo>,
    onAddItemClick: (id: Long?) -> Unit,
    onSignOutClick: () -> Unit // Nova ação para o botão da barra superior
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                actions = {
                    // Botão de Sair na TopAppBar conforme orientado no vídeo
                    IconButton(onClick = onSignOutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sair"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddItemClick(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    toDo = todo,
                    onCompletedChange = {},
                    onitemClick = { onAddItemClick(todo.id) },
                    onDeleteClicked = {}
                )

                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListContentPreview() {
    ToDOListTheme {
        ListContent(
            todos = listOf(ToDo1, ToDo2, ToDo3),
            onAddItemClick = {},
            onSignOutClick = {}
        )
    }
}