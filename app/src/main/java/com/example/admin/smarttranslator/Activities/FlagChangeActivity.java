package com.example.admin.smarttranslator.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.TextView;

import com.example.admin.smarttranslator.Adapters.FlagAdapter;
import com.example.admin.smarttranslator.Entities.User;
import com.example.admin.smarttranslator.LayoutManagers.CenterZoomLayoutManager;
import com.example.admin.smarttranslator.R;

public class FlagChangeActivity extends AppCompatActivity {

    private  TextView from;
    private TextView to;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flag_change_layout);

        from = findViewById(R.id.from_lan);
        to = findViewById(R.id.to_lan);

        RecyclerView flagView = findViewById(R.id.list_flag);
        RecyclerView.LayoutManager layoutManager = new CenterZoomLayoutManager(this, 1, false, from);
        flagView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(flagView);
        RecyclerView.Adapter adapter = new FlagAdapter(User.getCurrentFlags(), this);
        flagView.setAdapter(adapter);

        RecyclerView flagView2 = findViewById(R.id.list_flag2);
        RecyclerView.LayoutManager layoutManager2 = new CenterZoomLayoutManager(this, 1, false, to);
        flagView2.setLayoutManager(layoutManager2);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(flagView2);
        RecyclerView.Adapter adapter2 = new FlagAdapter(User.getCurrentFlags(),this);
        flagView2.setAdapter(adapter2);

        flagView.postDelayed(() -> flagView.smoothScrollToPosition(7), 0);
        flagView2.postDelayed(() -> flagView2.smoothScrollToPosition(7),0);
    }

    public void onClickBackButton(View view) {
        Intent intent = new Intent();

        intent.putExtra("from", String.valueOf(from.getText()).toLowerCase());
        intent.putExtra("to", String.valueOf(to.getText()).toLowerCase());

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
