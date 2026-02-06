package com.example.todolist.ui.feature.AddEdit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel // IMPORTANTE: Use o hiltViewModel
import com.example.todolist.ui.UiEvent
import com.example.todolist.ui.theme.ToDOListTheme

@Composable
fun AddEditScreen(
    navigateBack: () -> Unit,
    // Deixe o Hilt injetar o ViewModel automaticamente aqui
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val title = viewModel.title
    val description = viewModel.description

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message,
                    )
                }
                is UiEvent.NavigateBack -> {
                    navigateBack()
                }
                is UiEvent.Navigate<*> -> {}
            }
        }
    }

    AddEditContent(
        title = title,
        description = description,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    title: String,
    description: String?,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(AddEditEvent.Save)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Salvar Tarefa")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues) // Corrigido: padding do Scaffold
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { onEvent(AddEditEvent.TitleChanged(it)) },
                label = { Text(text = "Título") }, // Melhor usar label que placeholder
                placeholder = { Text(text = "O que precisa ser feito?") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = { onEvent(AddEditEvent.DescriptionChanged(it)) },
                label = { Text(text = "Descrição") },
                placeholder = { Text(text = "Adicione mais detalhes...") }
            )
        }
    }
}

@Preview
@Composable
private fun AddEditContentPreview() {
    ToDOListTheme {
        AddEditContent(
            title = "Estudar para a UFU",
            description = "Revisar matéria de Sistemas de Informação",
            snackbarHostState = SnackbarHostState(),
            onEvent = {},
        )
    }
}