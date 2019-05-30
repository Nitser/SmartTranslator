package com.example.admin.smarttranslator.Adapters;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.smarttranslator.Models.Language;
import com.example.admin.smarttranslator.R;

import java.util.ArrayList;
import java.util.List;

public class FlagAdapter extends RecyclerView.Adapter<FlagAdapter.FlagViewHolder> {
    private List<Language> flagData;
    private List<Integer> flagId;
    private Context context;

    static class FlagViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout mView;

        FlagViewHolder(RelativeLayout mView){
            super(mView);
            this.mView = mView;
        }
    }

    public FlagAdapter(List<Language> flags, Context context){
        flagData =  flags;
        this.context = context;
        flagId = new ArrayList<>();
    }

    @NonNull
    @Override
    public FlagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flags_view, viewGroup, false);
        return new FlagViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull FlagViewHolder myViewHolder, int i) {
        int resId = myViewHolder.mView.getResources().getIdentifier("ic_"+flagData.get(i%flagData.size()).toString(), "mipmap", context.getPackageName());

        ImageView flag = (ImageView)myViewHolder.mView.getChildAt(0);
        flag.setBackgroundResource(resId);
        flag.setContentDescription(flagData.get(i%flagData.size()).toString());

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}

