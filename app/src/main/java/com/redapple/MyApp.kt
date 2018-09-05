package com.redapple

import android.app.Application
import android.arch.persistence.room.Room
import com.google.android.gms.ads.MobileAds
import com.redapple.di.compenents.AppComponent
import com.redapple.di.compenents.DaggerAppComponent
import com.redapple.di.modules.DatabaseModule
import com.redapple.di.modules.PostRepoModule
import com.redapple.di.modules.RemoteDataModule


/**
 * Created by Wilson on 15-02-2018.
 */
class MyApp : Application()
{


    companion object {
        lateinit var component: AppComponent
        val BASE_URL = "http://swapi.co/api/"
        lateinit var database: AppDatabase
    }



    override fun onCreate() {
        super.onCreate()
        buildDependencyGraph()
        Preferences.init(this)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
//        component.inject(this)
        database = Room.databaseBuilder(this, AppDatabase::class.java,"sample").build()
    }

    private fun buildDependencyGraph() {
        component =
                DaggerAppComponent.builder()
                .databaseModule(DatabaseModule(applicationContext))
                .remoteDataModule(RemoteDataModule(BASE_URL))
                .postRepoModule(PostRepoModule())
                .build()
    }
}