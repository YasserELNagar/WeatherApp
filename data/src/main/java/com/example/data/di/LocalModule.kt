package com.example.data.di

import com.example.data.ICoroutineDispatchers
import com.example.data.local.dao.WeatherDao
import com.example.data.local.datasource.IWeatherLocalDatasource
import com.example.data.local.datasource.WeatherLocalDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Singleton
    @Provides
    fun provideWeatherLocalDataSource(weatherDao: WeatherDao,coroutineDispatchers: ICoroutineDispatchers):IWeatherLocalDatasource{
        return WeatherLocalDatasource(weatherDao,coroutineDispatchers)
    }

}