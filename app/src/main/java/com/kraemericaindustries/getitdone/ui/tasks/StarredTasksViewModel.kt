package com.kraemericaindustries.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kraemericaindustries.getitdone.GetItDoneApplication
import com.kraemericaindustries.getitdone.data.TaskRepository
import com.kraemericaindustries.getitdone.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StarredTasksViewModel : ViewModel() {

    private val repository: TaskRepository = GetItDoneApplication.taskRepository

    suspend fun fetchTasks(): Flow<List<Task>> {
        return repository.getStarredTasks()
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}