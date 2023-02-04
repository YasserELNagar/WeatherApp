package com.example.weatherapp.features.weatherHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.core.databinding.FragmentCapturePhotoBinding
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherHistoryBinding

class WeatherHistoryFragment : Fragment() {

    private lateinit var binding : FragmentWeatherHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentWeatherHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddWeather.setOnClickListener {
            val action = WeatherHistoryFragmentDirections.actionWeatherHistoryFragmentToNavCore()
            findNavController().navigate(action)
        }
    }


}