package com.farm.ngo.farm.utility;

import android.app.Application;
import android.content.res.Resources;

public class FarmApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mdetect.init(this);
    }
}
