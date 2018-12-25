package com.farm.ngo.farm.Service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.R;

public class LoadingDialog extends Dialog {
    private Context mContext;
    private String text;
    public LoadingDialog(@NonNull Context context,String text) {
        super(context);
        this.text=text;
        mContext=context;
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
