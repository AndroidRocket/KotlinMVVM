package com.redapple.models.dao

import android.arch.persistence.room.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.redapple.models.TaskItem


/**
 * Created by Wilson on 07-03-2018.
 */
interface GenericDao<T> {
    @Insert
    fun insert(obj: T)  : Long

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert
    fun insert(vararg obj: T)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)

//    fun getAllTask() : List<T>


}