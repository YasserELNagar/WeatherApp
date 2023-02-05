package com.example.domain.usecase

import android.content.Context
import android.location.Location
import com.example.data.repository.IWeatherRepository
import com.example.domain.mapper.LocalMapper
import com.example.domain.model.WeatherItem
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FetchCurrentLocationUseCase @Inject constructor(@ApplicationContext private val context: Context,private val repository: IWeatherRepository) {

    private val  fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    suspend operator fun invoke(): Flow<Location?> = callbackFlow {

        val locationCallback= object : LocationCallback() {
            override fun onLocationAvailability(availability: LocationAvailability) {
                super.onLocationAvailability(availability)
                if (!availability.isLocationAvailable){
                    close(Exception("Location Not Available"))
                }
            }
        override fun onLocationResult(result: LocationResult) {
            result.let {
                for (location in result.locations) {
                    trySend(location)
                }
            }
        }
    }
        val locationUpdateRequest = createLocationRequest()

        fusedLocationProviderClient.requestLocationUpdates(locationUpdateRequest,locationCallback,null)

        awaitClose{
            fusedLocationProviderClient.removeLocationUpdates(
                locationCallback
            )
        }

    }


    private fun createLocationRequest(): LocationRequest {
        val locationUpdateRequest = LocationRequest.create().apply {
            this.interval = 1000
            this.fastestInterval = 1000
            this.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        return locationUpdateRequest
    }
}