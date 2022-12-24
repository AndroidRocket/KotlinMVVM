package com.redapple.views.activity

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.redapple.MyApp
import com.redapple.R
import com.redapple.models.TaskItem
import com.redapple.viewmodels.TaskDetailViewModel
import com.redapple.viewmodels.TaskViewModel

class TaskDetailActivity : AppCompatActivity() {


    companion object {

        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, user: TaskItem): Intent {
            val intent = Intent(context, TaskDetailActivity::class.java)
            intent.putExtra(INTENT_USER_ID, user.id)
            return intent
        }
    }


    private val simpleViewModel by lazy {
        ViewModelProviders.of(this).get(TaskDetailViewModel::class.java).also {
            MyApp.component.inject(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        attachObserver()
//        simpleViewModel.insertIntoDB(TaskItem(name = "Add from Detail  " + System.currentTimeMillis()))
    }


    private fun attachObserver()
    {
        simpleViewModel.taskItem.observe(this, Observer<ArrayList<TaskItem>> {it->
            it.let {
                it!![0]?.name?.let { it1 -> Log.e("task", it1) }
            }
        })
    }
}
