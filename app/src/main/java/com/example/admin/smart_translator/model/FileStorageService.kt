package com.example.admin.smart_translator.model

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Так как класс не имеет состояния, то можно метод createImageFile сделать тоже статическим
class FileStorageService {

    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    companion object {

        fun writeFile(context: Context, key: String, `object`: Any) {
            val fos = context.openFileOutput(key, Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(`object`)
            oos.close()
            fos.close()
        }

        fun readFile(context: Context, key: String): Any {
            val fis = context.openFileInput(key)
            val ois = ObjectInputStream(fis)
            return ois.readObject()
        }
    }
}
