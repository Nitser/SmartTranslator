package com.example.admin.smarttranslator.Services

import android.content.Context
import android.os.Environment

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileStorageService {

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    companion object {

        @Throws(IOException::class)
        fun writeFile(context: Context, key: String, `object`: Any) {
            val fos = context.openFileOutput(key, Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(`object`)
            oos.close()
            fos.close()
        }

        @Throws(IOException::class, ClassNotFoundException::class)
        fun readFile(context: Context, key: String): Any {
            val fis = context.openFileInput(key)
            val ois = ObjectInputStream(fis)
            return ois.readObject()
        }
    }
}
