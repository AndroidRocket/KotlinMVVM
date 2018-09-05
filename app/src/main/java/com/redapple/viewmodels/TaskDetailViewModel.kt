package com.redapple.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import boonya.ben.callingwebservice.model.Species
import com.redapple.models.TaskItem
import com.redapple.repositories.TaskRepository
import javax.inject.Inject

/**
 * Created by Wilson on 03-03-2018.
 */
class TaskDetailViewModel : ViewModel()
{
    @Inject
    lateinit var repository: TaskRepository

    var taskItem =  MutableLiveData<ArrayList<TaskItem>>()

    var isLoading = MutableLiveData<Boolean>()


    fun insertIntoDB(task : ArrayList<TaskItem>) {

        isLoading.value = true

        repository.insertTask(task,
                {
                    taskItem.value = task
                    isLoading.value = false
                })
    }

}