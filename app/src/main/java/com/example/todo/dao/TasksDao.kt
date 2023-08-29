package com.example.todo.dao

import androidx.room.*
import com.example.todo.model.Task

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("select * from tasks")
    fun getAllTasks(): List<Task>

    @Query("select * from tasks where dateTime= :dateTime")
    fun getTasksByDate(dateTime: Long): List<Task>

}