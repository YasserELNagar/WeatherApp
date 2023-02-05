package com.example.data.di

import com.example.data.ICoroutineDispatchers
import com.example.data.local.dao.WeatherDao
import com.example.data.local.datasource.IWeatherLocalDatasource
import com.example.data.local.datasource.WeatherLocalDatasource
import com.example.data.remote.datasource.IWeatherRemoteDatasource
import com.example.data.remote.datasource.WeatherRemoteDatasource
import com.example.data.remote.service.WeatherServicesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(servicesApi: WeatherServicesApi,coroutineDispatchers: ICoroutineDispatchers):IWeatherRemoteDatasource{
        return WeatherRemoteDatasource(servicesApi,coroutineDispatchers)
    }

}