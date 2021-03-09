package ru.lforb.work.todolist.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.Repository.Repository

class TodoViewModel(val repository: Repository) : ViewModel() {
    val scope = CoroutineScope(Dispatchers.IO)

    var tasks = mutableListOf<ToDo>()

    var tasksDone = mutableListOf<ToDo>()

    val taskLive: MutableLiveData<MutableList<ToDo>> by lazy {
        MutableLiveData<MutableList<ToDo>>()
    }

    fun insertTask(newToDo: ToDo) {
        scope.launch {
            repository.insertTask(newToDo)
            tasks.add(newToDo)
        }
    }

    fun getAllTodo() {
        scope.launch {
            tasks.addAll(repository.getAllTodo())
        }
    }

    fun getAllDone() {
        scope.launch {
            tasksDone.addAll(repository.getAllDone())
        }
    }

    fun selectDone(id: Int, isDone: Int) {
        scope.launch {
            repository.selectDone(id, isDone)
        }
    }

    fun deleteTodo(toDo: ToDo) {
        scope.launch {
            repository.deleteTodo(toDo)
        }
    }
}