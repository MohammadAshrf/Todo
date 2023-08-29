package com.example.todo.ui.home.tabs.tasks_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.model.Task

class TasksAdapter(var tasks: List<Task>?) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks!![position])

    }

    override fun getItemCount(): Int = tasks?.size ?: 0


    fun bindTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(tasks: Task) {
            itemBinding.title.text = tasks.title
            itemBinding.description.text = tasks.description
        }

    }


}