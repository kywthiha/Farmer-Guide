package com.farm.ngo.farm.utility;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

public class Mdetect {
    private static Boolean cacheUnicode;
    public static void init(Context context) {
        if (cacheUnicode != null) {
            Log.i("MDetect", "MDetect was already initialized.");
            return;
        }
        TextView textView = new TextView(context, null);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("\u1000");
        textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int length1 = textView.getMeasuredWidth();

        textView.setText("\u1000\u1039\u1000");
        textView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int length2 = textView.getMeasuredWidth();
        cacheUnicode = length1 == length2;
    }
    public static Boolean isUnicode(){
        return cacheUnicode;
    }

}
