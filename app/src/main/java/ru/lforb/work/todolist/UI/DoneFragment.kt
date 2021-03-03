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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        database = Firebase.database.reference
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

     fun deleteTask(position: Int){
        //viewModel.deleteTodo(viewModel.tasksDone[position])
         viewModel.tasksDone[position].id?.let { database.child("tasksDone").child(viewModel.user).child(it).setValue(null) }
        viewModel.tasksDone.removeAt(position)
         viewModel.taskLive.postValue(viewModel.tasks)
    }

    fun addToDo(position: Int){
        //viewModel.tasksDone[position].id?.let { viewModel.selectDone(it, 0) }
        viewModel.tasksDone[position].id?.let { database.child("tasks").child(viewModel.user).child(it).setValue(viewModel.tasksDone[position]) }
        viewModel.tasksDone[position].id?.let { database.child("tasksDone").child(viewModel.user).child(it).setValue(null) }
        viewModel.tasks.add(viewModel.tasksDone[position])
        viewModel.tasksDone.removeAt(position)
        viewModel.taskLive.postValue(viewModel.tasks)
    }
}