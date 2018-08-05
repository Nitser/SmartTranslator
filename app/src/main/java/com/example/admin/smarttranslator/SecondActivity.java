package com.example.admin.smarttranslator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
//import co.oriens.yandex_translate_android_api.TranslatorBackgroundTask;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class SecondActivity extends Activity implements View.OnTouchListener {

    private File directory;
    private ProgressBar progress;
    private ImageView mImageView;
    private Context mContext;
    private Uri lastUri;
    private Spinner spinner;
    private String translationResult=null;
    private List<String> resultList;
    private ClarifaiApi task;
    private YandexTranslatorApi translator;
    private String languagePair = "en-ru" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        createDirectory();
        mContext = this;
        mImageView = findViewById(R.id.ivPhoto);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);
        translator = new YandexTranslatorApi(mContext);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, translator.getLanguages());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setPrompt("Choose language");
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                languagePair = translator.getMinLanguages()[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        dispatchTakePictureIntent();
    }

    public void onClickPhoto(View view) {
        mImageView.setImageResource(0);
        dispatchTakePictureIntent();
    }
    public void onClickBack(View view) {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void createDirectory() {
            ////исправить! сделать нормальную проверку
            directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TestFolder");
            if (!directory.exists())
                directory.mkdirs();
            if (!directory.exists())
                Toast.makeText(getBaseContext(), "Directory is not created!", Toast.LENGTH_SHORT).show();
    }

    public void onClickCheck(View view) {
        task = new ClarifaiApi();
        translator = new YandexTranslatorApi(mContext);
        task.setCurrentUri(lastUri);
        resultList = null;
        task.execute();
        waitThis();
        resultList = task.getResultList();
        translator.execute(resultList.get(1),languagePair);
        waitThis2();
        translationResult = translator.getTranslationResult();
        if(translationResult.equals("err")){
            Toast.makeText(mContext, "err", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(mContext, translationResult, Toast.LENGTH_LONG).show();
        }
    }

    private void waitThis(){
        progress.setVisibility(View.VISIBLE);
        while(task.getResultList() == null) {}
        progress.setVisibility(ProgressBar.INVISIBLE);
    }

    private void waitThis2(){
        progress.setVisibility(View.VISIBLE);
        while(translator.getTranslationResult() == null) {}
        progress.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivityForResult(takePictureIntent,  1);
    }

    private Uri generateFileUri() {
        File file = new File(directory.getPath() + "/" + "photo_" + System.currentTimeMillis() + ".jpg");
        lastUri=Uri.fromFile(file);
        return Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //save first photo before editing
        if (requestCode == 1) {
            if (intent == null) {
                try {
                    mImageView.setImageURI(lastUri);
                    mImageView.animate().rotation(90);
                    cropImage();
                    // Iteration of Result
                } catch (Exception e) {
                    Toast.makeText(mContext, "Problem", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(requestCode==2){
            if(intent==null)
                Toast.makeText(mContext, "null", Toast.LENGTH_LONG).show();
            else {
                Bundle extras = intent.getExtras();
                    //Получаем изображение
                    try {
                      //  Bitmap thePic = extras.getParcelable("data");
                        //Отображаем в нем полученное с камеры изображение:
                      //  mImageView.setImageBitmap(thePic);
                        mImageView.setImageURI(lastUri);
                    } catch (NullPointerException ex) {
                        Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
            }
        }
    }

    private void cropImage(){
        try {
            //Вызываем стандартное действие захвата изображения:
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //Указываем тип изображения и Uri
            cropIntent.setDataAndType(lastUri, "image/*");
            //Настраиваем характеристики захвата:
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra("output", lastUri);
            cropIntent.putExtra("outputFormat", "JPEG");
            //Запускаем Activity и возвращаемся в метод onActivityResult
            startActivityForResult(cropIntent, 2);
        }
        catch(ActivityNotFoundException cant){
            //Показываем сообщение об ошибке:
            String errorMessage = "Ваше устройство не поддерживает работу с камерой!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
