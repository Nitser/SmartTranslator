package com.example.admin.smarttranslator.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.R;
import com.example.admin.smarttranslator.Services.InternalStorage;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_layout);

        PhotoCard.width = getWindowManager().getDefaultDisplay().getWidth();

        InternalStorage internalStorage = new InternalStorage(this);
        internalStorage.execute();

        TextView label_1 = findViewById(R.id.label_1);
        TextView label_2 = findViewById(R.id.label_2);

        Typeface custom_font_label_1 = Typeface.createFromAsset(getAssets(),  "fonts/haetten.ttf");
        Typeface custom_font_label_2 = Typeface.createFromAsset(getAssets(),  "fonts/ITCEDSCR.TTF");

        label_1.setTypeface(custom_font_label_1);
        label_2.setTypeface(custom_font_label_2);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, 3*1000);
    }
}
