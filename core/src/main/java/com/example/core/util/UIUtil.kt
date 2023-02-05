package com.example.core.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.data.AppException
import com.facebook.drawee.backends.pipeline.Fresco
import com.stfalcon.frescoimageviewer.ImageViewer
import java.io.File

fun ImageView.loadOfflineImage(
    path: String?,
) {
    path?.let {
        Glide.with(context)
            .load(Uri.fromFile(File(it)))
            .error(R.drawable.img_no_image)
            .centerCrop()
            .into(this)
    }
}

fun Activity.zoom(position: Int, imageUrls: List<String>) {

    val imageUris = imageUrls.map {
        try {
            return@map File(it).toUri()
        } catch (e: Exception) {
            return@map null
        }
    }
    ImageViewer.Builder<Any?>(this, imageUris)
        .setStartPosition(position)
        .hideStatusBar(false)
        .allowZooming(true)
        .allowSwipeToDismiss(true)
        .setBackgroundColorRes(android.R.color.black)
        .show()

}

fun initFresco(application: Application){
    Fresco.initialize(application)
}


fun Context.handleError(t: Throwable?) {
    val errorMsg=when(t){
        AppException.NetworkException->{
            getString(R.string.internet_connection_msg)
        }
        else->{
            getString(R.string.seomthing_went_wrong)
        }
    }
    Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
}