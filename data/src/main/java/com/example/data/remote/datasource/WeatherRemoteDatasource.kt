package com.example.data.remote.datasource

import com.example.data.ICoroutineDispatchers
import com.example.data.remote.NetworkUtil
import com.example.data.remote.dto.CurrentWeatherDTO
import com.example.data.remote.service.WeatherServicesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDatasource @Inject constructor (
    private val api:WeatherServicesApi,
    private val coroutineScopeDispatchers: ICoroutineDispatchers
    ):IWeatherRemoteDatasource {
    override suspend fun currentWeather(latLng: String): CurrentWeatherDTO? {
        return withContext(coroutineScopeDispatchers.IO){
            NetworkUtil.processAPICall { api.currentWeather(latLng) }
        }
    }
}