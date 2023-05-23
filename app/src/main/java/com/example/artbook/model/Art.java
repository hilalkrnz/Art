package com.example.artbook.model;

import android.graphics.Bitmap;

public class Art {
    int id;
    String artCaption;
    String artTitle;
    String artPaintType;
    Bitmap image;


    public Art(int id, String artCaption, String artTitle, String artPaintType, Bitmap image) {
        this.id = id;
        this.artCaption = artCaption;
        this.artTitle = artTitle;
        this.artPaintType = artPaintType;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getArtCaption() {
        return artCaption;
    }
    public String getArtTitle() {
        return artTitle;
    }
    public String getArtPaintType() {
        return artPaintType;
    }
    public Bitmap getImage() {
        return image;
    }
}
