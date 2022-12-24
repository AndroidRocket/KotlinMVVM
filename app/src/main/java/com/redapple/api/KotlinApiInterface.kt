package com.redapple.api

import com.redapple.models.SpeciesList
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Wilson on 01-03-2018.
 */
interface KotlinApiInterface {

    @GET("species/")
    fun getList() : Call<SpeciesList>
}