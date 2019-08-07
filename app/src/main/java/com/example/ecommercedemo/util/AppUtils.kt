package com.example.ecommercedemo.util

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.IOException
import java.io.InputStream


fun getBitmapFromAsset(context: Context, filePath: String): Bitmap? {
    val assetManager = context.assets

    val istr: InputStream
    var bitmap: Bitmap? = null
    try {
        istr = assetManager.open(filePath)
        bitmap = BitmapFactory.decodeStream(istr)
    } catch (e: IOException) {
        e.printStackTrace()
        // handle exception
    }

    return bitmap
}