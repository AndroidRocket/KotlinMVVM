package com.redapple.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.redapple.AppDatabase
import com.redapple.models.dao.TaskItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Wilson on 25-02-2018.
 */
@Module
class DatabaseModule(val context: Context) {


    val DB_FILE_NAME = "sample.db"

    @Provides @Singleton
    fun provideTaskDao(appDatabase: AppDatabase) : TaskItemDao = appDatabase.taskDao()

    @Provides @Singleton
    fun privideAppDatabase() : AppDatabase =

        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_FILE_NAME)
                .build()


}