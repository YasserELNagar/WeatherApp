package com.example.domain.usecase

import javax.inject.Inject

class ValidateAddingWeatherItemUseCase @Inject constructor() {

    operator fun invoke(degree:String?,city:String?,condition:String?): WeatherInputValidations? {
        return when{
            degree.isNullOrEmpty()->{
                WeatherInputValidations.TEMP_EMPTY
            }
            city.isNullOrEmpty()->{
                WeatherInputValidations.LOCATION_EMPTY
            }
            condition.isNullOrEmpty()->{
                WeatherInputValidations.WEATHER_CONDITION_EMPTY
            }
            else->{
                null
            }
        }

    }
}

enum class WeatherInputValidations {
    LOCATION_EMPTY,
    TEMP_EMPTY,
    WEATHER_CONDITION_EMPTY
}