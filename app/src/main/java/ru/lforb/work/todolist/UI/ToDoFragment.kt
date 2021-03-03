package ru.lforb.work.todolist.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.R
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentToDoBinding


class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var adapter: ToDoAdapter
    private lateinit var viewModel: TodoViewModel
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        database = Firebase.database.reference
        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.userLive.observe(activity as MainActivity, Observer {
            if (viewModel.user.isNotEmpty()){
                val myTasks = database.child("tasks").child(it).orderByChild("date")
                myTasks.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        viewModel.tasks.clear()
                        for (postSnapshot in dataSnapshot.children) {
                            viewModel.tasks.add(postSnapshot.getValue<ToDo>()!!)
                        }
                        viewModel.taskLive.postValue(viewModel.tasks)
                    }
                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

                val myTasksDone = database.child("tasksDone").child(it).orderByChild("date")
                myTasksDone.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        viewModel.tasksDone.clear()
                        for (postSnapshot in dataSnapshot.children) {
                            viewModel.tasksDone.add(postSnapshot.getValue<ToDo>()!!)
                        }
                        viewModel.taskLive.postValue(viewModel.tasksDone)
                    }
                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })
            }
        })

        viewModel.taskLive.observe(activity as MainActivity, Observer {
            binding.recyclerViewTodo.adapter?.notifyDataSetChanged()
            viewModel.tasks
        })


        viewModel.addLive.observe(activity as MainActivity, Observer {
            binding.recyclerViewTodo.adapter?.notifyDataSetChanged()
        })

        adapter = ToDoAdapter(viewModel.tasks, this)
        binding.recyclerViewTodo.adapter = adapter
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(context)


        binding.btnAddTask.setOnClickListener {
            navController.navigate(R.id.addTaskFragment)
        }



    }

    fun deleteTask(position: Int){
        //viewModel.deleteTodo(viewModel.tasks[position])
        viewModel.tasks[position].id?.let { database.child("tasks").child(viewModel.user).child(it).setValue(null) }
        viewModel.tasks[position].id?.let { Log.d("555", it) }
        viewModel.tasks.removeAt(position)
        viewModel.taskLive.postValue(viewModel.tasksDone)
    }

    fun addToDone(position: Int){
        //viewModel.tasks[position].id?.let { viewModel.selectDone(it, 1) }
        viewModel.tasks[position].id?.let { database.child("tasksDone").child(viewModel.user).child(it).setValue(viewModel.tasks[position]) }
        viewModel.tasks[position].id?.let { database.child("tasks").child(viewModel.user).child(it).setValue(null) }
            viewModel.tasksDone.add(viewModel.tasks[position])
            viewModel.tasks.removeAt(position)
            viewModel.taskLive.postValue(viewModel.tasksDone)
        }

    override fun onStart() {
        super.onStart()


    }
}