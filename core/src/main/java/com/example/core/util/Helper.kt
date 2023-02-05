package com.example.core.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.core.R
import java.io.File




fun Int.dpFromPx(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}

fun Int.pxFromDp(context: Context): Int {
    return this * context.resources.displayMetrics.density.toInt()
}

@RequiresApi(Build.VERSION_CODES.DONUT)
fun Activity.shareImage(imagePath: String, packageName: String? = null) {

    val imageFile=File(imagePath)

    val imageUri = FileProvider.getUriForFile(
        this,
        "com.example.weatherapp.fileprovider",
        imageFile)

    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, imageUri)
        packageName?.let {
            setPackage(it)
        }
        type = "image/jpeg"
    }
    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)))
}



