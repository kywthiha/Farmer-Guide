package com.farm.ngo.farm.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.farm.ngo.farm.Model.Post;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.SlideshowDialogFragment;

public class postshowdetail extends AppCompatActivity {
    TextView title,info;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_showdetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("News");
        Intent i=getIntent();
        final Post u=(Post)i.getSerializableExtra("post");
        title=(TextView)findViewById(R.id.title);
        info=(TextView)findViewById(R.id.info);
        img=(ImageView)findViewById(R.id.img);
        title.setText(u.getTitle());
        info.setText(u.getInfo());
        Glide.with(getApplicationContext())
                .load(u.getUrl())
                .thumbnail(Glide.with(getApplicationContext()).load(R.drawable.verify_code_loading))
                .into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreview(u.getUrl());
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPreview(String mFileUri) {
        FragmentManager ft = getSupportFragmentManager();
        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance(mFileUri);
        newFragment.show(ft, "slideshow");
    }
}
