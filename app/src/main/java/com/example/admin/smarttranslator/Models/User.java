package com.example.admin.smarttranslator.Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static final int SIZE_OF_RESULTS = 15;
    private static List<Language> currentFlags = new ArrayList<>();
    private static List<PhotoCard> photoCardStorage = new ArrayList<>();
    private static List<PhotoCard> likedPhotoCardStorage = new ArrayList<>();


    public static List<Language> getCurrentFlags() {
        return currentFlags;
    }

    public static void setCurrentFlags(List<Language> currentFlags) {
        User.currentFlags = currentFlags;
    }

    public static List<PhotoCard> getPhotoCardStorage() {
        return photoCardStorage;
    }

    public static void setPhotoCardStorage(List<PhotoCard> photoCardStorage) {
        User.photoCardStorage = photoCardStorage;
    }

    public static List<PhotoCard> getLikedPhotoCardStorage() {
        return likedPhotoCardStorage;
    }

    public static void setLikedPhotoCardStorage(List<PhotoCard> likedPhotoCardStorage) {
        User.likedPhotoCardStorage = likedPhotoCardStorage;
    }
}
