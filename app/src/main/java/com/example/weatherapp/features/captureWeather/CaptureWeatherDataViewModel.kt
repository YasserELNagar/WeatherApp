package com.example.weatherapp.features.captureWeather

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.resource.Resource
import com.example.domain.model.WeatherItem
import com.example.domain.usecase.FetchCurrentLocationUseCase
import com.example.domain.usecase.GetCurrentWeatherDataUseCase
import com.example.domain.usecase.SaveWeatherItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureWeatherDataViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val saveWeatherItemUseCase: SaveWeatherItemUseCase,
    private val fetchCurrentLocationUseCase: FetchCurrentLocationUseCase,
    private val getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase
):ViewModel() {

    private val _currentWeatherDataStateFlow = MutableStateFlow<Resource<WeatherItem?>>(Resource.Initial())
    val currentWeatherDataStateFlow: StateFlow<Resource<WeatherItem?>> = _currentWeatherDataStateFlow

    private val _weatherInputValidations = MutableStateFlow<WeatherInputValidations?>(null)
    val weatherInputValidations: StateFlow<WeatherInputValidations?> = _weatherInputValidations

    private val _finish = MutableStateFlow<Boolean>(false)
    val finish: StateFlow<Boolean> = _finish

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    fun fetchCurrentLocation(){
        viewModelScope.launch {
            fetchCurrentLocationUseCase().distinctUntilChanged().collect{location->
                location?.let {
                    _currentLocation.value=location
                    loadCurrentWeatherData("${location.latitude},${location.latitude}")
                }
            }
        }
    }

    private fun loadCurrentWeatherData(latLag:String){
        viewModelScope.launch {
            _currentWeatherDataStateFlow.value=Resource.Loading()
            try {
                val weatherData=getCurrentWeatherDataUseCase(latLag)
                _currentWeatherDataStateFlow.value=Resource.SUCCESS(weatherData)
            }catch (t:Throwable){
                _currentWeatherDataStateFlow.value=Resource.ERROR(t)
            }

        }
    }

    fun onSaveClick(degree:String?,city:String?,condition:String?){
        when{
            degree.isNullOrEmpty()->{
                _weatherInputValidations.value=WeatherInputValidations.TEMP_EMPTY
            }
            city.isNullOrEmpty()->{
                _weatherInputValidations.value=WeatherInputValidations.LOCATION_EMPTY
            }
            condition.isNullOrEmpty()->{
                _weatherInputValidations.value=WeatherInputValidations.WEATHER_CONDITION_EMPTY
            }
            else->{
                saveWeatherData(degree.toDouble(),city,condition)
            }
        }

    }

    private fun saveWeatherData(degree:Double,city:String,condition:String){
        viewModelScope.launch {
            val photoPath =state.get<String>("capturePhotoPath")
            saveWeatherItemUseCase(WeatherItem(temp = degree, cityName = city, description = condition, photoPath = photoPath))
            _finish.value=true
        }
    }

}