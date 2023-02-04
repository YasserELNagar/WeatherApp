package com.example.domain.usecase

import com.example.data.local.entities.WeatherEntity
import com.example.data.repository.IWeatherRepository
import javax.inject.Inject

class GetWeatherItemUseCase @Inject constructor (private val repository: IWeatherRepository) {
    suspend operator fun invoke(itemId: Int): WeatherEntity? {
        return repository.getWeatherItem(itemId)
    }
}