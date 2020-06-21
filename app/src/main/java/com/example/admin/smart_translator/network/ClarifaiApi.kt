package com.example.admin.smart_translator.network

import android.net.Uri
import clarifai2.api.ClarifaiBuilder
import clarifai2.dto.input.ClarifaiInput
import clarifai2.dto.model.output.ClarifaiOutput
import clarifai2.dto.prediction.Concept
import java.io.File

object ClarifaiApi {

    private var predictionResults: List<ClarifaiOutput<Concept>>? = null
    private var resultList = ArrayList<String>()

    private const val clarifaiKey = "c151c5d2a75e4ba1aa32bb937af40643"

    fun getClarifaiResult(currentUri: Uri) {

        val client = ClarifaiBuilder(clarifaiKey).buildSync()
        predictionResults = client.defaultModels.generalModel().predict()
                .withInputs(ClarifaiInput.forImage(File(currentUri.path!!))).executeSync().get()

        if (predictionResults != null && predictionResults!!.isNotEmpty()) {
            for (i in predictionResults!!.indices) {
                val clarifaiOutput = predictionResults!![i]
                val concepts = clarifaiOutput.data()
                if (concepts.size > 0) {
                    for (j in concepts.indices) {
                        resultList.add(concepts[j].name()!!)
                    }
                }
            }
        }

    }

//        currentPhotoCard.neoResult = resultList
//        if (resultList != null) {
//            val translator = YandexTranslatorApi(currentPhotoCard, resultList) { response.processFinish() }
//            translator.execute(currentPhotoCard.lanFrom, currentPhotoCard.lanTo)
//        }

}