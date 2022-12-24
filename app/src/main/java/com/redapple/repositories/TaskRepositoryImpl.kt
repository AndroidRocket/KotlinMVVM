package com.redapple.repositories

import com.redapple.models.Species
import com.redapple.models.SpeciesList
import com.redapple.api.KotlinApiInterface
import com.redapple.models.TaskItem
import com.redapple.models.dao.TaskItemDao
import com.redapple.utils.DatabaseAsycTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Wilson on 02-03-2018.
 */
class TaskRepositoryImpl (val localSource: TaskItemDao, val remoteSource: KotlinApiInterface) : TaskRepository {


    companion object {
        var list = ArrayList<TaskItem>()
    }

    override fun upateTask(task: ArrayList<TaskItem>, successHandler: (TaskItem?) -> Unit) {
        val database = DatabaseAsycTask<TaskItem>(localSource, DatabaseAsycTask.UPDATE)
        database.registerInteractot(object : DatabaseAsycTask.AsyncResponse<TaskItem>
        {
//            override var taskList: ArrayList<TaskItem>
//                get() = task
//                set(value) {task}

            override fun processList(output: List<Any>?) {
            }

            override fun processObject(output: TaskItem) {
                output.let {
                    successHandler(output)
                }
            }
            override var taskItem: TaskItem
                get() = task[0]
                set(value) {task[0]}



        })
        database.execute()
    }

    override fun moveTodone(task: ArrayList<TaskItem>, successHandler: (TaskItem?) -> Unit) {
        val database = DatabaseAsycTask<TaskItem>(localSource, DatabaseAsycTask.MOVE_TO_DONE)
        database.registerInteractot(object : DatabaseAsycTask.AsyncResponse<TaskItem>
        {
//            override var taskList: ArrayList<TaskItem>
//                get() = task
//                set(value) {task}

            override fun processList(output: List<Any>?) {
            }

            override fun processObject(output: TaskItem) {
                output.let {
                    successHandler(output)
                }
            }
            override var taskItem: TaskItem
                get() = task[0]
                set(value) {task[0]}



        })
        database.execute()
    }

    override fun moveToTrash(task: ArrayList<TaskItem>, successHandler: (TaskItem?) -> Unit) {
        val database = DatabaseAsycTask<TaskItem>(localSource, DatabaseAsycTask.MOVE_TO_TRASH)
        database.registerInteractot(object : DatabaseAsycTask.AsyncResponse<TaskItem>
        {
//            override var taskList: ArrayList<TaskItem>
//                get() = task
//                set(value) {task}

            override fun processList(output: List<Any>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun processObject(output: TaskItem) {
                output.let {
                    successHandler(output)
                }
            }
            override var taskItem: TaskItem
                get() = task[0]
                set(value) {task[0]}



        })
        database.execute()
    }

    override fun deleteTask(task: ArrayList<TaskItem>, successHandler: (TaskItem?) -> Unit) {
        list.addAll(task)
        val database = DatabaseAsycTask(localSource, DatabaseAsycTask.DELETE,task)
        database.registerInteractot(object : DatabaseAsycTask.AsyncResponse<TaskItem>
        {
//            override var taskList: ArrayList<TaskItem> get() = task
//                set(value) {task}


            override fun processList(output: List<Any>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun processObject(output: TaskItem) {
                output.let {
                    successHandler(output)
                }
            }
            override var taskItem: TaskItem get() = TaskItem(name = "Delete")
                set(value) {TaskItem(name = "Delete")}



        })
        database.execute()
    }

    override fun insertTask(task: ArrayList<TaskItem>, successHandler: (TaskItem?) -> Unit) {
        val database = DatabaseAsycTask<TaskItem>(localSource, DatabaseAsycTask.INSERT)
        database.registerInteractot(object : DatabaseAsycTask.AsyncResponse<TaskItem>
        {
//            override var taskList: ArrayList<TaskItem>
//                get() = task
//                set(value) {task}

            override fun processList(output: List<Any>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun processObject(output: TaskItem) {
                output.let {
                    successHandler(output)
                }
            }

            override var taskItem: TaskItem
                get() = task[0]
                set(value) {task[0]}





        })
        database.execute()


    }



    override fun getList(successHandler: (List<Species>?) -> Unit, failureHandler: (Throwable?) -> Unit) {

        remoteSource.getList().enqueue(object : Callback<SpeciesList> {
            override fun onResponse(call: Call<SpeciesList>?, response: Response<SpeciesList>?) {
                response?.body()?.let {
                    successHandler(it.speciesList)
                }
            }
            override fun onFailure(call: Call<SpeciesList>?, t: Throwable?) {
                failureHandler(t)
            }
        })
    }

    override fun getListFromDb(successHandler: (List<TaskItem>?) -> Unit) {

        val database = DatabaseAsycTask<TaskItem>(localSource, DatabaseAsycTask.GETALL)
        database.registerInteractot(object : DatabaseAsycTask.AsyncResponse<TaskItem>
        {
//            override var taskList: ArrayList<TaskItem>
//                get() = null as ArrayList<TaskItem>
//                set(value) {null}

            override fun processList(output: List<Any>?) {
                output.let {
                    successHandler(output as List<TaskItem>)
                }

            }

            override fun processObject(output: TaskItem) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override var taskItem: TaskItem
                get() = null as TaskItem
                set(value) {null}

        })
        database.execute()

    }
}