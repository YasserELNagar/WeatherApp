package com.example.core.capturePhoto

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.core.capturePhoto.Constns.CAPTURE_PHOTO_PATH
import com.example.core.capturePhoto.Constns.REQUEST_CAPTURE_PHOTO_CODE
import com.example.core.databinding.FragmentCapturePhotoBinding
import com.example.core.util.CAMERA_PERMISSION_REQUEST_CODE
import com.example.core.util.checkCameraPermission
import com.example.core.util.getPhotoFile
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@AndroidEntryPoint
class CapturePhotoFragment : Fragment() {
    private lateinit var binding: FragmentCapturePhotoBinding
    private lateinit var mFutureCameraProvider: ListenableFuture<ProcessCameraProvider>
    private lateinit var mImageCapture: ImageCapture
    private val capturePhotoViewModel :CapturePhotoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCapturePhotoBinding.inflate(inflater)
        if (requireActivity().checkCameraPermission()) {
            initCameraProvider()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivTakePhoto.setOnClickListener {
            takePhoto()
        }
    }

    private fun initCameraProvider() {
        mFutureCameraProvider = ProcessCameraProvider.getInstance(requireContext())
        mFutureCameraProvider.addListener(Runnable {
            val cameraProvider = mFutureCameraProvider.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {

        //get preview surface provider
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.previewView.surfaceProvider)
        }


        //set the back camera as the default
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        mImageCapture = ImageCapture.Builder().build()

        try {
            // Unbind use cases before rebinding
            cameraProvider?.unbindAll()

            // Bind use cases to camera
            cameraProvider?.bindToLifecycle(
                this, cameraSelector, preview, mImageCapture
            )

        } catch (exc: Exception) {
            Timber.e("Use case binding failed $exc")
        }
    }


    private fun takePhoto() {

        val photoName = "IMAGE_"+System.currentTimeMillis()
        //create photo file to save photo in
        val photoFile = requireActivity().getPhotoFile(photoName)

        //output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        mImageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Timber.i("Photo capture succeeded: ${photoFile.path}")
                    sendResultBack(photoFile.path)
                }
            })

    }

    private fun sendResultBack(photoPath: String?) {
        capturePhotoViewModel.setCapturePhoto(photoPath)
        findNavController().popBackStack()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionDenied = true
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            for (permission in grantResults) {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    permissionDenied = false
                    break
                }
            }
        }


        if (!permissionDenied) {
            initCameraProvider()
        } else {
            activity?.finish()
        }
    }
}