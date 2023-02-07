package com.example.domain.usecase

import org.junit.Assert
import org.junit.Test


class ValidateAddingWeatherItemUseCaseTest{


    @Test
    fun `ValidateAddingWeatherItemUseCase() with empty temp then return TEMP_EMPTY_VALIDATION error`() {
        val expected = WeatherInputValidations.TEMP_EMPTY

        val actual = ValidateAddingWeatherItemUseCase().invoke("","Mansoura","Sunny")

        Assert.assertEquals(expected,actual)
    }

    @Test
    fun `ValidateAddingWeatherItemUseCase() with weather condition temp then return WEATHER_CONDITION_EMPTY error`() {
        val expected = WeatherInputValidations.WEATHER_CONDITION_EMPTY

        val actual = ValidateAddingWeatherItemUseCase().invoke("10.2","Mansoura","")

        Assert.assertEquals(expected,actual)
    }

    @Test
    fun `ValidateAddingWeatherItemUseCase() with empty city then return LOCATION_EMPTY error`() {
        val expected = WeatherInputValidations.LOCATION_EMPTY

        val actual = ValidateAddingWeatherItemUseCase().invoke("40.2","","Sunny")

        Assert.assertEquals(expected,actual)
    }

    @Test
    fun `ValidateAddingWeatherItemUseCase() with valid data then return no validation error`() {
        val expected = null

        val actual = ValidateAddingWeatherItemUseCase().invoke("12.5","Mansoura","Cold")

        Assert.assertEquals(expected,actual)
    }




}