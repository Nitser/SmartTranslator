package com.example.admin.smarttranslator.Services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.admin.smarttranslator.Models.PhotoCard
import com.example.admin.smarttranslator.Models.User

import java.util.ArrayList
import java.util.Arrays

class DataBaseService(context: Context) : SQLiteOpenHelper(context, "NeoPhotoCard", null, 1) {

    fun addPhotoCard(photoCard: PhotoCard) {
        val db = this.writableDatabase
        val cv = makeContentValue(photoCard)
        db.insert("photocard", null, cv)
        this.close()
        db.close()
    }

    private fun makeContentValue(photoCard: PhotoCard): ContentValues {
        val cv = ContentValues()
        cv.put("path", photoCard.filePath)
        cv.put("liked", if (photoCard.isLiked) 1 else 0)
        cv.put("lanfrom", photoCard.lanFrom)
        cv.put("lanto", photoCard.lanTo)
        cv.put("resultfrom", convertArrayToString(photoCard.resultFrom))
        cv.put("resultto", convertArrayToString(photoCard.resultTo))
        cv.put("resultneo", convertArrayToString(photoCard.neoResult!!))
        return cv
    }

    fun loadPhotoCard() {
        val db = this.writableDatabase
        val c = db.query("photocard", null, null, null, null, null, null)

        if (c.moveToFirst()) {
            do {
                val photoCard = PhotoCard()
                photoCard.id = c.getInt(c.getColumnIndex("id"))
                photoCard.filePath = c.getString(c.getColumnIndex("path"))
                photoCard.isLiked = c.getInt(c.getColumnIndex("liked")) == 1
                photoCard.lanFrom = c.getString(c.getColumnIndex("lanfrom"))
                photoCard.lanTo = c.getString(c.getColumnIndex("lanto"))
                photoCard.neoResult = convertStringToArray(c.getString(c.getColumnIndex("resultneo")))
                photoCard.resultFrom = convertStringToArray(c.getString(c.getColumnIndex("resultfrom")))
                photoCard.resultTo = convertStringToArray(c.getString(c.getColumnIndex("resultto")))

                User.photoCardStorage.add(photoCard)
                if (photoCard.isLiked)
                    User.likedPhotoCardStorage.add(photoCard)
            } while (c.moveToNext())
        }
        c.close()
        db.close()
    }

    private fun convertArrayToString(array: List<String>): String {
        val str = StringBuilder()
        for (i in array.indices) {
            str.append(array[i])
            if (i < array.size - 1) {
                str.append(strSeparator)
            }
        }
        return str.toString()
    }

    private fun convertStringToArray(str: String): List<String> {
        val arr = str.split(strSeparator.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        return ArrayList(Arrays.asList(*arr))
    }

    fun updatePhotoCard(photoCard: PhotoCard) {
        val db = this.writableDatabase
        val cv = makeContentValue(photoCard)
        db.update("photocard", cv, "id=" + photoCard.id, null)
        db.close()
    }

    fun deletePhotoCard(photoCard: PhotoCard) {
        val db = this.writableDatabase
        db.delete("photocard", "id" + "=" + photoCard.id, null)
        db.close()

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table photocard ("
                + "id integer primary key autoincrement,"
                + "path text,"
                + "liked integer,"
                + "lanfrom text,"
                + "lanto text,"
                + "resultfrom text,"
                + "resultneo text,"
                + "resultto text" + ");")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        private val strSeparator = ","
    }
}
