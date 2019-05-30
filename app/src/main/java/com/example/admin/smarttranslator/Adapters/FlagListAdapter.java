package com.example.admin.smarttranslator.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.smarttranslator.Models.Language;
import com.example.admin.smarttranslator.R;

public class FlagListAdapter extends RecyclerView.Adapter<FlagListAdapter.FlagViewHolder> {

    private Language[] languages = Language.values();
    private Context context;
    boolean[] checked;
    boolean isReset = false;

    public FlagListAdapter(Context context){
        this.context=  context;
        checked = new boolean[languages.length];
    }

    public static class FlagViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout mView;

        public FlagViewHolder(RelativeLayout mView){
            super(mView);
            this.mView = mView;
        }
    }

    @NonNull
    @Override
    public FlagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_flags_view, viewGroup, false);
        FlagListAdapter.FlagViewHolder vh = new FlagListAdapter.FlagViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FlagViewHolder flagViewHolder, int i) {
        int resId = flagViewHolder.mView.getResources().getIdentifier("ic_"+languages[i].toString(), "mipmap", context.getPackageName());

        CheckBox checkBox = (CheckBox)flagViewHolder.mView.getChildAt(0);
        checkBox.setChecked(checked[i]);
        checkBox.setOnClickListener(v -> checked[i] = !checked[i]);
        if (isReset) {
            checkBox.setChecked(false);
            checked[i] = false;
        }


        ImageView flag = (ImageView)flagViewHolder.mView.getChildAt(1);
        flag.setBackgroundResource(resId);
        flag.setContentDescription(languages[i].toString());

        TextView name = (TextView)flagViewHolder.mView.getChildAt(2);
        name.setText(languages[i].name().substring(0, 1) + languages[i].name().substring(1).toLowerCase());
    }

    public void reset(){
        isReset = true;
        notifyDataSetChanged();
    }

    public boolean[] getChecked(){
        return checked;
    }

    @Override
    public int getItemCount() {
        return languages.length;
    }
}
