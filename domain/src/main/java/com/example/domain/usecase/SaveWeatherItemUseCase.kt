package com.example.domain.usecase

import com.example.data.repository.IWeatherRepository
import com.example.domain.mapper.LocalMapper
import com.example.domain.model.WeatherItem
import javax.inject.Inject

class SaveWeatherItemUseCase @Inject constructor(private val repository: IWeatherRepository) {
    suspend operator fun invoke(item: WeatherItem){
        LocalMapper.mapFromDomain(item)?.let {
            repository.addWeatherItem(it)
        }
    }
}