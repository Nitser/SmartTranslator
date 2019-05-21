package com.example.admin.smarttranslator.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.smarttranslator.Entities.PhotoCard;
import com.example.admin.smarttranslator.Entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseServise extends SQLiteOpenHelper {

    private String strSeparator = ",";

    public DataBaseServise(Context context) {
        super(context, "NeoPhotoCard", null, 1);
    }

    public void addPhotoCard( PhotoCard photoCard ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = makeContentValue(photoCard);
        db.insert("photocard", null, cv);
        this.close();
        db.close();
    }

    private ContentValues makeContentValue(PhotoCard photoCard){
        ContentValues cv = new ContentValues();
        cv.put("path", photoCard.getFilePath());
        cv.put("liked", (photoCard.isLiked()? 1 : 0));
        cv.put("lanfrom", photoCard.getLanFrom());
        cv.put("lanto", photoCard.getLanTo());
        cv.put("resultfrom", convertArrayToString(photoCard.getResultFrom()));
        cv.put("resultto", convertArrayToString(photoCard.getResultTo()));
        cv.put("resultneo", convertArrayToString(photoCard.getNeoResult()));
        return cv;
    }

    public void loadPhotoCard(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query("photocard", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int pathColIndex = c.getColumnIndex("path");
            int likedColIndex = c.getColumnIndex("liked");
            int fromColIndex = c.getColumnIndex("lanfrom");
            int toColIndex = c.getColumnIndex("lanto");
            int fromResultColIndex = c.getColumnIndex("resultfrom");
            int toResultColIndex = c.getColumnIndex("resultto");
            int neoResultColIndex = c.getColumnIndex("resultneo");


            do {
                PhotoCard photoCard = new PhotoCard();
                photoCard.setId(c.getInt(idColIndex));
                photoCard.setFilePath(c.getString(pathColIndex));
                photoCard.setLiked(c.getInt(likedColIndex) == 1);
                photoCard.setLanFrom(c.getString(fromColIndex));
                photoCard.setLanTo(c.getString(toColIndex));
                photoCard.setNeoResult(convertStringToArray(c.getString(neoResultColIndex)));
                photoCard.setResultFrom(convertStringToArray(c.getString(fromResultColIndex)));
                photoCard.setResultTo(convertStringToArray(c.getString(toResultColIndex)));

                User.getPhotoCardStorage().add(photoCard);
                if(photoCard.isLiked())
                    User.getLikedPhotoCardStorage().add(photoCard);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    private String convertArrayToString(List<String> array){
        StringBuilder str = new StringBuilder();
        for (int i = 0;i<array.size(); i++) {
            str.append(array.get(i));
            if(i<array.size()-1){
                str.append(strSeparator);
            }
        }
        return str.toString();
    }
    private List<String> convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return new ArrayList<>(Arrays.asList(arr));
    }

    public void updatePhotoCard(PhotoCard photoCard){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = makeContentValue(photoCard);
        db.update("photocard", cv, "id=" + photoCard.getId(), null);
        db.close();
    }

    public void deletePhotoCard(PhotoCard photoCard){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("photocard", "id" + "=" + photoCard.getId(), null);
        db.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table photocard ("
                + "id integer primary key autoincrement,"
                + "path text,"
                + "liked integer,"
                + "lanfrom text,"
                + "lanto text,"
                + "resultfrom text,"
                + "resultneo text,"
                + "resultto text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
