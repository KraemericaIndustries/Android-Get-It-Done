package com.kraemericaindustries.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kraemericaindustries.getitdone.data.GetItDoneDatabase
import com.kraemericaindustries.getitdone.data.TaskDao
import com.kraemericaindustries.getitdone.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private val taskDao: TaskDao by lazy {
        GetItDoneDatabase.createDatabase(requireContext()).getTaskDao()
    }

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
        thread {
            val tasks = taskDao.getAllTasks()
            requireActivity().runOnUiThread {
                binding.recyclerView.adapter = TasksAdapter(tasks = tasks)
            }
        }
    }
}