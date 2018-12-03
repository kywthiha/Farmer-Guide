package com.farm.ngo.farm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;

import java.io.IOException;

public class SplashScreenActivity extends AppCompatActivity {

    UsingSQLiteHelper helper;
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        test = (TextView) findViewById(R.id.test_view);

    }

    public void setDB(View view) {
        test.setText(helper.getData()[0]);
    }
}
