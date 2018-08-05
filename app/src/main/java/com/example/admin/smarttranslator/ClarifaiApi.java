package com.example.admin.smarttranslator;

import android.net.Uri;
import android.os.AsyncTask;

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
    private List<String> resultList;

    public ClarifaiApi(){}

    public ClarifaiApi(Uri currentUri){
        this.currentUri = currentUri;
    }

    public void setCurrentUri(Uri currentUri){
        this.currentUri = currentUri;
    }

    public List<String> getResultList(){
        return resultList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

            final ClarifaiClient client = new ClarifaiBuilder(clarifaiKey).buildSync();
            predictionResults = client.getDefaultModels().generalModel().predict()
                    .withInputs(ClarifaiInput.forImage(new File(currentUri.getPath()))).executeSync().get();
            resultList = new ArrayList<String>();
            if (predictionResults != null && predictionResults.size() > 0) {
                for (int i = 0; i < predictionResults.size(); i++) {
                    ClarifaiOutput<Concept> clarifaiOutput = predictionResults.get(i);
                    List<Concept> concepts = clarifaiOutput.data();
                    if (concepts != null && concepts.size() > 0) {
                        for (int j = 0; j < concepts.size(); j++) {
                            resultList.add(concepts.get(j).name());
                        }
                    }
                }
            }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
