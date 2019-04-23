package com.farm.ngo.farm.data;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonRead {
    private Activity myActivity;
    private String fileName;

    public JsonRead(Activity myActivity, String fileName) {
        this.myActivity = myActivity;
        this.fileName = fileName;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = myActivity.getAssets().open(fileName+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
