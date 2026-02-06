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
import androidx.compose.runtime.collectAsState // Importante para observar o Flow do Room
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.example.todolist.viewmodel.ListViewModel


@Composable
fun ListScreen(
    authViewModel: AuthViewModel,
    listViewModel: ListViewModel, // Adicionado para buscar as tarefas do banco
    navController: NavController,
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    // Observa o estado de autenticação do Firebase
    val authState = authViewModel.authState.observeAsState()

    // Observa a lista de tarefas do banco de dados Room
    val todos by listViewModel.todos.collectAsState(initial = emptyList())

    // Redireciona para o Login se o usuário deslogar
    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate(LoginRoute) {
                popUpTo(0)
            }
        }
    }

    ListContent(
        todos = todos, // Agora usa a lista real do ViewModel
        onAddItemClick = navigateToAddEditScreen,
        onSignOutClick = { authViewModel.signOut() },
        onToggleTodo = { listViewModel.adicionarTarefa(it) }, // Ação de marcar/desmarcar
        onDeleteTodo = { listViewModel.deletarTarefa(it) }  // Ação de excluir
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    todos: List<ToDo>,
    onAddItemClick: (id: Long?) -> Unit,
    onSignOutClick: () -> Unit,
    onToggleTodo: (ToDo) -> Unit,
    onDeleteTodo: (ToDo) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                actions = {
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
        if (todos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Nenhuma tarefa encontrada.")
            }
        } else {
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
                        onCompletedChange = { onToggleTodo(todo) },
                        onitemClick = { onAddItemClick(todo.id.toLong()) },
                        onDeleteClicked = { onDeleteTodo(todo) }
                    )

                    if (index < todos.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
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
            onSignOutClick = {},
            onToggleTodo = {},
            onDeleteTodo = {}
        )
    }
}