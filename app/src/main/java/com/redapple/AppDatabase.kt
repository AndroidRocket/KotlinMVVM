package com.redapple

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.redapple.models.TaskItem
import com.redapple.models.dao.GenericDao
import com.redapple.models.dao.TaskItemDao

/**
 * Created by Wilson on 15-02-2018.
 */
@Database(entities = arrayOf(TaskItem::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun taskDao() : TaskItemDao

//    abstract fun genDao() : GenericDao<TaskItem>
}