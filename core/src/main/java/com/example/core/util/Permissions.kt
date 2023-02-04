package com.example.core.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import timber.log.Timber

const val CAMERA_PERMISSION_REQUEST_CODE = 101
const val CAMERA_PERMISSION = Manifest.permission.CAMERA

const val LOCATION_PERMISSION_REQUEST_CODE = 202
const val LOCATION_FINE_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
const val LOCATION_COARSE_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION


fun Activity.checkCameraPermission(): Boolean {
    Timber.i("Checking for camera permissions")
    val permissions = arrayOf(
        CAMERA_PERMISSION
    )

    return if (ContextCompat.checkSelfPermission(
            this,
            CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        Timber.i("camera permission found")
        true
    } else {
        Timber.i("camera permission not found and asking for it")

        ActivityCompat.requestPermissions(this, permissions, CAMERA_PERMISSION_REQUEST_CODE)
        false
    }
}


fun Activity.checkLocationPermissions(): Boolean {
    Timber.i("Checking for Location permissions")
    val permissions = arrayOf(
        LOCATION_COARSE_PERMISSION,
        LOCATION_FINE_PERMISSION
    )

    return if (ContextCompat.checkSelfPermission(
            this,
            LOCATION_COARSE_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
            this,
            LOCATION_FINE_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        Timber.i("Location permission found")
        true
    } else {
        Timber.i("Location permission not found and asking for it")

        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        false
    }
}