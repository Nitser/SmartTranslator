package com.example.admin.smarttranslator.Services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.util.Log

import java.io.FileOutputStream
import java.io.IOException

class PhotoService {

    fun decodingPhoto(path: String, width: Int, height: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, width, height)
        options.inJustDecodeBounds = false
        var resultBitmap = BitmapFactory.decodeFile(path, options)

        resultBitmap = changeOrientation(resultBitmap, getExifAngle(path))
        return resultBitmap
    }

    fun compressPhoto(bitmap: Bitmap, path: String) {

        try {
            FileOutputStream(path).use { out ->

                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun changeOrientation(bitmap: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getExifAngle(path: String): Int {
        var angle = 0
        try {
            val ei = androidx.exifinterface.media.ExifInterface(path)
            val orientation = ei.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, androidx.exifinterface.media.ExifInterface.ORIENTATION_UNDEFINED)
            when (orientation) {
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90 -> angle = 90
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180 -> angle = 180
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270 -> angle = 270

                else -> angle = 0
            }
        } catch (e: Exception) {
            Log.d("!!!!!!_ANGLE", e.toString())
        }

        return angle
    }

}
