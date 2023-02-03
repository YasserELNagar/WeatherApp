package com.example.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.data.Constns.DB_NAME
import com.example.data.local.AppDataBase
import com.example.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun provideWeatherDao(appDataBase: AppDataBase): WeatherDao {
        return appDataBase.weatherDao()
    }

    @Singleton
    @Provides
    fun provideAppDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(
            application,
            AppDataBase::class.java,
            DB_NAME
        ).build()
    }

}