package com.example.core.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.example.core.R

const val GPS_REQUEST_CODE = 4043


fun Activity.checkGps(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        true
    } else {
        showGPSDisabledAlertToUser()
        false
    }
}

private fun Activity.showGPSDisabledAlertToUser() {
    val alertDialogBuilder = AlertDialog.Builder(this)

    alertDialogBuilder.setMessage(R.string.please_open_gps)
        .setCancelable(false)
        .setPositiveButton(getString(R.string.gps_request)) { dialog, id ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(
                intent,
                GPS_REQUEST_CODE
            )
        }
    alertDialogBuilder.setNegativeButton(
        getString(R.string.cancel)
    ) { dialog, id -> }
    val alert = alertDialogBuilder.create()

    alert.show()
}

