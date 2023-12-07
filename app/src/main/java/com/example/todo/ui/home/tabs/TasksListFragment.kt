package com.example.todo.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.MyDataBase
import com.example.todo.databinding.FragmentTasksListBinding
import com.example.todo.model.Task
import com.example.todo.ui.home.tabs.tasks_list.TasksAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar

class TasksListFragment : Fragment() {
    private lateinit var viewBinding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTasksListBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }

    fun loadTasks() {
        context?.let {
            val tasks = MyDataBase.getInstance(it)
                .tasksDao()
                .getTasksByDate(selectedDate.timeInMillis)
            tasksAdapter.bindTasks(tasks.toMutableList())
        }
    }

    private val tasksAdapter = TasksAdapter(null)

    val selectedDate = Calendar.getInstance()

    init {
        selectedDate.set(Calendar.HOUR_OF_DAY, 0)
        selectedDate.set(Calendar.HOUR, 0)
        selectedDate.set(Calendar.MINUTE, 0)
        selectedDate.set(Calendar.SECOND, 0)
        selectedDate.set(Calendar.MILLISECOND, 0)
    }

    private fun initViews() {
        viewBinding.recyclerView.adapter = tasksAdapter
        tasksAdapter.onItemDeleteListener = TasksAdapter.OnItemClickListener { position, task ->
            deleteTaskFromDatabase(task)
            tasksAdapter.taskDeleted(task)
        }
        viewBinding.calendarView.setSelectedDate(
            CalendarDay.today()
        )
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                //reload tasks in the selected date
                selectedDate.set(Calendar.YEAR, date.year)
                selectedDate.set(Calendar.MONTH, date.month - 1)
                selectedDate.set(Calendar.DAY_OF_MONTH, date.day)
                loadTasks()
            }
        }
    }

    private fun deleteTaskFromDatabase(task: Task) {
        MyDataBase.getInstance(requireContext())
            .tasksDao()
            .deleteTask(task)

    }

}