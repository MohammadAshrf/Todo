package com.example.todo.ui.home.tabs.tasks_list

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.todo.MyDataBase
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskFragment : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addTaskButton.setOnClickListener {
            createTask()
        }
        viewBinding.dateContainer.setOnClickListener {
            showDatePickerDialog()
        }
    }

    val calendar = Calendar.getInstance()

    private fun showDatePickerDialog() {
        context?.let {
            val dialog = DatePickerDialog(it)
            dialog.setOnDateSetListener { datePicker, year, month, day ->
                viewBinding.date.text = ("$day-${month + 1}-$year")
                calendar.set(year, month, day)
                //To ignore time
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            dialog.show()
        }
    }

    private fun valid(): Boolean {
        var isValid = true

        if (viewBinding.title.text.toString().isNullOrBlank()) {
            viewBinding.titleContainer.error = "please enter title!"
            isValid = false
        } else {
            viewBinding.titleContainer.error = null
        }
        if (viewBinding.desc.text.toString().isNullOrBlank()) {
            viewBinding.descContainer.error = "please enter description!"
            isValid = false
        } else {
            viewBinding.descContainer.error = null
        }
        if (viewBinding.date.text.toString().isNullOrBlank()) {
            viewBinding.dateContainer.error = "please enter date!"
            isValid = false
        } else {
            viewBinding.dateContainer.error = null
        }
        return isValid
    }

    private fun createTask() {
        if (!valid()) {
            return
        }
        val task = Task(
            title = viewBinding.title.text.toString(),
            description = viewBinding.desc.text.toString(),
            dateTime = calendar.timeInMillis
        )
        MyDataBase.getInstance(requireContext())
            .tasksDao()
            .insertTask(task)
        onTaskAddedListener?.onTaskAdded()
        dismiss()
    }

    var onTaskAddedListener: OnTaskAddedListener? = null

    fun interface OnTaskAddedListener {
        fun onTaskAdded()
    }
}
