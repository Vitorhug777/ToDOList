package com.example.todolist.data

import com.example.todolist.Domain.ToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao,
) : TodoRepository {

    override suspend fun insert(title: String, description: String?) {
        val entity = TodoEntity(
            title = title,
            description = description,
            isCompleted = false,
        )

        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existentEntity = dao.getBy(id) ?: return
        val updatedEntity = existentEntity.copy(isCompleted = isCompleted)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }

    override fun getAll(): Flow<List<ToDo>> {
        return dao.getAll().map {
            it.map { entity ->
                ToDo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }

    override suspend fun getBy(id: Long): ToDo? {
        return dao.getBy(id)?.let { entity ->
            ToDo(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                isCompleted = entity.isCompleted
            )
        }
    }
}