package ru.lforb.work.todolist.Model

import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ru.lforb.work.todolist.UI.MainActivity
import java.lang.Exception
import javax.inject.Inject

class RemoteModel @Inject constructor() {
    private var database: DatabaseReference = Firebase.database.reference
    private  var auth: FirebaseAuth = Firebase.auth

    suspend fun addTask(listDb:String, userId: String, taskId:String, task:ToDo){
        database.child(listDb).child(userId).child(taskId).setValue(task)
    }

    suspend fun deleteTask(listDb:String, userId: String, taskId:String){
        database.child(listDb).child(userId).child(taskId).setValue(null)
    }

    suspend fun getAllTasks(listLocal:MutableList<ToDo>, listDb:String, userId: String, recycler:RecyclerView) {
        val myTasks = database.child(listDb).child(userId).orderByChild("date")

        myTasks.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listLocal.clear()
                for (postSnapshot in dataSnapshot.children) {
                    listLocal.add(postSnapshot.getValue<ToDo>()!!)
                }
                recycler.adapter?.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    //Authorization

    fun signIn(email:String, password:String, activity:MainActivity, navController: NavController, idNav:Int){
        try {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity){
                        if (it.isSuccessful) {

                            navController.navigate(idNav)
                            Toast.makeText(activity, "Вы вошли", Toast.LENGTH_SHORT).show()
                        }else{
                            signUp(email, password, activity, navController, idNav)
                        }
                    }

        }catch (e: Exception){
            Toast.makeText(activity, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
        }

    }

    private fun signUp(email:String, password:String, activity:MainActivity, navController: NavController, idNav:Int){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) {
                    if (it.isSuccessful) {
                        Toast.makeText(activity, "Вы зарегестрированы", Toast.LENGTH_SHORT).show()
                        navController.navigate(idNav)
                    } else {
                        if(password.length < 6){
                            Toast.makeText(activity, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(activity, "Введите правильно логин и пароль", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
    }

    fun getUserId():String{
        val user = auth.currentUser
        return user.uid
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }







}