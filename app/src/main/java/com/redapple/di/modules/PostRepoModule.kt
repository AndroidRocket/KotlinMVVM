package com.redapple.di.modules

import com.redapple.repositories.TaskRepository
import com.redapple.repositories.TaskRepositoryImpl
import com.redapple.api.KotlinApiInterface
import com.redapple.models.dao.TaskItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Wilson on 02-03-2018.
 */
@Module
class PostRepoModule {

    @Provides
    @Singleton
    fun providePostRepository(localSource: TaskItemDao, remoteSource: KotlinApiInterface): TaskRepository
            = TaskRepositoryImpl(localSource, remoteSource)
}