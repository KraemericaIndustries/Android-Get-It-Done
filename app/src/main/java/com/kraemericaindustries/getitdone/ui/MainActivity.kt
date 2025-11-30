package com.kraemericaindustries.getitdone.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.kraemericaindustries.getitdone.R
import com.kraemericaindustries.getitdone.databinding.ActivityMainBinding
import com.kraemericaindustries.getitdone.databinding.DialogAddTaskBinding
import com.kraemericaindustries.getitdone.ui.tasks.StarredTasksFragment
import com.kraemericaindustries.getitdone.ui.tasks.TasksFragment
import com.kraemericaindustries.getitdone.util.InputValidator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleScope.launch {
                viewModel.getTaskLists().collectLatest { taskLists ->

                    pager.adapter = PagerAdapter(this@MainActivity, taskLists.size + 2)
                    pager.currentItem = 1
                    TabLayoutMediator(tabs, pager) { tab, position ->
                        when (position) {
                            0 -> tab.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.icon_star_filled)
                            taskLists.size + 1 -> tab.customView = Button(this@MainActivity).apply {
                                text = "Add New List"
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

            buttonSave.setOnClickListener {
                viewModel.createTask(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString()
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity, private val numberOfPages: Int) : FragmentStateAdapter(activity) {

        override fun getItemCount() = numberOfPages

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StarredTasksFragment()
                else -> TasksFragment()
            }
        }
    }
}