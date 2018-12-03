package com.farm.ngo.farm;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.farm.ngo.farm.Model.Data;

public class DataViewActivity extends AppCompatActivity {

    private Data data;

    private TextView dataTitle, dataBody;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = (Data) getIntent().getSerializableExtra("object");

        dataTitle = (TextView) findViewById(R.id.data_title);

        dataBody = (TextView) findViewById(R.id.data_body);
        imageView=(ImageView)findViewById(R.id.image_view);

        dataTitle.setText(data.getTitle());
        imageView.setImageDrawable(getImage(data.getImage_url()));


        dataBody.setText(data.getBody());
    }
    public Drawable getImage(String name){
        Resources resources=this.getResources();
        int resourceId=resources.getIdentifier(name,"drawable",this.getPackageName());
        if(resourceId==0){
            resourceId=resources.getIdentifier("paddy","drawable",this.getPackageName());
        }

        return resources.getDrawable(resourceId);
    }
}
