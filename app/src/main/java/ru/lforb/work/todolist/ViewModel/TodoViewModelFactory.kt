package ru.lforb.work.todolist.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.lforb.work.todolist.Repository.Repository
import javax.inject.Inject
@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory @Inject constructor(val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(repository) as T
    }
}


