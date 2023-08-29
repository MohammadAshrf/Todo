package com.example.todo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.home.tabs.SettingsFragment
import com.example.todo.ui.home.tabs.TasksListFragment
import com.example.todo.ui.home.tabs.tasks_list.AddTaskFragment
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    var tasksListFragmentRef: TasksListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav
            .setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_tasks_list -> {
                        tasksListFragmentRef= TasksListFragment()
                        showFragment(tasksListFragmentRef!!)
                    }

                    R.id.nav_settings -> {
                        showFragment(SettingsFragment())
                    }
                }
                return@setOnItemSelectedListener true
            }
        viewBinding.addTask
            .setOnClickListener {
                showAddTaskBottomSheet()
            }
        viewBinding.bottomNav.selectedItemId = R.id.nav_tasks_list
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskFragment()
        addTaskSheet.onTaskAddedListener = AddTaskFragment.OnTaskAddedListener {
            Snackbar.make(
                viewBinding.root,
                "Task Added Successfully", Snackbar.LENGTH_LONG
            )
                .show()
            //Notify Tasks List Fragment to update the tasks list
            tasksListFragmentRef?.loadTasks()

        }
        addTaskSheet.show(supportFragmentManager, "")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .commit()
    }

}