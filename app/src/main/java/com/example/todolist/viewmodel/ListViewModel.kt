package com.example.todolist.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.Domain.ToDo // Verifique se o caminho do seu modelo ToDo é este
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    // Esta é a lista que a sua ListScreen está tentando ler (o .todos)
    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos: StateFlow<List<ToDo>> = _todos

    init {
        buscarTarefas()
    }

    private fun buscarTarefas() {
        viewModelScope.launch {
            // Aqui você chamaria o seu DAO ou Repository do Room
            // Exemplo: _todos.value = repository.getAllTodos()

            // Por enquanto, uma lista vazia ou mockada para o erro sumir:
            _todos.value = listOf()
        }
    }

    // Funções que você vai precisar para a sua To-Do List
    fun adicionarTarefa(tarefa: ToDo) {
        viewModelScope.launch {
            // lógica para salvar no Room
        }
    }

    fun deletarTarefa(tarefa: ToDo) {
        viewModelScope.launch {
            // lógica para deletar do Room
        }
    }
}