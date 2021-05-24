package com.jc666.dramas_sample_room.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 *
 * 主要用於Bitmap to base64 ,or base64 to bitmap fun
 *
 * @author JC666
 */

object BitmapAndBase64StringToolUtil {

    fun convertBytesToB64String(bytes: ByteArray): String? {
        return try {
            Base64.encodeToString(bytes, 0, bytes.size, Base64.NO_WRAP)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            ""
        }
    }

    fun convertStringToBitmap(b64: String): Bitmap? {
        val bytes = Base64.decode(b64, Base64.NO_WRAP)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun convertBitmapToBase64String(bitmap: Bitmap): String? {
        val bitmapStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapStream)
        val byteArray = bitmapStream.toByteArray()
        return convertBytesToB64String(byteArray)
    }



}