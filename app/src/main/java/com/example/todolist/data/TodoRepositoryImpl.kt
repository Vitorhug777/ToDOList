package com.example.todolist.data

import com.example.todolist.Domain.ToDo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TodoRepository {

    private val taskCollection = firestore.collection("tasks")

    override suspend fun insert(title: String, description: String?) {
        val userId = auth.currentUser?.uid ?: return
        val task = hashMapOf(
            "title" to title,
            "description" to description,
            "isCompleted" to false,
            "userId" to userId
        )
        taskCollection.add(task).await()
    }

    // CORREÇÃO: Usando String para bater com a Interface
    override suspend fun updateCompleted(id: String, isCompleted: Boolean) {
        taskCollection.document(id).update("isCompleted", isCompleted).await()
    }

    override suspend fun delete(id: String) {
        taskCollection.document(id).delete().await()
    }

    override fun getAll(): Flow<List<ToDo>> = flow {
        val userId = auth.currentUser?.uid ?: ""
        val snapshot = taskCollection.whereEqualTo("userId", userId).get().await()
        emit(snapshot.toObjects<ToDo>())
    }

    override suspend fun getBy(id: String): ToDo? {
        return taskCollection.document(id).get().await().toObject(ToDo::class.java)
    }
}