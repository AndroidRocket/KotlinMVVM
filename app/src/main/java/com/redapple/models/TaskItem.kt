package com.redapple.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.redapple.utils.Utils

/**
 * Created by Wilson on 14-02-2018.
 */

@Entity(tableName = "task")
data class TaskItem(@ColumnInfo(name = "id")@PrimaryKey(autoGenerate = true)var id : Int=0, @ColumnInfo(name = "task_name")var name : String="", @ColumnInfo(name = "created_date")var createdDate : String= Utils.getCurrentDateInString(), @ColumnInfo(name = "task_desc")var desc : String="", @ColumnInfo(name = "is_trash")var isTrash : Boolean=false, @ColumnInfo(name = "is_done")var isDone : Boolean=false)
{@Ignore
constructor() : this(0)}
typealias TaskItems = List<TaskItem>