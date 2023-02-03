package com.example.data.local.entities

import androidx.room.Entity

@Entity(tableName = "WeatherItems",primaryKeys = ["id"])
data class WeatherEntity(
    val id:Long?=null,
    val temp:Double?=null,
    val description:String?=null,
    val iconUrl :String?=null,
    val cityName:String?=null,
    val photoPath:String?=null
)