package com.example.admin.smarttranslator.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.admin.smarttranslator.Fragments.CoffeeFragment;
import com.example.admin.smarttranslator.Fragments.FavoriteFragment;
import com.example.admin.smarttranslator.Fragments.HistoryFragment;
import com.example.admin.smarttranslator.Fragments.HomeFragment;
import com.example.admin.smarttranslator.Services.InternalStorage;
import com.example.admin.smarttranslator.Entities.Language;
import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(getIntent().hasExtra("Fragment")){
            switch (getIntent().getStringExtra("Fragment")){
                case "home": navigation.setSelectedItemId(R.id.navigation_home); break;
                case "history": navigation.setSelectedItemId(R.id.navigation_notifications); break;
                case "favorite": navigation.setSelectedItemId(R.id.navigation_dashboard); break;
            }
        }
        else {
            navigation.setSelectedItemId(R.id.navigation_home);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        loadFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.navigation_dashboard:
                        loadFragment(FavoriteFragment.newInstance());
                        return true;
                    case R.id.navigation_notifications:
                        loadFragment(HistoryFragment.newInstance());
                        return true;
                    case R.id.navigation_person:
                        loadFragment(CoffeeFragment.newInstance());
                        return true;
                }
                return false;
            };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
    }

}
