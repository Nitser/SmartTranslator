package com.example.admin.smart_translator.entities

import android.graphics.Bitmap
import java.util.ArrayList

class PhotoCard {
    var id: Int = 0
    var filePath: String = ""
    var isLiked = false
    var flagFromRes: Int = 0
    var flagToRes: Int = 0
    var resultFrom: ArrayList<String> = ArrayList()
    var resultTo: ArrayList<String> = ArrayList()
    var neoResult: ArrayList<String> = ArrayList()
    var bitmap: Bitmap? = null

}
