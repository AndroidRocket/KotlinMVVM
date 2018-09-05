package com.redapple.di.compenents

import dagger.Component
import com.redapple.MyApp
import com.redapple.di.modules.DatabaseModule
import com.redapple.di.modules.PostRepoModule
import com.redapple.di.modules.RemoteDataModule
import com.redapple.viewmodels.TaskDetailViewModel
import com.redapple.viewmodels.TaskViewModel
import javax.inject.Singleton

/**
 * Created by Wilson on 25-02-2018.
 */
@Singleton
@Component(modules = arrayOf(DatabaseModule::class, RemoteDataModule::class,PostRepoModule::class))
interface AppComponent {

    fun inject(taskViewModel: TaskViewModel)

    fun inject(taskDetailViewModel: TaskDetailViewModel)

    fun inject(myApp: MyApp)
}