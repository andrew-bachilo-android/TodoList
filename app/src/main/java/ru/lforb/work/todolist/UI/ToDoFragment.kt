package ru.lforb.work.todolist.UI

import android.os.Bundle
import android.transition.TransitionInflater
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val tInflater = TransitionInflater.from(requireContext())
        exitTransition = tInflater.inflateTransition(R.transition.slide_down)
        enterTransition = tInflater.inflateTransition(R.transition.slide_up)
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.recyclerViewTodo.adapter?.notifyDataSetChanged()

        adapter = ToDoAdapter(viewModel.tasks, this)
        binding.recyclerViewTodo.adapter = adapter
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(context)

        viewModel.getAllTasks(binding.recyclerViewTodo)

        binding.btnAddTask.setOnClickListener {
            navController.navigate(R.id.addTaskFragment)
        }
    }

    fun deleteTask(position: Int){
        viewModel.deleteTask(viewModel.tasks[position])
    }

    fun addToDone(position: Int){
        viewModel.addTaskDone(viewModel.tasks[position])
        viewModel.deleteTask(viewModel.tasks[position])
    }

    override fun onStart() {
        super.onStart()
        binding.btnAddTask.animate().apply {
            duration = 1200
            rotationY(720f)
        }.start()

    }
}