package com.example.ecommercedemo.util

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.IOException
import java.io.InputStream
import android.media.MediaMetadataRetriever
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import java.io.File


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

fun getBitmapFromVide(): Bitmap{

    val sdcard = Environment.getExternalStorageDirectory()
    val file = File(sdcard, "myvid.mp4")
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(file.absolutePath);

    val bm = retriever.getFrameAtTime(1000*1000,MediaMetadataRetriever.OPTION_CLOSEST)
    retriever.release()
    return bm


}