package ru.lforb.work.todolist.UI

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.lforb.work.todolist.R
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentSignBinding
import java.lang.Exception


class SignFragment : Fragment() {
    private var _binding: FragmentSignBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var viewModel: TodoViewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        _binding = FragmentSignBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.btnLogin.setOnClickListener {

            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            try {
                auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity as MainActivity) { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    viewModel.user = user.uid
                                    Toast.makeText(activity, "Вы вошли", Toast.LENGTH_SHORT).show()
                                    navController.navigate(R.id.startFragment)
                                } else {
                                    auth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(activity as MainActivity) { task ->
                                                if (task.isSuccessful) {
                                                    val user = auth.currentUser
                                                    Toast.makeText(activity, "Вы зарегестрированы", Toast.LENGTH_SHORT).show()
                                                    navController.navigate(R.id.startFragment)
                                                } else {
                                                    Toast.makeText(activity, "Введите правильно логин и пароль", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                }
                            }
            }catch (e:Exception){
                Toast.makeText(activity, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            binding.editTextEmail.setText(currentUser.email)


        }
    }



}