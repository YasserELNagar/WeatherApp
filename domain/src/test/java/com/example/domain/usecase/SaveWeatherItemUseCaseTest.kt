package com.example.domain.usecase

import com.example.data.local.entities.WeatherEntity
import com.example.data.repository.IWeatherRepository
import com.example.domain.model.WeatherItem
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SaveWeatherItemUseCaseTest{

    @Test
    fun `SaveWeatherItemUseCaseTest() with WeatherItem invoke repo addWeatherItem()`(){
        runBlocking {
            val repository :IWeatherRepository = mock()
            val useCase = SaveWeatherItemUseCase(repository)
            val weatherItem=WeatherItem(temp = 10.5, cityName = "Mansoura", description = "Cold")
            useCase.invoke(weatherItem)
            verify(repository).addWeatherItem(WeatherEntity(temp = 10.5, cityName = "Mansoura", description = "Cold"))
        }

    }

    @Test
    fun `SaveWeatherItemUseCaseTest() with WeatherItem repo addWeatherItem with different data will not be called()`(){
        runBlocking {
            val repository :IWeatherRepository = mock()
            val useCase = SaveWeatherItemUseCase(repository)
            val weatherItem=WeatherItem(temp = 10.5, cityName = "Mansoura", description = "Cold")
            val weatherEntity=WeatherEntity(temp = 10.5, cityName = "Mansoura", description = "Cold")
            useCase.invoke(weatherItem)
            verify(repository)
                .addWeatherItem(
                    argThat{weatherEntity!=WeatherEntity(temp = 10.5, cityName = "Cairo", description = "Cold")}
                )
        }

    }

    @Test
    fun `SaveWeatherItemUseCaseTest() with WeatherItem repo addWeatherItem will  invoked with the right values()`(){
        runBlocking {
            val repository :IWeatherRepository = mock()
            val useCase = SaveWeatherItemUseCase(repository)
            val weatherItem=WeatherItem(temp = 10.5, cityName = "Mansoura", description = "Cold")
            val weatherEntity=WeatherEntity(temp = 10.5, cityName = "Mansoura", description = "Cold")
            useCase.invoke(weatherItem)
            verify(repository)
                .addWeatherItem(
                    argThat{weatherEntity==WeatherEntity(temp = 10.5, cityName = "Mansoura", description = "Cold")}
                )
        }

    }

}
