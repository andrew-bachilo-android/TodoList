package ru.lforb.work.todolist.Repository

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.lforb.work.todolist.Model.RemoteModel
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.UI.MainActivity
import javax.inject.Inject

class Repository  @Inject constructor(val remoteModel: RemoteModel){
    suspend fun addTask(listDb:String, userId: String, taskId:String, task: ToDo){
        remoteModel.addTask(listDb, userId, taskId, task)
    }

    suspend fun deleteTask(listDb:String, userId: String, taskId:String){
        remoteModel.deleteTask(listDb, userId, taskId)
    }

    suspend fun getAllTasks(listLocal:MutableList<ToDo>, listDb:String, userId: String, recyclerView: RecyclerView) {
        remoteModel.getAllTasks(listLocal, listDb, userId, recyclerView)
    }

    suspend fun signIn(email:String, password:String, activity: MainActivity, navController: NavController, idNav:Int){
        remoteModel.signIn(email, password, activity, navController, idNav)
    }

    suspend fun getUserId():String{
        return remoteModel.getUserId()
    }

    suspend fun getCurrentUser(): FirebaseUser? {
        return remoteModel.getCurrentUser()
    }

}