package com.kraemericaindustries.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kraemericaindustries.getitdone.data.model.Task
import com.kraemericaindustries.getitdone.databinding.ItemTaskBinding

class TasksAdapter(private val listener: TaskItemClickListener) :
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

        this.tasks = tasks.sortedBy {
            it.isComplete
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {

                checkBox.isChecked = task.isComplete
                toggleStar.isChecked = task.isStarred
                root.setOnLongClickListener {
                    listener.onTaskDeleted(task)
                    true
                }
                if (task.isComplete) {
                    textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDetails.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTitle.paintFlags = 0
                    textViewDetails.paintFlags = 0
                }

                textViewTitle.text = task.title
                if (task.description.isNullOrEmpty()) {
                    textViewDetails.visibility = View.GONE
                } else {
                    textViewDetails.text = task.description
                    textViewDetails.visibility = View.VISIBLE
                }

                checkBox.setOnClickListener {
                    val updatedTask = task.copy(isComplete = checkBox.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
                toggleStar.setOnClickListener {
                    val updatedTask = task.copy(isStarred = toggleStar.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
            }
        }
    }

    interface TaskItemClickListener {

        fun onTaskUpdated(task: Task)

        fun onTaskDeleted(task: Task)
    }
}