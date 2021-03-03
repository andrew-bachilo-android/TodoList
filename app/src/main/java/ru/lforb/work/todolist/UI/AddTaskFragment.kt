package ru.lforb.work.todolist.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentAddTaskBinding
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Date.UTC


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
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
        database = Firebase.database.reference
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        var idOne = UUID.randomUUID()
        binding.btnAdd.setOnClickListener {
            val currentTime = Calendar.getInstance().getTimeInMillis()
            Log.d("hhh", currentTime.toString())
            val title = binding.editTitle.text
            val description = binding.editDescription.text
            val task = ToDo(id = idOne.toString(), title = title.toString(), description = description.toString(), done = 0, date = currentTime)
            database.child("tasks").child(viewModel.user).child(idOne.toString()).setValue(task)
            Log.d("555", task.id.toString())

            (activity as MainActivity).navController.navigateUp()

        }
    }


}