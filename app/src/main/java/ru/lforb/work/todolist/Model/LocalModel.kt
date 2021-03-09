package ru.lforb.work.todolist.Model

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalModel @Inject constructor(@ApplicationContext context: Context) {
    private val dataBase: ToDoDataBase = Room.databaseBuilder(
            context,
            ToDoDataBase::class.java, "todo_db"
    ).build()

    suspend fun insertTask(todo: ToDo) {
        dataBase.myToDoDao().insertTask(todo)
    }

    suspend fun getAllTodo(): MutableList<ToDo> {
        return dataBase.myToDoDao().getAllTodo()
    }

    suspend fun getAllDone(): MutableList<ToDo> {
        return dataBase.myToDoDao().getAllDone()
    }

    suspend fun selectDone(id: Int, isDone: Int) {
        return dataBase.myToDoDao().selectDone(id, isDone)
    }

    suspend fun deleteTodo(todo: ToDo) {
        dataBase.myToDoDao().deleteTodo(todo)
    }


}