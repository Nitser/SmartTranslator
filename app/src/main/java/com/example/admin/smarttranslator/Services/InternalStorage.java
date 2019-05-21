package com.example.admin.smarttranslator.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.Entities.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class InternalStorage extends AsyncTask{

    private Context context;

    public InternalStorage(Context context) { this.context = context; }

    @Override
    protected Object doInBackground(Object[] objects) {
        DataBaseServise db = new DataBaseServise(context);
        db.loadPhotoCard();
        StoragePhotoService storagePhotoService = new StoragePhotoService(context);
        for(PhotoCard photoCard: User.getPhotoCardStorage()){
            File file = new File(photoCard.getFilePath());
            if(file.exists()) {
                photoCard.setBitmap(storagePhotoService.decodingPhoto(photoCard.getFilePath(), PhotoCard.width, PhotoCard.width));
            }
            else {
                db.deletePhotoCard(photoCard);
                User.getPhotoCardStorage().remove(photoCard);
                User.getLikedPhotoCardStorage().remove(photoCard);
            }
        }
        return null;
    }

    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String key) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }
}