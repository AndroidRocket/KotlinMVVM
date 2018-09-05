package com.redapple.repositories

import boonya.ben.callingwebservice.model.Species
import com.redapple.models.TaskItem

/**
 * Created by Wilson on 17-02-2018.
 */
interface TaskRepository {

   fun insertTask(task: ArrayList<TaskItem>,successHandler: (TaskItem?) -> Unit)

   fun deleteTask(task: ArrayList<TaskItem>,successHandler: (TaskItem?) -> Unit)

   fun getList(successHandler: (List<Species>?)->Unit,failureHandler : (Throwable?)->Unit)

   fun getListFromDb(successHandler: (List<TaskItem>?) -> Unit)

   fun moveTodone(task: ArrayList<TaskItem>,successHandler: (TaskItem?) -> Unit)

   fun moveToTrash(task: ArrayList<TaskItem>,successHandler: (TaskItem?) -> Unit)

   fun upateTask(task: ArrayList<TaskItem>,successHandler: (TaskItem?) -> Unit)
}