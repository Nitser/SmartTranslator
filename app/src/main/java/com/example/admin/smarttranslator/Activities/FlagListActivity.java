package com.example.admin.smarttranslator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.example.admin.smarttranslator.Adapters.FlagListAdapter;
import com.example.admin.smarttranslator.Entities.Language;
import com.example.admin.smarttranslator.Entities.User;
import com.example.admin.smarttranslator.R;

public class FlagListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flag_list_layout);

        RecyclerView flagView = findViewById(R.id.flags_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        flagView.setLayoutManager(layoutManager);
        adapter = new FlagListAdapter(this);

//        flagView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean b) {
//
//            }
//        });
        flagView.setAdapter(adapter);

    }

    public void onClickBackButton(View view) {
        FlagListAdapter ad = (FlagListAdapter) adapter;
        boolean[] checked = ad.getChecked();
        User.getCurrentFlags().clear();
        for(int i = 0; i< Language.values().length; i++){
            if(checked[i]){
                User.getCurrentFlags().add(Language.values()[i]);
            }
        }
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
    }

    public void onClickBackReset(View view){
        FlagListAdapter ad = (FlagListAdapter) adapter;
        ad.reset();
    }
}
