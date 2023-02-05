package com.example.weatherapp.features.weatherHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.resource.Resource
import com.example.domain.model.WeatherItem
import com.example.domain.usecase.GetWeatherItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val getWeatherItemsUseCase: GetWeatherItemsUseCase
) : ViewModel() {

    private val _weatherItemsStateFlow = MutableStateFlow<Resource<List<WeatherItem?>?>>(Resource.Initial())
    val weatherItemsStateFlow: StateFlow<Resource<List<WeatherItem?>?>> = _weatherItemsStateFlow

    lateinit var weatherItemsPhotos :List<String>


    init {
        loadWeatherItems()
    }

    private fun loadWeatherItems(){
        viewModelScope.launch {
            _weatherItemsStateFlow.value= Resource.Loading()
            getWeatherItemsUseCase().collect{weatherItems->
                _weatherItemsStateFlow.value= Resource.SUCCESS(weatherItems)
                weatherItemsPhotos = weatherItems?.map {
                    it?.photoPath?:""
                }?: emptyList()
            }
        }
    }

}