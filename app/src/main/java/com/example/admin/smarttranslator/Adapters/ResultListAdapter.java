package com.example.admin.smarttranslator.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.smarttranslator.R;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.MyViewHolder> {
    private List<String> fromData;
    private List<String> toData;
    private int width;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout mView;

        public MyViewHolder(RelativeLayout mView){
            super(mView);
            this.mView = mView;
        }
    }

    public ResultListAdapter(List<String> fromDataset, List<String> toDataset, int width){
        fromData = fromDataset;
        toData = toDataset;
        this.width = width;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_text_view, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            TextView from = (TextView)myViewHolder.mView.getChildAt(0);
            TextView to = (TextView)myViewHolder.mView.getChildAt(1);
            from.getLayoutParams().width = width/2;
            to.getLayoutParams().width = width/2;
            from.setText(fromData.get(i));
            to.setText(toData.get(i));
    }

    @Override
    public int getItemCount() {
        return fromData.size();
    }
}
