package com.example.admin.smarttranslator.Api;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.smarttranslator.Adapters.ResultListAdapter;
import com.example.admin.smarttranslator.Models.PhotoCard;
import com.example.admin.smarttranslator.Models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YandexTranslatorApi extends AsyncTask<String, Void, Void> {

    private final static String myYandexKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644";
    private List<String> from;
    private List<String> resultsFrom = new ArrayList<>();
    private List<String> resultsTo = new ArrayList<>();
    private PhotoCard currentPhotoCard;
    private AsynResponse response;

    public YandexTranslatorApi(PhotoCard currentPhotoCard, List<String> from, AsynResponse response) {
        this.from = from;
        this.currentPhotoCard = currentPhotoCard;
        this.response = response;
    }

    public interface AsynResponse {
        void processFinish();
    }

    @Override
    protected Void doInBackground(String... params) {
        String languageFrom = params[0];
        String languageTo = params[1];
        String jsonString;

        try {
            for (int i = 0; i < from.size(); i++) {
                String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + myYandexKey + "&text=" + from.get(i) + "&lang=" + languageFrom;
                String yandexUrlTo = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + myYandexKey + "&text=" + from.get(i) + "&lang=" + languageTo;
                URL yandexTranslateURL = new URL(yandexUrl);
                URL yandexTranslateURLTo = new URL(yandexUrlTo);

                HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) yandexTranslateURLTo.openConnection();
                InputStream inputStream = httpJsonConnection.getInputStream();
                InputStream inputStream1 = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));

                StringBuilder jsonStringBuilder = new StringBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                while ((jsonString = bufferedReader.readLine()) != null) {
                    jsonStringBuilder.append(jsonString).append("\n");
                }

                while ((jsonString = bufferedReader1.readLine()) != null) {
                    stringBuilder.append(jsonString).append("\n");
                }

                bufferedReader.close();
                bufferedReader1.close();
                inputStream.close();
                inputStream1.close();
                httpJsonConnection.disconnect();
                httpURLConnection.disconnect();

                String resultStringFrom = jsonStringBuilder.toString().trim();
                String resultStringTo = stringBuilder.toString().trim();

                resultStringFrom = resultStringFrom.substring(resultStringFrom.indexOf('[') + 1);
                resultStringTo = resultStringTo.substring(resultStringTo.indexOf('[') + 1);

                resultsFrom.add(resultStringFrom.substring(0, resultStringFrom.indexOf("]")));
                resultsTo.add(resultStringTo.substring(0, resultStringTo.indexOf("]")));

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(resultsFrom.size()==0 || resultsTo.size()==0){
            Log.d("!!!_Yandex", "Null lists");
        } else {
            for (int i = 0; i < (User.SIZE_OF_RESULTS < from.size() ? User.SIZE_OF_RESULTS : from.size()); i++) {

                String from = resultsFrom.get(i);
                String to = resultsTo.get(i);

                if (from.equals("err") || to.equals("err")) {
                    break;
                } else {
                    currentPhotoCard.getResultFrom().add(from.replaceAll("\"", ""));
                    currentPhotoCard.getResultTo().add(to.replaceAll("\"", ""));
                }
            }

            response.processFinish();
        }
    }
}
