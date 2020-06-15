package com.example.admin.smarttranslator.Models

import android.graphics.Bitmap
import java.util.ArrayList

class PhotoCard {
    var id: Int = 0
    var filePath: String? = null
    var isLiked = false
    var lanFrom: String? = null
    var lanTo: String? = null
    var resultFrom: List<String> = ArrayList()
    var resultTo: List<String> = ArrayList()
    var neoResult: List<String>? = null
    var bitmap: Bitmap? = null

    constructor() {}

    constructor(path: String, lanFrom: String, lanTo: String, neoResult: List<String>, resultFrom: List<String>, resultTo: List<String>) {
        filePath = path
        this.lanFrom = lanFrom
        this.lanTo = lanTo
        this.neoResult = neoResult
        this.resultFrom = resultFrom
        this.resultTo = resultTo
    }

    constructor(lanFrom: String, lanTo: String) {
        this.lanFrom = lanFrom
        this.lanTo = lanTo
    }

    constructor(lan: String) {
        lanFrom = lan
    }

    fun changeLike() {
        isLiked = !isLiked
    }

    companion object {

        var width: Int = 0
    }
}
