package com.farm.ngo.farm.utility;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

public class UiUtil {
    public static Toast toast;
    public static void showSnackBar(Window window, String uploaded_new_crop) {
        Snackbar snackbar=Snackbar.make(window.findViewById(android.R.id.content),uploaded_new_crop, Snackbar.LENGTH_LONG);
        View view=snackbar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);
        snackbar.show();
    }

    public static void showToast(Context context, String text){
       toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
       toast.setGravity(Gravity.CENTER,0,0);
       toast.show();
    }
}
