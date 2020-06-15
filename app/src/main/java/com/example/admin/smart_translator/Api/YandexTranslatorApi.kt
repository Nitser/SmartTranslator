package com.example.admin.smart_translator.Api

import android.os.AsyncTask
import android.util.Log

import com.example.admin.smart_translator.Models.PhotoCard
import com.example.admin.smart_translator.Models.User

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

class YandexTranslatorApi(private val currentPhotoCard: PhotoCard, private val from: List<String>, private val response: AsynResponse) : AsyncTask<String, Void, Void>() {
    private val resultsFrom = ArrayList<String>()
    private val resultsTo = ArrayList<String>()

    interface AsynResponse {
        fun processFinish()
    }

    override fun doInBackground(vararg params: String): Void? {
        val languageFrom = params[0]
        val languageTo = params[1]
        var jsonString: String

        try {
            for (i in from.indices) {
                val yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + myYandexKey + "&text=" + from[i] + "&lang=" + languageFrom
                val yandexUrlTo = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + myYandexKey + "&text=" + from[i] + "&lang=" + languageTo
                val yandexTranslateURL = URL(yandexUrl)
                val yandexTranslateURLTo = URL(yandexUrlTo)

                val httpJsonConnection = yandexTranslateURL.openConnection() as HttpURLConnection
                val httpURLConnection = yandexTranslateURLTo.openConnection() as HttpURLConnection
                val inputStream = httpJsonConnection.inputStream
                val inputStream1 = httpURLConnection.inputStream

                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val bufferedReader1 = BufferedReader(InputStreamReader(inputStream1))

                val jsonStringBuilder = StringBuilder()
                val stringBuilder = StringBuilder()

                while ((jsonString = bufferedReader.readLine()) != null) {
                    jsonStringBuilder.append(jsonString).append("\n")
                }

                while ((jsonString = bufferedReader1.readLine()) != null) {
                    stringBuilder.append(jsonString).append("\n")
                }

                bufferedReader.close()
                bufferedReader1.close()
                inputStream.close()
                inputStream1.close()
                httpJsonConnection.disconnect()
                httpURLConnection.disconnect()

                var resultStringFrom = jsonStringBuilder.toString().trim { it <= ' ' }
                var resultStringTo = stringBuilder.toString().trim { it <= ' ' }

                resultStringFrom = resultStringFrom.substring(resultStringFrom.indexOf('[') + 1)
                resultStringTo = resultStringTo.substring(resultStringTo.indexOf('[') + 1)

                resultsFrom.add(resultStringFrom.substring(0, resultStringFrom.indexOf("]")))
                resultsTo.add(resultStringTo.substring(0, resultStringTo.indexOf("]")))

            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(aVoid: Void) {
        super.onPostExecute(aVoid)
        if (resultsFrom.size == 0 || resultsTo.size == 0) {
            Log.d("!!!_Yandex", "Null lists")
        } else {
            for (i in 0 until if (User.SIZE_OF_RESULTS < from.size) User.SIZE_OF_RESULTS else from.size) {

                val from = resultsFrom[i]
                val to = resultsTo[i]

                if (from == "err" || to == "err") {
                    break
                } else {
                    currentPhotoCard.resultFrom.add(from.replace("\"".toRegex(), ""))
                    currentPhotoCard.resultTo.add(to.replace("\"".toRegex(), ""))
                }
            }

            response.processFinish()
        }
    }

    companion object {

        private val myYandexKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644"
    }
}
