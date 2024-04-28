package com.kraemericaindustries.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kraemericaindustries.getitdone.data.Task
import com.kraemericaindustries.getitdone.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = TasksAdapter(
            tasks = listOf(
                Task(title = "firsttit", description = "firstdescr"),
                Task(title = "secondtit", description = "seconddescr"),
                Task(title = "thirdtit", description = "thirddescr")
            )
        )
    }
}