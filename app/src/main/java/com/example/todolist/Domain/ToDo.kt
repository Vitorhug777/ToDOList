package com.example.todolist.Domain

data class ToDo(
    val id: String = "", // O Firebase usa Strings alfanuméricas
    val title: String = "",
    val description: String? = "",
    val isCompleted: Boolean = false,
    val userId: String = ""
)

// CORREÇÃO: Use aspas para transformar em String
val ToDo1 = ToDo(
    id = "1",
    title = "Todo1",
    description = "Draft the proposal and send it for review by Friday.",
    isCompleted = false
)

val ToDo2 = ToDo(
    id = "2",
    title = "ToDo2",
    description = "Draft the proposal and send it for review by Friday.",
    isCompleted = true
)

val ToDo3 = ToDo(
    id = "3",
    title = "ToDo3",
    description = "Draft the proposal and send it for review by Friday.",
    isCompleted = true
)