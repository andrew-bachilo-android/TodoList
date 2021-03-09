package ru.lforb.work.todolist.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {
    @Query("SELECT * FROM tasks WHERE isDone = 0")
    suspend fun getAllTodo(): MutableList<ToDo>

    @Query("SELECT * FROM tasks WHERE isDone = 1")
    suspend fun getAllDone(): MutableList<ToDo>

    @Insert
    suspend fun insertTask(todo: ToDo)

    @Query("UPDATE tasks SET isDone = :isDone WHERE id = :id")
    suspend fun selectDone(id: Int, isDone: Int)

    @Delete
    suspend fun deleteTodo(todo: ToDo)
}