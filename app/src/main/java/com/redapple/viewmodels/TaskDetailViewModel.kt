package com.redapple.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

        repository.insertTask(task
        ) {
            taskItem.value = task
            isLoading.value = false
        }
    }

}