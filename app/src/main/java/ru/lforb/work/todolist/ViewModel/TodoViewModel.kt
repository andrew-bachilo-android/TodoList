package ru.lforb.work.todolist.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.lforb.work.todolist.Model.ToDo

class TodoViewModel() : ViewModel(){

    var tasks = mutableListOf<ToDo>()

    var user = ""

    val userLive: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var tasksDone = mutableListOf<ToDo>()

    val taskLive: MutableLiveData<MutableList<ToDo>> by lazy {
        MutableLiveData<MutableList<ToDo>>()
    }
    val addLive: MutableLiveData<ToDo> by lazy {
        MutableLiveData<ToDo>()
    }


}