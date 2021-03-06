package ru.lforb.work.todolist.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import ru.lforb.work.todolist.R
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.ViewModel.TodoViewModelFactory
import ru.lforb.work.todolist.databinding.ActivityMainBinding
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var viewModel: TodoViewModel
    @Inject lateinit var factory: TodoViewModelFactory
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        navController = Navigation.findNavController(this, R.id.navHost)
        setupActionBarWithNavController(navController)
        binding.toolbar.setupWithNavController(navController)

        viewModel = ViewModelProvider(this, factory).get(TodoViewModel::class.java)
        database = Firebase.database.reference


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}