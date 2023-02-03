package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.Constns.DB_VERSION
import com.example.data.local.dao.WeatherDao
import com.example.data.local.entities.WeatherEntity

@Database(entities = [WeatherEntity::class], version = DB_VERSION)
abstract class AppDataBase :RoomDatabase() {

    abstract fun weatherDao():WeatherDao
}