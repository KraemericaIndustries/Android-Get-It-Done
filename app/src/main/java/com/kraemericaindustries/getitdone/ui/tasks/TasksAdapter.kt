package com.kraemericaindustries.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.kraemericaindustries.getitdone.data.Task
import com.kraemericaindustries.getitdone.databinding.ItemTaskBinding

class TasksAdapter(private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.checkBox.isChecked = task.isComplete
            binding.toggleStar.isChecked = task.isStarred
            if (task.isComplete) {
                binding.textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewDetails.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTitle.paintFlags = 0
                binding.textViewDetails.paintFlags = 0
            }
            binding.textViewTitle.text = task.title
            binding.textViewDetails.text = task.description
            binding.checkBox.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> task.copy(isComplete = true)
                    else -> task.copy(isComplete = false)
                }
                listener.onTaskUpdated(updatedTask)
            }
            binding.toggleStar.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> task.copy(isStarred = true)
                    else -> task.copy(isStarred = false)
                }
                listener.onTaskUpdated(updatedTask)
            }
        }
    }

    interface TaskUpdatedListener {

        fun onTaskUpdated(task: Task)
    }
}