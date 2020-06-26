package com.example.admin.smart_translator.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.FileOutputStream

class PhotoUtils {

    fun decoding(imagePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imagePath, options)
        options.inJustDecodeBounds = false
        var resultBitmap = BitmapFactory.decodeFile(imagePath, options)
        resultBitmap = changeOrientation(resultBitmap, getExifAngle(imagePath))
        return resultBitmap
    }

    fun compression(bitmap: Bitmap, path: String) {
        FileOutputStream(path).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out)
        }
    }

    private fun changeOrientation(bitmap: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getExifAngle(imagePath: String): Int {
        var angle = 0
        val exif = ExifInterface(imagePath)
        val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL)

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> angle = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> angle = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> angle = 270
        }
        return angle
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

}
