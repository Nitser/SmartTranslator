package com.example.admin.smart_translator.Models

import java.util.ArrayList

object User {
    val SIZE_OF_RESULTS = 15
    var currentFlags: List<Language> = ArrayList()
    var photoCardStorage: List<PhotoCard> = ArrayList()
    var likedPhotoCardStorage: List<PhotoCard> = ArrayList()
}
