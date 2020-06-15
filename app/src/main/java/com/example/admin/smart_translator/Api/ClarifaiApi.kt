package com.example.admin.smart_translator.Api

import android.net.Uri
import android.os.AsyncTask

import com.example.admin.smart_translator.Models.PhotoCard

import java.io.File
import java.util.ArrayList

import clarifai2.api.ClarifaiBuilder
import clarifai2.dto.input.ClarifaiInput
import clarifai2.dto.model.output.ClarifaiOutput
import clarifai2.dto.prediction.Concept

class ClarifaiApi(private val currentUri: Uri, private val currentPhotoCard: PhotoCard, private val response: AsynResponse) : AsyncTask<Void, Void, Void>() {
    private var predictionResults: List<ClarifaiOutput<Concept>>? = null
    private var resultList: MutableList<String>? = null

    interface AsynResponse {
        fun processFinish()
    }

    override fun onPostExecute(aVoid: Void) {
        super.onPostExecute(aVoid)
        currentPhotoCard.neoResult = resultList
        if (resultList != null) {
            val translator = YandexTranslatorApi(currentPhotoCard, resultList) { response.processFinish() }
            translator.execute(currentPhotoCard.lanFrom, currentPhotoCard.lanTo)
        }
    }

    override fun doInBackground(vararg params: Void): Void? {

        val client = ClarifaiBuilder(clarifaiKey).buildSync()
        predictionResults = client.getDefaultModels().generalModel().predict()
                .withInputs(ClarifaiInput.forImage(File(currentUri.path))).executeSync().get()

        resultList = ArrayList()
        if (predictionResults != null && predictionResults!!.size > 0) {
            for (i in predictionResults!!.indices) {
                val clarifaiOutput = predictionResults!![i]
                val concepts = clarifaiOutput.data()
                if (concepts.size > 0) {
                    for (j in concepts.indices) {
                        resultList!!.add(concepts.get(j).name())
                    }
                }
            }
        }
        return null
    }

    companion object {
        private val clarifaiKey = "c151c5d2a75e4ba1aa32bb937af40643"
    }
}