package com.example.data.repository

import com.example.data.local.entities.WeatherEntity
import com.example.data.remote.dto.CurrentWeatherDTO
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    suspend fun addWeatherItem(item: WeatherEntity)
    suspend fun editWeatherItem(item:WeatherEntity)
    suspend fun clearWeatherItem(item:WeatherEntity)
    suspend fun getWeatherItem(itemId:Int):WeatherEntity?
    suspend fun getWeatherItems(): Flow<List<WeatherEntity>?>
    suspend fun getCurrentWeatherData(latLng:String): CurrentWeatherDTO?
}