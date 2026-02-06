package com.example.todolist.data

import com.example.todolist.Domain.ToDo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(title: String, description: String?)
    suspend fun updateCompleted(id: String, isCompleted: Boolean) // ID String
    suspend fun delete(id: String) // ID String
    fun getAll(): Flow<List<ToDo>>
    suspend fun getBy(id: String): ToDo? // ID String
}