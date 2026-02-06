package com.example.todolist.ui.feature.AddEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TodoRepository
import com.example.todolist.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: TodoRepository // O Hilt vai entregar o TodoRepositoryImpl aqui
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }
            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }
            is AddEditEvent.Save -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        _uiEvent.send(UiEvent.ShowSnackbar(message = "O título não pode estar vazio"))
                        return@launch
                    }

                    // CHAMA O REPOSITÓRIO (Que agora deve estar configurado para o Firebase)
                    repository.insert(title, description)

                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }
        }
    }
}