package ru.lforb.work.todolist.UI

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.lforb.work.todolist.R
import ru.lforb.work.todolist.ViewModel.TodoViewModel
import ru.lforb.work.todolist.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private  val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tInflater = TransitionInflater.from(requireContext())
        exitTransition = tInflater.inflateTransition(R.transition.slide_up)
        enterTransition = tInflater.inflateTransition(R.transition.slide_down)
        viewModel = ViewModelProvider(activity as MainActivity).get(TodoViewModel::class.java)
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setUpTabs()
    }

    fun setUpTabs(){
        val adapter = PagerAdapter(childFragmentManager)
        adapter.addFragment(ToDoFragment(), "To Do")
        adapter.addFragment(DoneFragment(), "Done")
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}