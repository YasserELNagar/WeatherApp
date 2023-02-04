package com.example.data.remote.dto

data class CurrentWeatherDTO(
    val current: Current?=null,
    val location: Location?=null
)