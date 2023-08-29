package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.dao.TasksDao
import com.example.todo.model.Task

@Database(entities = [Task::class], version = 2, exportSchema = true)

abstract class MyDataBase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    //static fun in (Java)
    //Singleton
    companion object {
        private var instance: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase {
            if (instance == null) {
                //initialize
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    "tasksDB"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}
