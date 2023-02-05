package com.example.core.util

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.core.R
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