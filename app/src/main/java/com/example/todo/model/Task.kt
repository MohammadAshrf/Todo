package com.example.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tasks")
data class Task(
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo
    var title: String? = null,
    @ColumnInfo
    var description: String? =null,
    @ColumnInfo
    var dateTime:Long?=null,
    @ColumnInfo
    var isDone:Boolean=false
)