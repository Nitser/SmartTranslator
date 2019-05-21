package com.example.admin.smarttranslator.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.admin.smarttranslator.Activities.SecondActivity;
import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.R;
import com.example.admin.smarttranslator.Services.StoragePhotoService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<PhotoCard> photos;
    private Context context;
    private StoragePhotoService storagePhotoService;

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mView;

        HistoryViewHolder(RelativeLayout mView){
            super(mView);
            this.mView = mView;
            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("PhotoCard_Position", getAdapterPosition());
                intent.putExtra("PhotoCard_Parent", "history");
                context.startActivity(intent);
            });
        }

    }

    public HistoryAdapter(List<PhotoCard> photos, Context context){
        this.photos =  photos;
        this.context = context;
        storagePhotoService = new StoragePhotoService(context);
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_history_layout, viewGroup, false);
        HistoryAdapter.HistoryViewHolder vh = new HistoryAdapter.HistoryViewHolder(view);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder myViewHolder, int i) {
        CardView cardView = (CardView)myViewHolder.mView.getChildAt(0);
        ConstraintLayout layout = (ConstraintLayout)cardView.getChildAt(0);

        ImageView imageView = (ImageView)layout.getChildAt(0);
        LinearLayout linearLayout = (LinearLayout)layout.getChildAt(1);

        ImageView flagFrom = (ImageView) linearLayout.getChildAt(0);
        ImageView flagTo = (ImageView) linearLayout.getChildAt(2);

        Bitmap bitmap = storagePhotoService.decodingPhoto(photos.get(i).getFilePath(), 150, 200);
        imageView.setImageBitmap(bitmap);

        int fromId = myViewHolder.mView.getResources().getIdentifier("ic_"+photos.get(i).getLanFrom(), "mipmap", myViewHolder.mView.getContext().getPackageName());
        int toId = myViewHolder.mView.getResources().getIdentifier("ic_"+photos.get(i).getLanTo(), "mipmap", myViewHolder.mView.getContext().getPackageName());
        flagFrom.setBackgroundResource(fromId);
        flagTo.setBackgroundResource(toId);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}