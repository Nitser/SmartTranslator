package com.example.admin.smart_translator.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileStorageUtils {

    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    fun writeFile(context: Context, key: String, `object`: Any) {
        val fileOutputStream = context.openFileOutput(key, Context.MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(`object`)
        objectOutputStream.close()
        fileOutputStream.close()
    }

    fun readFile(context: Context, key: String): Any {
        val fileInputStream = context.openFileInput(key)
        val objectInputStream = ObjectInputStream(fileInputStream)
        return objectInputStream.readObject()
    }

}
