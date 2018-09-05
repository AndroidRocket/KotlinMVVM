package com.redapple.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import boonya.ben.callingwebservice.model.Species
import com.redapple.AppDatabase
import com.redapple.repositories.TaskRepository
import com.redapple.models.TaskItem
import com.redapple.utils.DatabaseAsycTask
import com.redapple.views.adapters.MyAdapter
import javax.inject.Inject


/**
 * Created by Wilson on 17-02-2018.
 */
class TaskViewModel : ViewModel()
{

    @Inject
    lateinit var repository: TaskRepository

    var isset: Boolean = false


//    @Inject
//    lateinit var  database : AppDatabase


    var isLoading = MutableLiveData<Boolean>()

    var apiError = MutableLiveData<Throwable>()

    var apiResponse = MutableLiveData<List<Species>>()
    var users = MutableLiveData<List<TaskItem>>()
    var taskItem =  MutableLiveData<ArrayList<TaskItem>>()
    var delteTaskItem =  MutableLiveData<List<TaskItem>>()
    var moveToDone =  MutableLiveData<ArrayList<TaskItem>>()
    var updateTask =  MutableLiveData<ArrayList<TaskItem>>()

    fun getListFromApi() {

        isLoading.value = true
        repository.getList(
                {
                    apiResponse.value = it
                    isLoading.value = false
                },
                {
                    apiError.value = it
                    isLoading.value = false
                })
    }


    fun getListFromDB() {

        isLoading.value = true
        repository.getListFromDb(
                {
                    users.value = it
                    isLoading.value = false
                    isset = true
                })

    }


    fun insertIntoDB(task : ArrayList<TaskItem>) {

        isLoading.value = true
        repository.insertTask(task,
                {
                    taskItem.value = task
                    isLoading.value = false
                })
    }

//    fun deleteTask(task : TaskItem) {
//
//        isLoading.value = true
//        repository.deleteTask(task,
//                {
//                    delteTaskItem.value = task
//                    isLoading.value = false
//                })
//    }


    fun deleteTask(task : ArrayList<TaskItem>) {

        isLoading.value = true
        repository.deleteTask(task,
                {
                    delteTaskItem.value = task
                    isLoading.value = false
                })
    }

    fun moveToDone(task : ArrayList<TaskItem>) {

        isLoading.value = true
        repository.moveTodone(task,
                {
                    moveToDone.value = task
                    isLoading.value = false
                })
    }


    fun updateTask(task : ArrayList<TaskItem>) {

        isLoading.value = true
        repository.moveTodone(task,
                {
                    updateTask.value = task
                    isLoading.value = false
                })
    }




//    fun getInsertedTask() : LiveData<TaskItem>
//    {
//        if (taskItem == null) {
//            taskItem = MutableLiveData<TaskItem>()
//            insertTask()
//        }
//        return taskItem as MutableLiveData<TaskItem>
//    }
//    fun getUsers(): LiveData<List<TaskItem>> {
//        if (users == null) {
//            users = MutableLiveData<List<TaskItem>>()
//            loadTasks()
//        }
//        return users as MutableLiveData<List<TaskItem>>
//    }
//
//    private fun loadTasks() {
//        var networkDataManager = DatabaseAsycTask(database.taskDao(), DatabaseAsycTask.GETALL)
//        networkDataManager.registerInteractot(this)
//        networkDataManager.execute()
//    }
//
//    fun insertTask()  {
//        var networkDataManager = DatabaseAsycTask(database.taskDao(), DatabaseAsycTask.INSERT, TaskItem(name = "Wilson  " + System.currentTimeMillis()))
//        networkDataManager.registerInteractot(this)
//        networkDataManager.execute()
//    }
}