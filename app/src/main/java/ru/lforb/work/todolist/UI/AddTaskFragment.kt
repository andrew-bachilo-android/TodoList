package ru.lforb.work.todolist.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentAddTaskBinding


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.btnAdd.setOnClickListener {
            val title = binding.editTitle.text
            val description = binding.editDescription.text
            val task = ToDo(title = title.toString(), description = description.toString(), isDone = 0)
            viewModel.insertTask(task)
            (activity as MainActivity).navController.navigateUp()

        }
    }
}