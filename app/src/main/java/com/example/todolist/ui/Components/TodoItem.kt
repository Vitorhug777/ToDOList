package com.example.todolist.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.room.util.TableInfo
import com.example.todolist.Domain.ToDo
import com.example.todolist.Domain.ToDo1
import com.example.todolist.Domain.ToDo2
import com.example.todolist.ui.theme.ToDOListTheme

@Composable
fun TodoItem(
    toDo: ToDo,
    onCompletedChange: (Boolean) -> Unit,
    onitemClick: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    Surface(
        onClick = onitemClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    )    {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = toDo.isCompleted,
                onCheckedChange = onCompletedChange,

                )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = toDo.title,
                    style = MaterialTheme.typography.titleLarge
                )
                toDo.description?.let {


                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = toDo.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onitemClick

            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }

    }
}

@Preview
@Composable
private fun TodoItemPreview(){
    ToDOListTheme {
        TodoItem(
            toDo = ToDo1,
            onCompletedChange = {},
            onitemClick = {},
            onDeleteClicked = {}
        )
    }
}

@Preview
@Composable
private fun TodoItemCompletedPreview(){
    ToDOListTheme {
        TodoItem(
            toDo = ToDo2,
            onCompletedChange = {},
            onitemClick = {},
            onDeleteClicked = {}

        )
    }
}