package com.farm.ngo.farm.utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.R;

public class LoadingDialog extends Dialog {
    private Context mContext;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        mContext=context;
    }
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_data);
        ImageView imageView=(ImageView)findViewById(R.id.loading_img);
        TextView textView=(TextView)findViewById(R.id.text_displlay);
        Glide.with(mContext).load(R.drawable.spinner).into(imageView);
        if(text!=null)
            textView.setText(text);
    }
}
