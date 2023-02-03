package com.example.data.remote.datasource

import com.example.data.remote.dto.CurrentWeatherDTO
import retrofit2.Response
import retrofit2.http.Query

interface IWeatherRemoteDatasource {
    suspend fun currentWeather(latLng:String): CurrentWeatherDTO?
}