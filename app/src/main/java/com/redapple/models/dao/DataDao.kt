package com.redapple.models.dao

import androidx.room.Dao
import androidx.room.Query
import com.redapple.models.Data
import com.redapple.models.TaskItem

/**
 * Created by Wilson on 15-02-2018.
 */
@Dao
abstract class DataDao : GenericDao<Data>
{
    @Query("SELECT * FROM Data")
    abstract fun getData(): List<Data>
}