package com.redapple.di.modules

import com.google.gson.GsonBuilder
import com.redapple.api.KotlinApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Wilson on 02-03-2018.
 */
@Module
class RemoteDataModule(val baseUrl : String) {

//    @Provides @Singleton
//    fun provideKotlinRepoImp(apiService : KotlinApiInterface) : TaskRepository = KotlinRepoImpl(apiService)

    @Provides @Singleton
    fun provideApiImple(retrofit: Retrofit) : KotlinApiInterface
        =retrofit.create(KotlinApiInterface::class.java)


    @Provides @Singleton
    fun provideRetrofit() : Retrofit
    {
        val gson = GsonBuilder().create()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

}