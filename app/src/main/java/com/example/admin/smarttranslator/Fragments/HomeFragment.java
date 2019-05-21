package com.example.admin.smarttranslator.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.smarttranslator.Activities.FlagListActivity;
import com.example.admin.smarttranslator.Activities.SecondActivity;
import com.example.admin.smarttranslator.Adapters.FlagAdapter;
import com.example.admin.smarttranslator.Entities.Language;
import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.Entities.User;
import com.example.admin.smarttranslator.LayoutManagers.CenterZoomLayoutManager;
import com.example.admin.smarttranslator.Activities.HomeActivity;
import com.example.admin.smarttranslator.R;


public class HomeFragment extends Fragment {

    private TextView from;
    private TextView to;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        from = getView().findViewById(R.id.from_lan);
        to = getView().findViewById(R.id.to_lan);

        RecyclerView flagView = getView().findViewById(R.id.list_flag);
        RecyclerView.LayoutManager layoutManager = new CenterZoomLayoutManager(getView().getContext(), 1, false, from);
        flagView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(flagView);

        RecyclerView flagView2 = getView().findViewById(R.id.list_flag2);
        RecyclerView.LayoutManager layoutManager2 = new CenterZoomLayoutManager(getView().getContext(), 1, false, to);
        flagView2.setLayoutManager(layoutManager2);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(flagView2);
        if(User.getCurrentFlags().size()==0){
            User.getCurrentFlags().add(Language.ENGLISH);
            User.getCurrentFlags().add(Language.RUSSIAN);
        }

        RecyclerView.Adapter adapter = new FlagAdapter(User.getCurrentFlags(), getView().getContext());
        RecyclerView.Adapter adapter2 = new FlagAdapter(User.getCurrentFlags(), getView().getContext());
        flagView.setAdapter(adapter);
        flagView2.setAdapter(adapter2);

        flagView.postDelayed(() -> flagView.smoothScrollToPosition(7), 0);
        flagView2.postDelayed(() -> flagView2.smoothScrollToPosition(7),0);

        ImageButton button = getView().findViewById(R.id.startPhoto);
        button.setOnClickListener(v -> {
            PhotoCard photoCard = new PhotoCard(String.valueOf(from.getText()).toLowerCase(), String.valueOf(to.getText()).toLowerCase());
            User.getPhotoCardStorage().add(photoCard);
            Intent intent = new Intent(getView().getContext(), SecondActivity.class);
            intent.putExtra("PhotoCard_Parent", "home");
            intent.putExtra("Option", "Photo");
            startActivity(intent);
        });

        Button button2 = getView().findViewById(R.id.chooseFlag);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(getView().getContext(), FlagListActivity.class);
            startActivity(intent);
        });

        ImageButton button3 = getView().findViewById(R.id.startGallery);
        button3.setOnClickListener(v -> {
            PhotoCard photoCard = new PhotoCard(String.valueOf(from.getText()).toLowerCase(), String.valueOf(to.getText()).toLowerCase());
            User.getPhotoCardStorage().add(photoCard);
            Intent intent = new Intent(getView().getContext(), SecondActivity.class);
            intent.putExtra("PhotoCard_Parent", "home");
            intent.putExtra("Option", "Gallery");
            startActivity(intent);

        });


    }

}
