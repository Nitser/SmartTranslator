package com.example.admin.smart_translator.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Log

import java.io.FileOutputStream
import java.io.IOException

class PhotoService {

    fun decodingPhoto(path: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inJustDecodeBounds = false
        var resultBitmap = BitmapFactory.decodeFile(path, options)
        // Поймал крэш Caused by: java.lang.IllegalStateException: resultBitmap must not be null при закрытии камеры
        // Надо добавить проверку на null и если что сделать так, чтобы метод возвращал null
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

//    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
////        val height = options.outHeight
////        val width = options.outWidth
////        var inSampleSize = 1
////
////        if (height > reqHeight || width > reqWidth) {
////
////            val halfHeight = height / 2
////            val halfWidth = width / 2
////
////            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
////                inSampleSize *= 2
////            }
////        }
//
//        return inSampleSize
//    }

    private fun changeOrientation(bitmap: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getExifAngle(path: String): Int {
        var angle = 0
        try {
            val exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270
            }
        } catch (e: IOException) {
            Log.w("TAG", "-- Error in setting image")
        } catch (oom: OutOfMemoryError) {
            Log.w("TAG", "-- OOM Error in setting image")
        }

        return angle
    }

}