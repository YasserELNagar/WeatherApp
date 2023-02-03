package com.example.domain.repository

import com.example.data.local.entities.WeatherEntity
import com.example.domain.model.WeatherItem
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    suspend fun addWeatherItem(item: WeatherItem)
    suspend fun editWeatherItem(item:WeatherItem)
    suspend fun getWeatherItem(item:WeatherItem)
    suspend fun getWeatherItems(): Flow<List<WeatherEntity>?>
}