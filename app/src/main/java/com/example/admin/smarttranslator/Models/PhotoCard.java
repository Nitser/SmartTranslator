package com.example.admin.smarttranslator.Models;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;

public class PhotoCard {
    private int id;
    private String filePath;
    private boolean liked = false;
    private String lanFrom;
    private String lanTo;
    private List<String> resultFrom = new ArrayList<>();
    private List<String> resultTo = new ArrayList<>();
    private List<String> neoResult;
    private Bitmap bitmap;

    public static int width;

    public PhotoCard(){}

    public PhotoCard(String path, String lanFrom, String lanTo, List<String> neoResult , List<String> resultFrom, List<String> resultTo){
        filePath = path;
        this.lanFrom = lanFrom;
        this.lanTo = lanTo;
        this.neoResult = neoResult;
        this.resultFrom = resultFrom;
        this.resultTo = resultTo;
    }

    public PhotoCard(String lanFrom, String lanTo){
        this.lanFrom = lanFrom;
        this.lanTo = lanTo;
    }

    public Bitmap getBitmap(){ return bitmap; }

    public void setBitmap(Bitmap bitmap){ this.bitmap = bitmap; }

    public String getFilePath(){return filePath;}

    public void setFilePath(String path){ filePath = path; }

    public PhotoCard(String lan){
        lanFrom = lan;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked){ this.liked = liked; }

    public void changeLike(){ liked = !liked; }

    public String getLanFrom() {
        return lanFrom;
    }

    public void setLanFrom(String lanFrom) {
        this.lanFrom = lanFrom;
    }

    public String getLanTo() {
        return lanTo;
    }

    public void setLanTo(String lanTo) {
        this.lanTo = lanTo;
    }

    public List<String> getResultFrom() {
        return resultFrom;
    }

    public void setResultFrom(List<String> resultFrom) {
        this.resultFrom = resultFrom;
    }

    public List<String> getResultTo() {
        return resultTo;
    }

    public void setResultTo(List<String> resultTo) {
        this.resultTo = resultTo;
    }

    public List<String> getNeoResult() {
        return neoResult;
    }

    public void setNeoResult(List<String> result){ neoResult = result; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
