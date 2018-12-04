package com.farm.ngo.farm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.farm.ngo.farm.Model.Post;
import com.farm.ngo.farm.R;

public class postshowdetail extends AppCompatActivity {
    TextView title,info;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.post_showdetail);
        Intent i=getIntent();
        Post u=(Post)i.getSerializableExtra("post");
        title=(TextView)findViewById(R.id.title);
        info=(TextView)findViewById(R.id.info);
        img=(ImageView)findViewById(R.id.img);
        title.setText(u.getTitle());
        info.setText(u.getInfo());
        RequestOptions placeholderRequest = new RequestOptions();
        placeholderRequest.placeholder(R.drawable.loading);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(placeholderRequest).load(u.getUrl()).into(img);




    }
}
