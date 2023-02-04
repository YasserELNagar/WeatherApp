package com.example.data.repository

import com.example.data.local.datasource.IWeatherLocalDatasource
import com.example.data.local.entities.WeatherEntity
import com.example.data.remote.datasource.IWeatherRemoteDatasource
import com.example.data.remote.dto.CurrentWeatherDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDatasource: IWeatherRemoteDatasource,
    private val localDatasource: IWeatherLocalDatasource)
    :IWeatherRepository {
    override suspend fun addWeatherItem(item: WeatherEntity) {
        localDatasource.addWeatherItem(item)
    }

    override suspend fun editWeatherItem(item: WeatherEntity) {
        localDatasource.updateWeatherItem(item)
    }

    override suspend fun clearWeatherItem(item: WeatherEntity) {
        localDatasource.deleteWeatherItem(item)
    }

    override suspend fun getWeatherItem(itemId: Int) :WeatherEntity? {
        return localDatasource.getWeatherItem(itemId)
    }

    override suspend fun getWeatherItems(): Flow<List<WeatherEntity>?> {
        return localDatasource.getWeatherItems()
    }

    override suspend fun getCurrentWeatherData(latLng:String): CurrentWeatherDTO? {
        return remoteDatasource.currentWeather(latLng)
    }
}