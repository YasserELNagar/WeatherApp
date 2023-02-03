package com.example.domain.model


data class WeatherItem(
    val id:Long?=null,
    val temp:Double?=null,
    val description:String?=null,
    val iconUrl :String?=null,
    val cityName:String?=null,
    val photoPath:String?=null
)