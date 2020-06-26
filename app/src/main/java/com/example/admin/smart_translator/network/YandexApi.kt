package com.example.admin.smart_translator.network

object YandexTranslatorApi {

    //    private val currentPhotoCard: PhotoCard
//    private val from: List<String>
    private val resultsFrom = ArrayList<String>()
    private val resultsTo = ArrayList<String>()
    private val yandexUri = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
    private val yandexKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644"

//    fun getYandexResult(lanFrom: String, lanTo: String, photoCard: PhotoCard) {
//        var jsonString: String
//
//        for (i in photoCard.translationResult) {
//            val yandexUrlFrom = "$yandexUri$yandexKey&text=$i&lang=$lanFrom"
//            val yandexUrlTo = "$yandexUri$yandexKey&text=$i&lang=$lanTo"
//            val yandexTranslateURLFrom = URL(yandexUrlFrom)
//            val yandexTranslateURLTo = URL(yandexUrlTo)
//
//            val httpJsonConnection = yandexTranslateURLFrom.openConnection() as HttpURLConnection
//            val httpURLConnection = yandexTranslateURLTo.openConnection() as HttpURLConnection
//            val inputStream = httpJsonConnection.inputStream
//            val inputStream1 = httpURLConnection.inputStream
//
//            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
//            val bufferedReader1 = BufferedReader(InputStreamReader(inputStream1))
//
//            val jsonStringBuilder = StringBuilder()
//            val stringBuilder = StringBuilder()
//
//            while ((jsonString = bufferedReader.readLine()) != null) {
//                jsonStringBuilder.append(jsonString).append("\n")
//            }
//
//            while ((jsonString = bufferedReader1.readLine()) != null) {
//                stringBuilder.append(jsonString).append("\n")
//            }
//
//            bufferedReader.close()
//            bufferedReader1.close()
//            inputStream.close()
//            inputStream1.close()
//            httpJsonConnection.disconnect()
//            httpURLConnection.disconnect()
//
//            var resultStringFrom = jsonStringBuilder.toString().trim { it <= ' ' }
//            var resultStringTo = stringBuilder.toString().trim { it <= ' ' }
//
//            resultStringFrom = resultStringFrom.substring(resultStringFrom.indexOf('[') + 1)
//            resultStringTo = resultStringTo.substring(resultStringTo.indexOf('[') + 1)
//
//            resultsFrom.add(resultStringFrom.substring(0, resultStringFrom.indexOf("]")))
//            resultsTo.add(resultStringTo.substring(0, resultStringTo.indexOf("]")))
//
//        }
//    }

//    override fun onPostExecute(aVoid: Void) {
//        super.onPostExecute(aVoid)
//        if (resultsFrom.size == 0 || resultsTo.size == 0) {
//        } else {
////            for (i in 0 until if (User.SIZE_OF_RESULTS < from.size) User.SIZE_OF_RESULTS else from.size) {
////
////                val from = resultsFrom[i]
////                val to = resultsTo[i]
////
////                if (from == "err" || to == "err") {
////                    break
////                } else {
////                    currentPhotoCard.translationFrom.add(from.replace("\"".toRegex(), ""))
////                    currentPhotoCard.translationTo.add(to.replace("\"".toRegex(), ""))
////                }
////            }
//
//            response.processFinish()
//        }
//    }

}
