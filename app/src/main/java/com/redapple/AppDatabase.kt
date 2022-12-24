package com.redapple
import androidx.room.Database
import androidx.room.RoomDatabase
import com.redapple.models.TaskItem
import com.redapple.models.dao.TaskItemDao

/**
 * Created by Wilson on 15-02-2018.
 */
@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun taskDao() : TaskItemDao

//    abstract fun genDao() : GenericDao<TaskItem>
}