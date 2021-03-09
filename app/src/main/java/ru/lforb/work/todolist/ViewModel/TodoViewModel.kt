package ru.lforb.work.todolist.ViewModel


import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.Repository.Repository
import ru.lforb.work.todolist.UI.MainActivity


class TodoViewModel(val repository: Repository) : ViewModel(){
    var scope = CoroutineScope(Dispatchers.IO)

    var tasks = mutableListOf<ToDo>()

    var user = ""

    var tasksDone = mutableListOf<ToDo>()

    fun getAllTasks(recyclerView: RecyclerView){
        scope.launch {
            user = repository.getUserId()
            repository.getAllTasks(tasks, "tasks", user, recyclerView)
        }
    }

    fun getAllTasksDone(recyclerView: RecyclerView){
        scope.launch {
            user = repository.getUserId()
            repository.getAllTasks(tasksDone, "tasksDone", user, recyclerView)
        }
    }

    fun addTask(task:ToDo){
        scope.launch {
            repository.addTask("tasks", user, task.id!!, task)
        }
    }

    fun addTaskDone(task:ToDo){
        scope.launch {
            repository.addTask("tasksDone", user, task.id!!, task)
        }
    }

    fun deleteTask(task:ToDo){
        scope.launch {
            repository.deleteTask("tasks", user, task.id!!)
        }
    }

    fun deleteTaskDone(task:ToDo){
        scope.launch {
            repository.deleteTask("tasksDone", user, task.id!!)
        }
    }

     fun signIn(email:String, password:String, activity: MainActivity, navController: NavController, idNav:Int){
         scope.launch {
             repository.signIn(email, password, activity, navController, idNav)
         }
     }
}