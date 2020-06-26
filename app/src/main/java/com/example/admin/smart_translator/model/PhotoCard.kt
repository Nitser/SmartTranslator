package com.example.admin.smart_translator.model

import android.graphics.Bitmap
import java.util.ArrayList

class PhotoCard {

    var photoCardId: Int = 0
    var photoFilePathFromStorage: String = ""
    var isLiked = false
    var flagIconPathFromLang: Int = 0
    var flagIconPathToLang: Int = 0
    var translationFrom: ArrayList<String> = ArrayList()
    var translationTo: ArrayList<String> = ArrayList()
    var translationResult: ArrayList<String> = ArrayList()
    var photoBitmap: Bitmap? = null

}
