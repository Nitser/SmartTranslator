package com.example.admin.smarttranslator.Api;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.admin.smarttranslator.Models.PhotoCard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class ClarifaiApi extends AsyncTask<Void, Void, Void> {
    private List<ClarifaiOutput<Concept>> predictionResults;
    private final static String clarifaiKey="c151c5d2a75e4ba1aa32bb937af40643";
    private Uri currentUri;
    private PhotoCard currentPhotoCard;
    private List<String> resultList;
    private AsynResponse response;

    public ClarifaiApi(Uri currentUri, PhotoCard currentPhotoCard, AsynResponse response) {
        this.currentUri = currentUri;
        this.currentPhotoCard = currentPhotoCard;
        this.response = response;
    }

    public interface AsynResponse {
        void processFinish();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        currentPhotoCard.setNeoResult(resultList);
        if(resultList!= null) {
            YandexTranslatorApi translator = new YandexTranslatorApi(currentPhotoCard, resultList, () -> {
                response.processFinish();
            });
            translator.execute(currentPhotoCard.getLanFrom(), currentPhotoCard.getLanTo());
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        final ClarifaiClient client = new ClarifaiBuilder(clarifaiKey).buildSync();
        predictionResults = client.getDefaultModels().generalModel().predict()
                .withInputs(ClarifaiInput.forImage(new File(currentUri.getPath()))).executeSync().get();

        resultList = new ArrayList<>();
        if (predictionResults != null && predictionResults.size() > 0) {
            for (int i = 0; i < predictionResults.size(); i++) {
                ClarifaiOutput<Concept> clarifaiOutput = predictionResults.get(i);
                List<Concept> concepts = clarifaiOutput.data();
                if (concepts.size() > 0) {
                    for (int j = 0; j < concepts.size(); j++) {
                        resultList.add(concepts.get(j).name());
                    }
                }
            }
        }
        return null;
    }
}