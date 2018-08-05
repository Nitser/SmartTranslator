package com.example.admin.smarttranslator;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class YandexTranslatorApi extends AsyncTask<String, Void, Void> {

    private String translationResult=null;
    private String[] languages = {"English", "Russian", "French", "Chinese", "German"};
    private String[] minLanguages = {"en-en", "en-ru", "en-fr", "en-zh", "en-de"};
    private Context ctx;
    private final static String myYandexKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644";

    public YandexTranslatorApi(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected Void doInBackground(String... params) {
        //String variables
        String textToBeTranslated = params[0];
        String languagePair = params[1];
        String jsonString;

        try {
            //Set up the translation call URL
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + myYandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair;

            URL yandexTranslateURL = new URL(yandexUrl);

            //Set Http Conncection, Input Stream, and Buffered Reader
            HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
            InputStream inputStream = httpJsonConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Set string builder and insert retrieved JSON result into it
            StringBuilder jsonStringBuilder = new StringBuilder();
            while ((jsonString = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(jsonString).append("\n");
            }

            //Close and disconnect
            bufferedReader.close();
            inputStream.close();
            httpJsonConnection.disconnect();

            //Making result human readable
            String resultString = jsonStringBuilder.toString().trim();

            resultString = resultString.substring(resultString.indexOf('[')+1);
            resultString = resultString.substring(0,resultString.indexOf("]"));

            translationResult = resultString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            translationResult = "err";
        } catch (IOException e) {
            e.printStackTrace();
            translationResult = "err";
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String getTranslationResult() {
        return translationResult;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String[] getMinLanguages() {
        return minLanguages;
    }
}
