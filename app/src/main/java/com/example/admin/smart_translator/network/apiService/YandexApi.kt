package com.example.admin.smart_translator.network.apiService

import com.example.admin.smart_translator.network.model.YandexTranslateResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApi {

    @GET
    fun getUser(@Query("text") text: String, @Query("lang") language: String): Single<YandexTranslateResult>

}
