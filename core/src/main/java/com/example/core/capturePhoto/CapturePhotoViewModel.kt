package com.example.core.capturePhoto

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CapturePhotoViewModel @Inject constructor() :ViewModel(){
    private val _capturedPhotoStateFlow = MutableStateFlow<String?>(null)
    val capturedPhotoStateFlow: StateFlow<String?> = _capturedPhotoStateFlow

    fun setCapturePhoto(photoPath:String?){
        _capturedPhotoStateFlow.value=photoPath
    }

}