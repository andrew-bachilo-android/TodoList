package ru.lforb.work.todolist.Model



data class ToDo(
    val id: String? = null,
    val title:String? = null,
    val description:String? = null,
    val done:Int? = null,
    val date: Long? = null
)
