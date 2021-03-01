package ru.lforb.work.todolist.Model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDataBase : RoomDatabase() {
    abstract fun myToDoDao() : ToDoDao
}