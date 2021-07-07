package com.example.admin.smart_translator.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.admin.smart_translator.model.PhotoCard

import java.util.ArrayList

class SQLiteDatabaseUtils(context: Context) : SQLiteOpenHelper(context, "NeoPhotoCard", null, 1) {

    private val strSeparator: String
        get() = ","

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table photocard ("
                + "photoCardId integer primary key autoincrement,"
                + "path text,"
                + "liked integer,"
                + "lanfrom text,"
                + "lanto text,"
                + "resultfrom text,"
                + "resultneo text,"
                + "resultto text" + ");")
    }

    fun uploadPhotoCard(photoCard: PhotoCard) {
        writableDatabase.insert("photocard", null, makeContentValue(photoCard))
        writableDatabase.close()
        close()
    }

    fun downloadPhotoCard(): ArrayList<PhotoCard> {
        val c = writableDatabase.query("photocard", null, null, null, null, null, null)
        val photoCards = ArrayList<PhotoCard>()
        if (c.moveToFirst()) {
            do {
                val photoCard = PhotoCard()
                photoCard.photoCardId = c.getInt(c.getColumnIndex("photoCardId"))
                photoCard.photoFilePathFromStorage = c.getString(c.getColumnIndex("path"))
                photoCard.isLiked = c.getInt(c.getColumnIndex("liked")) == 1
                photoCard.flagIconPathFromLang = c.getInt(c.getColumnIndex("lanfrom"))
                photoCard.flagIconPathToLang = c.getInt(c.getColumnIndex("lanto"))
                photoCard.translationResult = ArrayList(convertStringToArray(c.getString(c.getColumnIndex("resultneo"))))
                photoCard.translationFrom = ArrayList(convertStringToArray(c.getString(c.getColumnIndex("resultfrom"))))
                photoCard.translationTo = ArrayList(convertStringToArray(c.getString(c.getColumnIndex("resultto"))))

                photoCards.add(photoCard)
            } while (c.moveToNext())
        }
        c.close()
        writableDatabase.close()
        return photoCards
    }

    fun updatePhotoCard(photoCard: PhotoCard) {
        val cv = makeContentValue(photoCard)
        writableDatabase.update("photocard", cv, "photoCardId=" + photoCard.photoCardId, null)
        writableDatabase.close()
    }

    fun deletePhotoCard(photoCard: PhotoCard) {
        writableDatabase.delete("photocard", "photoCardId" + "=" + photoCard.photoCardId, null)
        writableDatabase.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    private fun makeContentValue(photoCard: PhotoCard): ContentValues {
        val contentValue = ContentValues()
        with(contentValue) {
            put("path", photoCard.photoFilePathFromStorage)
            put("liked", if (photoCard.isLiked) 1 else 0)
            put("lanfrom", photoCard.flagIconPathFromLang)
            put("lanto", photoCard.flagIconPathToLang)
            put("resultfrom", convertArrayToString(photoCard.translationFrom))
            put("resultto", convertArrayToString(photoCard.translationTo))
            put("resultneo", convertArrayToString(photoCard.translationResult))
        }
        return contentValue
    }

    private fun convertArrayToString(array: List<String>): String {
        val stringBuilder = StringBuilder()
        for (i in array.indices) {
            stringBuilder.append(array[i])
            if (i < array.size - 1) {
                stringBuilder.append(strSeparator)
            }
        }
        return stringBuilder.toString()
    }

    private fun convertStringToArray(str: String): List<String> {
        val arr = str.split(strSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return ArrayList(listOf(*arr))
    }

}
