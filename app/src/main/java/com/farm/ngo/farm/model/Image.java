package com.farm.ngo.farm.model;

import android.graphics.Bitmap;

public class Image {
    private Bitmap bitmap;
    private User user;

    public Image() {
    }

    public Image(Bitmap bitmap, User user) {
        this.bitmap = bitmap;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
