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
import com.example.admin.smarttranslator.Api.ClarifaiApi;
import com.example.admin.smarttranslator.Api.YandexTranslatorApi;
import com.example.admin.smarttranslator.Models.PhotoCard;
import com.example.admin.smarttranslator.Models.User;
import com.example.admin.smarttranslator.R;
import com.example.admin.smarttranslator.Services.DataBaseService;
import com.example.admin.smarttranslator.Services.FileStorageService;
import com.example.admin.smarttranslator.Services.PhotoService;

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
    private CheckBox checkBox;
    private int width;

    private String previous;
    private PhotoCard currentPhotoCard;
    private static PhotoService photoService;
    private static FileStorageService fileStorageService;

    private static int status; // 0 - before Camera , 1 - new PhotoCard, 2 - old PhotoCard
    private Uri lastUri;

    public RecyclerView getRecyclerView(){ return resultListView; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            init();
            mImageView.setImageBitmap(currentPhotoCard.getBitmap());
            RecyclerView.Adapter adapter = new ResultListAdapter(currentPhotoCard.getResultFrom(), currentPhotoCard.getResultTo(), width);
            resultListView.setAdapter(adapter);
            if(currentPhotoCard.isLiked())
                checkBox.setChecked(true);
        }
        else {
            status = 0;
            currentPhotoCard = User.getPhotoCardStorage().get(User.getPhotoCardStorage().size() - 1);
            init();
            if(getIntent().getStringExtra("Option").equals("Photo"))
                startDispatchTakePictureIntent();
            else
                startDispatchGalleryIntent();
        }

    }

    private void init(){
        ImageView from = findViewById(R.id.fromFlag);
        ImageView to = findViewById(R.id.toFlag);
        int fromId = getResources().getIdentifier("ic_"+currentPhotoCard.getLanFrom(), "mipmap", getPackageName());
        int toId = getResources().getIdentifier("ic_"+currentPhotoCard.getLanTo(), "mipmap", getPackageName());

        from.setBackgroundResource(fromId);
        to.setBackgroundResource(toId);

        mImageView = findViewById(R.id.ivPhoto);
        width = getWindowManager().getDefaultDisplay().getWidth();
        mImageView.getLayoutParams().height = width;
        checkBox = findViewById(R.id.heart);
        checkBox.setOnClickListener(view -> {
            currentPhotoCard.changeLike();
            if(currentPhotoCard.isLiked())
                User.getLikedPhotoCardStorage().add(currentPhotoCard);
            else
                User.getLikedPhotoCardStorage().remove(currentPhotoCard);
        });

        resultListView = findViewById(R.id.list_results);
        resultListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        resultListView.setLayoutManager(layoutManager);

        photoService = new PhotoService();
        fileStorageService = new FileStorageService();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (intent == null) {
                try {
                    Bitmap myBitmap = photoService.decodingPhoto(currentPhotoCard.getFilePath(), width, width);
                    photoService.compressPhoto(myBitmap, currentPhotoCard.getFilePath());
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
                    photoFile = fileStorageService.createImageFile(this);
                } catch (IOException ex) {
                    Log.e("!!!!!!!!!!!_IO", ex.toString());
                }
                if (photoFile != null)
                    currentPhotoCard.setFilePath(photoFile.getAbsolutePath());

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                photoService.compressPhoto(bitmap, currentPhotoCard.getFilePath());
                photoService.decodingPhoto(currentPhotoCard.getFilePath(), width, width);
                mImageView.setImageBitmap(bitmap);
                currentPhotoCard.setBitmap(bitmap);
                status = 1;
                onClickCheck();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            YandexTranslatorApi translatorApi = new YandexTranslatorApi(currentPhotoCard, currentPhotoCard.getNeoResult(), () -> {
                RecyclerView.Adapter adapter = new ResultListAdapter(currentPhotoCard.getResultFrom(), currentPhotoCard.getResultTo(), width);
                resultListView.setAdapter(adapter);
            });
            translatorApi.execute(currentPhotoCard.getLanFrom(), currentPhotoCard.getLanTo());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(status==1){
            DataBaseService db = new DataBaseService(this);
            db.addPhotoCard(currentPhotoCard);
        }
        else if(status == 2){
            DataBaseService db = new DataBaseService(this);
            db.updatePhotoCard(currentPhotoCard);
        }
    }

    private void startDispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = fileStorageService.createImageFile(this);
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

    private void startDispatchGalleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4);
    }

    private void startCropImageIntent(){
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

    public void onClickCheck() {
        ClarifaiApi task = new ClarifaiApi(Uri.fromFile(new File(currentPhotoCard.getFilePath())), currentPhotoCard, () -> {
            RecyclerView.Adapter adapter = new ResultListAdapter(currentPhotoCard.getResultFrom(), currentPhotoCard.getResultTo(), width);
            resultListView.setAdapter(adapter);
        });
        task.execute();
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