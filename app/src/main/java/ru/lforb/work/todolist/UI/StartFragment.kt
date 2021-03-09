package ru.lforb.work.todolist.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.lforb.work.todolist.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private  val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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