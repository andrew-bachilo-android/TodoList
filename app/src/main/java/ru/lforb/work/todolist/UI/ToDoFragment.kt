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
import ru.lforb.work.todolist.R
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentToDoBinding
import java.lang.Exception


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
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)

        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.taskLive.observe(activity as MainActivity, Observer {
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
        viewModel.deleteTodo(viewModel.tasks[position])
        viewModel.tasks.removeAt(position)
        viewModel.taskLive.postValue(viewModel.tasksDone)
    }

    fun addToDone(position: Int){
            viewModel.selectDone(viewModel.tasks[position].id, 1)
            viewModel.tasksDone.add(viewModel.tasks[position])
            viewModel.tasks.removeAt(position)
            viewModel.taskLive.postValue(viewModel.tasksDone)
    }
}