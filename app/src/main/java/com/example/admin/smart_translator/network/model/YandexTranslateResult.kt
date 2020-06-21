package com.example.admin.smart_translator.network.model

import com.google.gson.annotations.SerializedName

class YandexTranslateResult {

    @SerializedName("code")
    var code: Int = 0

    @SerializedName("lang")
    lateinit var fromToLang: String

    @SerializedName("text")
    lateinit var result: ArrayList<String>

}
