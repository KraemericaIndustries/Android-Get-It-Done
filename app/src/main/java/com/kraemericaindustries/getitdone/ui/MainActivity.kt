package com.kraemericaindustries.getitdone.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.kraemericaindustries.getitdone.R
import com.kraemericaindustries.getitdone.data.model.TaskList
import com.kraemericaindustries.getitdone.databinding.ActivityMainBinding
import com.kraemericaindustries.getitdone.databinding.DialogAddTaskBinding
import com.kraemericaindustries.getitdone.databinding.DialogAddTaskListBinding
import com.kraemericaindustries.getitdone.databinding.TabButtonBinding
import com.kraemericaindustries.getitdone.ui.tasks.StarredTasksFragment
import com.kraemericaindustries.getitdone.ui.tasks.TasksFragment
import com.kraemericaindustries.getitdone.util.InputValidator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var currentTaskLists: List<TaskList> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleScope.launch {
                viewModel.getTaskLists().collectLatest { taskLists ->


                    currentTaskLists = taskLists

                    pager.adapter = PagerAdapter(this@MainActivity, taskLists)
                    pager.currentItem = 1
                    TabLayoutMediator(tabs, pager) { tab, position ->
                        when (position) {
                            0 -> tab.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.icon_star_filled)
                            taskLists.size + 1 -> {

                                val buttonBinding = TabButtonBinding.inflate(layoutInflater)
                                tab.customView = buttonBinding.root.apply {
                                 setOnClickListener { showAddTaskListDialog() }
                                }
                            }
                            else -> tab.text = taskLists[position - 1].name
                        }
                    }.attach()
                }
            }


            fab.setOnClickListener { showAddTaskDialog() }
            setContentView(root)
        }
    }

    private fun showAddTaskListDialog() {
        val dialogBinding = DialogAddTaskListBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.add_new_list_dialog_title))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.create_button_text)) { dialog, _ ->
                viewModel.addNewTaskList(dialogBinding.editTextListName.text?.toString())
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel_button_text)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {

            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            buttonSave.isEnabled = false

            editTextTaskTitle.addTextChangedListener { input ->

                buttonSave.isEnabled = InputValidator.isInputValid(input?.toString())
            }

            buttonShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }


            binding.pager.currentItem

            buttonSave.setOnClickListener {

                val selectedTaskListId = currentTaskLists[binding.pager.currentItem - 1].id

                viewModel.createTask(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString(),
                    listId = selectedTaskListId
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity, private val taskLists: List<TaskList>) : FragmentStateAdapter(activity) {

        override fun getItemCount() = taskLists.size + 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StarredTasksFragment()
                taskLists.size + 1 -> Fragment()
                else -> TasksFragment(taskLists[position - 1].id)
            }
        }
    }
}