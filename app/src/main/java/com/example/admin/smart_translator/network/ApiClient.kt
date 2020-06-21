package com.example.admin.smart_translator.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null

    private val yandexKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644"
    private val yandexUri = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=$yandexKey"

    fun getClient(): Retrofit {

        if (okHttpClient == null) {
            initOkHttp()
        }

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(yandexUri)
                    .client(okHttpClient!!)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}


//for (i in photoCard.neoResult) {
//    val yandexUrlFrom = "${ApiClient.yandexUri}${ApiClient.yandexKey}&text=$i&lang=$lanFrom"
//    val yandexUrlTo = "${ApiClient.yandexUri}${ApiClient.yandexKey}&text=$i&lang=$lanTo"
//    val yandexTranslateURLFrom = URL(yandexUrlFrom)
//    val yandexTranslateURLTo = URL(yandexUrlTo)
//
//    val httpJsonConnection = yandexTranslateURLFrom.openConnection() as HttpURLConnection
//    val httpURLConnection = yandexTranslateURLTo.openConnection() as HttpURLConnection
//    val inputStream = httpJsonConnection.inputStream
//    val inputStream1 = httpURLConnection.inputStream
//
//    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
//    val bufferedReader1 = BufferedReader(InputStreamReader(inputStream1))
//
//    val jsonStringBuilder = StringBuilder()
//    val stringBuilder = StringBuilder()
//
//    while ((jsonString = bufferedReader.readLine()) != null) {
//        jsonStringBuilder.append(jsonString).append("\n")
//    }
//
//    while ((jsonString = bufferedReader1.readLine()) != null) {
//        stringBuilder.append(jsonString).append("\n")
//    }
//
//    bufferedReader.close()
//    bufferedReader1.close()
//    inputStream.close()
//    inputStream1.close()
//    httpJsonConnection.disconnect()
//    httpURLConnection.disconnect()

//    var resultStringFrom = jsonStringBuilder.toString().trim { it <= ' ' }
//    var resultStringTo = stringBuilder.toString().trim { it <= ' ' }
//
//    resultStringFrom = resultStringFrom.substring(resultStringFrom.indexOf('[') + 1)
//    resultStringTo = resultStringTo.substring(resultStringTo.indexOf('[') + 1)
//
//    YandexTranslatorApi.resultsFrom.add(resultStringFrom.substring(0, resultStringFrom.indexOf("]")))
//    ApiClient.resultsTo.add(resultStringTo.substring(0, resultStringTo.indexOf("]")))
//
//}