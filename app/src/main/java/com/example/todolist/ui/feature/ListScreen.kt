package com.example.todolist.ui.feature

import android.window.SplashScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.Domain.ToDo
import com.example.todolist.Domain.ToDo1
import com.example.todolist.Domain.ToDo2
import com.example.todolist.Domain.ToDo3
import com.example.todolist.ui.Components.TodoItem
import com.example.todolist.ui.theme.ToDOListTheme

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit,


){
    ListContent(todos = emptyList(),
    onAddItemClick = navigateToAddEditScreen,
    )
}

@Composable
fun ListContent(
    todos: List<ToDo>,
    onAddItemClick: (id: Long?) -> Unit
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {onAddItemClick(null)}){
                Icon(Icons.Default.Add, contentDescription = "Add")

            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues),
            contentPadding = (PaddingValues(16.dp))

        ) {
            itemsIndexed(todos) {index, todo ->
                TodoItem(
                    toDo = todo,
                    onCompletedChange = {},
                    onitemClick = {},
                    onDeleteClicked = {}
                )

                if(index < todos.lastIndex){
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable

private fun ListContentPreview(){
    ToDOListTheme {
        ListContent(
            todos = listOf(
                ToDo1,
                ToDo2,
                ToDo3

            ),
            onAddItemClick = {},

        )
    }


}