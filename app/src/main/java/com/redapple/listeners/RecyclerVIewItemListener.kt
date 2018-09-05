package com.redapple.listeners

import com.redapple.models.TaskItem

/**
 * Created by Wilson on 10-03-2018.
 */
interface RecyclerViewItemListener {

    fun onItemClick(taskItem: TaskItem)

    fun moveToDone(taskItem: TaskItem)

    fun moveToTrash(taskItem: TaskItem)

    fun onEditClick(taskItem: Int)

    fun onDeleteClick(taskList : ArrayList<TaskItem>)
}