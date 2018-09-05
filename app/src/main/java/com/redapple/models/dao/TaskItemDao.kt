package com.redapple.models.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.redapple.models.TaskItem

/**
 * Created by Wilson on 15-02-2018.
 */
@Dao interface TaskItemDao : GenericDao<TaskItem>
{
    @Insert(onConflict = REPLACE)
    override fun insert(task : TaskItem) : Long

    @Delete
    override fun delete(task : TaskItem)

    @Delete
    fun delete(taskList: ArrayList<TaskItem>)

    @Query("Select * from task where is_done = 0 order by id desc")
    fun getAllVisibleTask() : List<TaskItem>

    @Query("Select * from task order by id desc")
    fun getAllTask() : List<TaskItem>

    @Query("select * from task where id = :id")
    fun findTaskById(id: Int): TaskItem

    @Update(onConflict = REPLACE)
    override fun update(task : TaskItem)

    @Update(onConflict = REPLACE)
    fun update(taskList : ArrayList<TaskItem>)

    @Query("update task set is_done = 1 where id = :id")
    fun moveTodoneWithRqwQuery(id : Int)


}