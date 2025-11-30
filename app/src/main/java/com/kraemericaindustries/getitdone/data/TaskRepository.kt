package com.kraemericaindustries.getitdone.data

import com.kraemericaindustries.getitdone.data.database.TaskDao
import com.kraemericaindustries.getitdone.data.database.TaskListDao
import com.kraemericaindustries.getitdone.data.model.Task
import com.kraemericaindustries.getitdone.data.model.TaskList
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao, private val taskListDao: TaskListDao) {

    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    suspend fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getStarredTasks(): Flow<List<Task>> {
        return taskDao.getStarredTasks()
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getTaskLists(): Flow<List<TaskList>> {
        return taskListDao.getAllLists()
    }

}