package com.redapple.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by oozou on 7/12/2017 AD.
 */
@Entity(tableName = "data")
data class Data(@PrimaryKey val id: String, val value: String)