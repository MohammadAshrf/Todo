package com.example.todo.ui.home.tabs.tasks_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.model.Task

class TasksAdapter(var tasks: MutableList<Task>?) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks!![position])
        if (onItemDeleteListener != null) {
            holder.itemBinding.deleteView.setOnClickListener {
                holder.itemBinding.swipeLayout.close(true)
                onItemDeleteListener?.onItemClick(position, tasks!![position])
            }
        }

    }

    override fun getItemCount(): Int = tasks?.size ?: 0

    fun bindTasks(tasks: MutableList<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun taskDeleted(task: Task) {
        val postion = tasks?.indexOf(task)
        tasks?.remove(task)
        notifyItemRemoved(postion!!)

    }

    class ViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(tasks: Task) {
            itemBinding.title.text = tasks.title
            itemBinding.description.text = tasks.description
        }

    }

    var onItemDeleteListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, task: Task)

    }


}