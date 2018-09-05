package com.redapple.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by oozou on 7/12/2017 AD.
 */
@Entity(tableName = "data")
data class Data(@PrimaryKey val id: String, val value: String)