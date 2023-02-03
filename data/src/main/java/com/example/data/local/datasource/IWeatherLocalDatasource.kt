package com.example.data.local.datasource

import com.example.data.local.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow


interface IWeatherLocalDatasource {
    suspend fun addWeatherItem(item: WeatherEntity)
    suspend fun updateWeatherItem(item: WeatherEntity)
    suspend fun deleteWeatherItem(item: WeatherEntity)
    suspend fun getWeatherItems(): Flow<List<WeatherEntity>?>
    suspend fun getWeatherItem(itemId: Int): WeatherEntity?
}