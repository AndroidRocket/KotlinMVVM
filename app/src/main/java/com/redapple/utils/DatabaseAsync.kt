package com.redapple.utils

import android.arch.persistence.room.Ignore
import android.os.AsyncTask
import com.redapple.models.TaskItem
import com.redapple.AppDatabase
import com.redapple.models.dao.TaskItemDao
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.redapple.repositories.TaskRepositoryImpl
import com.redapple.views.activity.HomeActivity
import com.redapple.views.fragments.SettingFragment


/**
 * Created by Wilson on 18-02-2018.
 */
class DatabaseAsycTask<T>(var taskItemDao: TaskItemDao, var dbOperation: Long) : AsyncTask<T, String, List<Any>?>()
{


    lateinit var taskItems : List<Any>

    var interactor: AsyncResponse<T>? = null


//    private lateinit var taskItem: Any

    lateinit var taskItems1 : List<T>
    private var taskId: Long = 0


    constructor(taskItemDao: TaskItemDao, dbOperation: Long,list: ArrayList<T>) : this(taskItemDao,dbOperation) {

        this.taskItemDao = taskItemDao
        this.dbOperation = dbOperation
        this.taskItems1 = list
    }

    companion object {

        const val INSERT = 0L
        const val UPDATE = 1L
        const val DELETE = 2L
        const val GETALL = 3L
        const val MOVE_TO_DONE = 4L
        const val MOVE_TO_TRASH = 5L
    }




    override fun doInBackground(vararg p0: T): List<Any>? {

        when (dbOperation)
        {
            INSERT ->{
                taskId = taskItemDao.insert(interactor?.taskItem as TaskItem)
                (interactor?.taskItem as TaskItem).id = taskId.toInt()
            }

            UPDATE -> taskItemDao.update((interactor?.taskItem as TaskItem))

            DELETE -> {
                taskItemDao.delete(TaskRepositoryImpl.list)
            }
            GETALL -> {
                taskItems = if(!HomeActivity.hideRequired) {
                    taskItemDao.getAllTask()
                } else {
                    taskItemDao.getAllVisibleTask()
                }


            }

            MOVE_TO_DONE -> {
//                (interactor?.taskItem as TaskItem).isDone = !(interactor?.taskItem as TaskItem).isDone
                taskItemDao.update((interactor?.taskItem as TaskItem))
            }
        }

        val allTask = null
        return allTask

    }



    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(result: List<Any>?) {
        super.onPostExecute(result)
        when(dbOperation)
        {
            INSERT -> interactor?.processObject(interactor?.taskItem!!)

            UPDATE -> interactor?.processObject(interactor?.taskItem!!)

            DELETE -> interactor?.processObject(interactor?.taskItem!!)

            GETALL -> interactor?.processList(taskItems)

            MOVE_TO_DONE -> interactor!!.processObject(interactor!!.taskItem)
        }

    }

    fun registerInteractot(mAsyncResponse: AsyncResponse<T>) {
        this.interactor = mAsyncResponse
    }
    interface AsyncResponse<T> {
        var taskItem: T
//        var taskList : ArrayList<T>
        fun processList(output: List<Any>?)
        fun processObject(output : T)
        companion object {

        }
    }
}