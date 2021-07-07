package com.example.admin.smart_translator.network

import android.net.Uri
import clarifai2.api.ClarifaiBuilder
import clarifai2.dto.input.ClarifaiInput
import clarifai2.dto.model.output.ClarifaiOutput
import clarifai2.dto.prediction.Concept
import com.example.admin.smart_translator.app.MainActivity
import java.io.File

object ClarifaiApiHandler {

    private var predictionResults: List<ClarifaiOutput<Concept>>? = null
    private var itemResults = ArrayList<String>()

    fun handleClarifaiItemResults(currentUri: Uri) {

        val client = ClarifaiBuilder(MainActivity.clarifaiApiKey).buildSync()
        predictionResults = client.defaultModels.generalModel().predict()
                .withInputs(ClarifaiInput.forImage(File(currentUri.path!!))).executeSync().get()

        if (predictionResults != null && predictionResults!!.isNotEmpty()) {
            for (i in predictionResults!!.indices) {
                val clarifaiOutput = predictionResults!![i]
                val concepts = clarifaiOutput.data()
                if (concepts.size > 0) {
                    for (j in concepts.indices) {
                        itemResults.add(concepts[j].name()!!)
                    }
                }
            }
        }
    }

//        currentPhotoCard.translationResult = resultList
//        if (resultList != null) {
//            val translator = YandexTranslatorApi(currentPhotoCard, resultList) { response.processFinish() }
//            translator.execute(currentPhotoCard.lanFrom, currentPhotoCard.lanTo)
//        }

}