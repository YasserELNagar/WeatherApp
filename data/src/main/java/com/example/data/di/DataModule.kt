package com.example.data.di

import com.example.data.AppCoroutineDispatchers
import com.example.data.ICoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun providesCoroutineDispatcher():ICoroutineDispatchers{
        return AppCoroutineDispatchers()
    }
}