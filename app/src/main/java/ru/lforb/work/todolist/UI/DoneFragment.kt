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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentDoneBinding


class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var adapter: DoneAdapter
    private lateinit var viewModel: TodoViewModel
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel.taskLive.observe(activity as MainActivity, Observer {
            binding.recyclerViewDone.adapter?.notifyDataSetChanged()
        })

        adapter = DoneAdapter(viewModel.tasksDone, this)
        binding.recyclerViewDone.adapter = adapter
        binding.recyclerViewDone.layoutManager = LinearLayoutManager(context)
    }

    fun deleteTask(position: Int) {
        viewModel.deleteTodo(viewModel.tasksDone[position])
        viewModel.tasksDone.removeAt(position)
        viewModel.taskLive.postValue(viewModel.tasks)
    }

    fun addToDo(position: Int) {
        viewModel.selectDone(viewModel.tasksDone[position].id, 0)
        viewModel.tasks.add(viewModel.tasksDone[position])
        viewModel.tasksDone.removeAt(position)
        viewModel.taskLive.postValue(viewModel.tasks)
    }
}