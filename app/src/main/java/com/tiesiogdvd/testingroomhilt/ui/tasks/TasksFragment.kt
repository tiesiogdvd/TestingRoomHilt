package com.tiesiogdvd.testingroomhilt.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

import androidx.recyclerview.widget.LinearLayoutManager
import com.tiesiogdvd.testingroomhilt.R
import com.tiesiogdvd.testingroomhilt.databinding.FragmentAddTaskBinding
import com.tiesiogdvd.testingroomhilt.databinding.FragmentTasksBinding
import com.tiesiogdvd.testingroomhilt.databinding.ItemTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {
    //Will be injected because of @AndroidEntryPoint
    val viewModel : TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTasksBinding.bind(view)
        val tasksAdapter = TasksAdapter()
        binding.fabAddTask.setOnClickListener(View.OnClickListener {
            tasksAdapter.notifyDataSetChanged();

        })
        binding.apply {
            recyclerviewTasks.apply {
                adapter = tasksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.tasks.observe(viewLifecycleOwner){
            tasksAdapter.submitList(it)
            //it is the default item name which is passed
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}