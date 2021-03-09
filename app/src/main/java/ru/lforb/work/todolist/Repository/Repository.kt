package ru.lforb.work.todolist.Repository

import ru.lforb.work.todolist.Model.LocalModel
import ru.lforb.work.todolist.Model.ToDo
import javax.inject.Inject

class Repository @Inject constructor(val localModel: LocalModel) {
    suspend fun insertTask(newToDo: ToDo) {
        localModel.insertTask(newToDo)
    }

    suspend fun getAllTodo(): MutableList<ToDo> {
        return localModel.getAllTodo()
    }

    suspend fun getAllDone(): MutableList<ToDo> {
        return localModel.getAllDone()
    }

    suspend fun selectDone(id: Int, isDone: Int) {
        localModel.selectDone(id, isDone)
    }

    suspend fun deleteTodo(toDo: ToDo) {
        localModel.deleteTodo(toDo)
    }
}