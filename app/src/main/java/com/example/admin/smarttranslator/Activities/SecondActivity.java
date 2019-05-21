package com.example.admin.smarttranslator.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.smarttranslator.Adapters.ResultListAdapter;
import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.Entities.User;
import com.example.admin.smarttranslator.R;
import com.example.admin.smarttranslator.Services.DataBaseServise;
import com.example.admin.smarttranslator.Services.StoragePhotoService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView resultListView;
    private ImageView mImageView;
    private int width;

    private String previous;
    private PhotoCard currentPhotoCard;
    private StoragePhotoService storagePhotoService;

    private static int status; // 0 - before Camera , 1 - new PhotoCard, 2 - old PhotoCard
    private Uri lastUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.ivPhoto);
        width = getWindowManager().getDefaultDisplay().getWidth();
        mImageView.getLayoutParams().height = width;
        CheckBox checkBox = findViewById(R.id.heart);
        checkBox.setOnClickListener(view -> {
            currentPhotoCard.changeLike();
            if(currentPhotoCard.isLiked())
                User.getLikedPhotoCardStorage().add(currentPhotoCard);
            else
                User.getLikedPhotoCardStorage().remove(currentPhotoCard);
        });

        switch (getIntent().getStringExtra("PhotoCard_Parent")){
            case "home": previous = "home";break;
            case "history": previous = "history"; break;
            case "favorite":  previous = "favorite"; break;
        }

        if(getIntent().hasExtra("PhotoCard_Position")){
            status = 2;
            if(previous.equals("history"))
                currentPhotoCard = User.getPhotoCardStorage().get(getIntent().getIntExtra("PhotoCard_Position", 0));
            else
                currentPhotoCard = User.getLikedPhotoCardStorage().get(getIntent().getIntExtra("PhotoCard_Position", 0));
            loader();
            mImageView.setImageBitmap(currentPhotoCard.getBitmap());
            RecyclerView.Adapter adapter = new ResultListAdapter(currentPhotoCard.getResultFrom(), currentPhotoCard.getResultTo(), width);
            resultListView.setAdapter(adapter);
            if(currentPhotoCard.isLiked())
                checkBox.setChecked(true);
        }
        else {
            status = 0;
            currentPhotoCard = User.getPhotoCardStorage().get(User.getPhotoCardStorage().size() - 1);
            loader();
            if(getIntent().getStringExtra("Option").equals("Photo"))
                dispatchTakePictureIntent();
            else
                dispatchGallery();
        }

    }

    private void loader(){
        ImageView from = findViewById(R.id.fromFlag);
        ImageView to = findViewById(R.id.toFlag);
        int fromId = getResources().getIdentifier("ic_"+currentPhotoCard.getLanFrom(), "mipmap", getPackageName());
        int toId = getResources().getIdentifier("ic_"+currentPhotoCard.getLanTo(), "mipmap", getPackageName());

        from.setBackgroundResource(fromId);
        to.setBackgroundResource(toId);

        resultListView = findViewById(R.id.list_results);
        resultListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        resultListView.setLayoutManager(layoutManager);

        storagePhotoService = new StoragePhotoService(this);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = storagePhotoService.createImageFile();
            } catch (IOException ex) {
                Log.e("!!!!!!!!!!!_IO", ex.toString());
            }
            if (photoFile != null) {
                currentPhotoCard.setFilePath(photoFile.getAbsolutePath());
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private void dispatchGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (intent == null) {
                try {
                    Bitmap myBitmap = storagePhotoService.decodingPhoto(currentPhotoCard.getFilePath(), width, width);
                    storagePhotoService.compressPhoto(myBitmap, currentPhotoCard.getFilePath());
                    mImageView.setImageBitmap(myBitmap);
                    currentPhotoCard.setBitmap(myBitmap);
                    status = 1;
                    onClickCheck();
                } catch (Exception e) {
                    Toast.makeText(this, "Problem", Toast.LENGTH_LONG).show();
                    Log.e("!!!!!!!!!!!_E", e.toString());
                }
            }
        }
        else if(requestCode == 4 && resultCode == RESULT_OK && intent != null && intent.getData() != null){
            Uri uri = intent.getData();
            try {
                File photoFile = null;
                try {
                    photoFile = storagePhotoService.createImageFile();
                } catch (IOException ex) {
                    Log.e("!!!!!!!!!!!_IO", ex.toString());
                }
                if (photoFile != null)
                    currentPhotoCard.setFilePath(photoFile.getAbsolutePath());

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                storagePhotoService.decodingPhoto(bitmap, width, width);
                storagePhotoService.compressPhoto(bitmap, currentPhotoCard.getFilePath());
                storagePhotoService.decodingPhoto(currentPhotoCard.getFilePath(), width, width);
                mImageView.setImageBitmap(bitmap);
                currentPhotoCard.setBitmap(bitmap);
                status = 1;
                onClickCheck();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                Bitmap myBitmap = storagePhotoService.decodingPhoto(currentPhotoCard.getFilePath(), width, width);
//                storagePhotoService.compressPhoto(myBitmap, currentPhotoCard.getFilePath());
//                mImageView.setImageBitmap(myBitmap);
//                currentPhotoCard.setBitmap(myBitmap);
        }
        else if(requestCode == 3){
            ImageView from = findViewById(R.id.fromFlag);
            ImageView to = findViewById(R.id.toFlag);

            currentPhotoCard.setLanFrom(intent.getStringExtra("from"));
            currentPhotoCard.setLanTo(intent.getStringExtra("to"));

            int fromId = getResources().getIdentifier("ic_"+currentPhotoCard.getLanFrom(), "mipmap", getPackageName());
            int toId = getResources().getIdentifier("ic_"+currentPhotoCard.getLanTo(), "mipmap", getPackageName());

            from.setBackgroundResource(fromId);
            to.setBackgroundResource(toId);

            if(currentPhotoCard.getResultFrom().size()!=0){
                currentPhotoCard.getResultFrom().clear();
                currentPhotoCard.getResultTo().clear();
                resultListView.getAdapter().notifyDataSetChanged();
            }

            YandexTranslatorApi translatorApi = new YandexTranslatorApi(currentPhotoCard.getNeoResult());
            translatorApi.execute(currentPhotoCard.getLanFrom(), currentPhotoCard.getLanTo());
        }
    }

    public void onClickCheck() {
        ClarifaiApi task = new ClarifaiApi();
        task.setCurrentUri(Uri.fromFile(new File(currentPhotoCard.getFilePath())));
        task.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(status==1){
            DataBaseServise db = new DataBaseServise(this);
            db.addPhotoCard(currentPhotoCard);
        }
        else if(status == 2){
            DataBaseServise db = new DataBaseServise(this);
            db.updatePhotoCard(currentPhotoCard);
        }
    }

    public class ClarifaiApi extends AsyncTask<Void, Void, Void> {
        private List<ClarifaiOutput<Concept>> predictionResults;
        private final static String clarifaiKey="c151c5d2a75e4ba1aa32bb937af40643";
        private Uri currentUri;
        private List<String> resultList;

        void setCurrentUri(Uri currentUri){
            this.currentUri = currentUri;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            currentPhotoCard.setNeoResult(resultList);
            if(resultList!= null) {
                YandexTranslatorApi translator = new YandexTranslatorApi(resultList);
                translator.execute(currentPhotoCard.getLanFrom(), currentPhotoCard.getLanTo());
            }
        }
//             else {
//            LinearLayout linearLayout = findViewById(R.id.layout);
//            TextView textView = new TextView();
//            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            textView.setText(R.string.no_results);
//            textView.setMinHeight(80);
//            textView.setTextSize(18);
//            textView.setPadding(0, 35, 0, 0);
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//            linearLayout.addView(textView, linearLayout.getChildCount()-1);
//        }
//            List<String> arrayList = new ArrayList<>();
//            Collections.addAll(arrayList, translatorFrom.getLanguages());
//
//            List<String> arrayList2 = new ArrayList<>();
//            Collections.addAll(arrayList, translatorTo.getMinLanguages());
//
//            RecyclerView.Adapter adapter = new ResultListAdapter(fromResult, toResult, width);
//            resultListView.setAdapter(adapter);
//        }

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
    }

    public class YandexTranslatorApi extends AsyncTask<String, Void, Void> {

        private String translationResult = null;
        private final static String myYandexKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644";
        List<String> from;
        List<String> resultsFrom = new ArrayList<>();
        List<String> resultsTo = new ArrayList<>();

        public YandexTranslatorApi(List<String> from) {
            this.from = from;
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
                translationResult = "err";
            } catch (IOException e) {
                e.printStackTrace();
                translationResult = "err";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(resultsFrom.size()==0 || resultsTo.size()==0){
                Log.d("!!!_Yandex", "Null lists");
            }
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

            RecyclerView.Adapter adapter = new ResultListAdapter(currentPhotoCard.getResultFrom(), currentPhotoCard.getResultTo(), width);
            resultListView.setAdapter(adapter);

        }
    }

    private void cropImage(){
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(lastUri, "image/*");
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra("output", lastUri);
            cropIntent.putExtra("outputFormat", "JPEG");
            startActivityForResult(cropIntent, 2);
        }
        catch(ActivityNotFoundException cant){
            String errorMessage = "Ваше устройство не поддерживает работу с камерой!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickFlagButton(View view){
        Intent intent = new Intent(SecondActivity.this, FlagChangeActivity.class);
        startActivityForResult(intent, 3);
    }

    public void onClickBackButton(View view) {

        Intent intent = new Intent(SecondActivity.this, HomeActivity.class);
        intent.putExtra("Fragment", previous);
        startActivity(intent);
    }

}