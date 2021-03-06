package com.example.admin.smarttranslator.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.smarttranslator.Adapters.HistoryAdapter;
import com.example.admin.smarttranslator.Models.User;
import com.example.admin.smarttranslator.R;

public class HistoryFragment extends Fragment {

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = getView().findViewById(R.id.historyView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getView().getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        if(User.getPhotoCardStorage().size()==0){
//            photos.add(new PhotoCard("en", "ru"));
        } else{
            RecyclerView.Adapter adapter = new HistoryAdapter(User.getPhotoCardStorage(), getView().getContext());
            recyclerView.setAdapter(adapter);

        }


    }

}
