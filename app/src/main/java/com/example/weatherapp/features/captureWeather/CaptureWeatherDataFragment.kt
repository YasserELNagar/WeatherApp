package com.example.weatherapp.features.captureWeather

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.resource.Resource
import com.example.core.util.*
import com.example.domain.model.WeatherItem
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCaptureWeatherDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaptureWeatherDataFragment : Fragment() {

    private val viewModel: CaptureWeatherDataViewModel by viewModels()
    private lateinit var binding: FragmentCaptureWeatherDataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCaptureWeatherDataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindCapturedPhoto()
        setupClickListeners()
        subscribe()
        if (!loadedCurrentLocation() && hasLocationPermissions()) {
            viewModel.fetchCurrentLocation()
        }

    }

    private fun bindCapturedPhoto() {
        val args:CaptureWeatherDataFragmentArgs by navArgs()
        binding.ivPhoto.loadOfflineImage(args.capturePhotoPath)
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.onSaveClick(
                binding.edtTemp.text.toString(),
                binding.edtCity.text.toString(),
                binding.edtCondition.text.toString()
            )
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun subscribe() {
        subscribeToWeatherInputValidations()
        subscribeToCurrentWeatherDataStateFlow()
        subscribeToFinish()
    }

    private fun subscribeToWeatherInputValidations() {
        lifecycleScope.launchWhenResumed {
            viewModel.weatherInputValidations.collect { validationError ->
                validationError?.let {
                    when (it) {
                        WeatherInputValidations.TEMP_EMPTY -> {
                            binding.edtTemp.error = getString(R.string.required)
                        }
                        WeatherInputValidations.LOCATION_EMPTY -> {
                            binding.edtCity.error = getString(R.string.required)
                        }
                        WeatherInputValidations.WEATHER_CONDITION_EMPTY -> {
                            binding.edtCondition.error = getString(R.string.required)
                        }
                    }
                }

            }
        }
    }

    private fun subscribeToCurrentWeatherDataStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.currentWeatherDataStateFlow.collect{state->
                when(state){
                    is Resource.Loading->{
                        handleLoading(true)
                    }
                    is Resource.SUCCESS->{
                        handleLoading(false)
                        bindCurrentWeatherData(state.data)
                    }
                    is Resource.ERROR->{
                        handleLoading(false)
                        requireContext().handleError(state.error)
                    }
                    else->{}
                }
            }
        }
    }

    private fun bindCurrentWeatherData(data: WeatherItem?) {
        data?.let {
            binding.edtTemp.setText(data.temp.toString())
            binding.edtCity.setText(data.cityName)
            binding.edtCondition.setText(data.description)
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.llLoadingSection.isVisible=isLoading
    }

    private fun subscribeToFinish() {
        lifecycleScope.launchWhenResumed {
            viewModel.finish.collect{close->
                if (close){
                    findNavController().popBackStack()
                }
            }
        }
    }


    private fun loadedCurrentLocation(): Boolean {
        return viewModel.currentLocation.value != null
    }

    private fun hasLocationPermissions(): Boolean {
        return requireActivity().checkGps() && requireActivity().checkLocationPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionDenied = true
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            for (permission in grantResults) {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    permissionDenied = false
                    break
                }
            }
        }

        if (!permissionDenied) {
            viewModel.fetchCurrentLocation()
        }
    }
}