package com.example.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.local.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherItem(item: WeatherEntity)

    @Update
    suspend fun updateWeatherItem(item: WeatherEntity)

    @Delete
    suspend fun deleteWeatherItem(item: WeatherEntity)

    @Query("Select * From WeatherItems Order BY id Desc")
    fun getWeatherItems(): Flow<List<WeatherEntity>?>

    @Query("Select * From WeatherItems Where id = :itemId")
    suspend fun getWeatherItem(itemId: Int): WeatherEntity?
}