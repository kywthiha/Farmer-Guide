package com.farm.ngo.farm;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.farm.ngo.farm.Model.Post;
import com.farm.ngo.farm.R;

public class PostShowAdmin extends AppCompatActivity {
    TextView title,info;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_showdetail_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
