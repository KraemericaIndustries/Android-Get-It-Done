package com.kraemericaindustries.getitdone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.kraemericaindustries.getitdone.databinding.ActivityMainBinding
import com.kraemericaindustries.getitdone.databinding.DialogAddTaskBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener {

            showAddTaskDialog()
        }

    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(this)
            .setTitle("Add new task")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                Toast.makeText(
                    this,
                    "Your task is: ${dialogBinding.editText.text}",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }
    }
}