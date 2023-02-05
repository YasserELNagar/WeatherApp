package com.example.weatherapp.features.weatherHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.capturePhoto.CapturePhotoViewModel
import com.example.core.resource.Resource
import com.example.core.util.handleError
import com.example.core.util.shareImage
import com.example.core.util.zoom
import com.example.domain.model.WeatherItem
import com.example.weatherapp.databinding.FragmentWeatherHistoryBinding
import com.example.weatherapp.features.weatherHistory.adapter.WeatherHistoryItemsAdapter
import com.example.weatherapp.features.weatherHistory.adapter.WeatherItemActionType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherHistoryFragment : Fragment() {

    private lateinit var binding : FragmentWeatherHistoryBinding

    private val viewModel: WeatherHistoryViewModel by viewModels()

    private val capturePhotoViewModel :CapturePhotoViewModel by activityViewModels()

    private val weatherHistoryItemsAdapter: WeatherHistoryItemsAdapter by lazy {
        WeatherHistoryItemsAdapter{ position, item, actionType ->
            when(actionType){
                WeatherItemActionType.VIEW->{
                    requireActivity().zoom(position,viewModel.weatherItemsPhotos)
                }
                WeatherItemActionType.SHARE->{
                    requireActivity().shareImage(item?.photoPath?:"")
                }
            }
        }
    }

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
        setupWeatherItemsRV()
        setupClickListeners()
        subscribe()
    }

    private fun subscribe() {
        subscribeToCapturePhotoResult()
        subscribeToWeatherItemsStateFLow()
    }

    private fun subscribeToWeatherItemsStateFLow() {
        lifecycleScope.launchWhenResumed {
            viewModel.weatherItemsStateFlow.collect{state->
                when(state){
                    is Resource.Loading->{
                        //No Need for now
                    }
                    is Resource.SUCCESS->{
                        onWeatherItemsSuccess(state.data)
                    }
                    is Resource.ERROR->{
                        requireContext().handleError(state.error)
                    }
                    else->{}
                }
            }
        }
    }

    private fun onWeatherItemsSuccess(items: List<WeatherItem?>?) {
        weatherHistoryItemsAdapter.submitList(items)
    }

    private fun subscribeToCapturePhotoResult() {
        lifecycleScope.launchWhenResumed {
            capturePhotoViewModel.capturedPhotoStateFlow.collect{photoPath->
                photoPath?.let {
                    openCaptureWeatherData(photoPath)
                    capturePhotoViewModel.setCapturePhoto(null)
                }
            }
        }
    }

    private fun openCaptureWeatherData(photoPath: String) {
        val action = WeatherHistoryFragmentDirections.actionWeatherHistoryFragmentToCaptureWeatherDataFragment(photoPath)
        findNavController().navigate(action)
    }

    private fun setupClickListeners() {
        binding.btnAddWeather.setOnClickListener {
            val action = WeatherHistoryFragmentDirections.actionWeatherHistoryFragmentToNavCore()
            findNavController().navigate(action)
        }
    }

    private fun setupWeatherItemsRV() {
        binding.rvWeatherListItem.apply {
            adapter=weatherHistoryItemsAdapter
        }
    }


}