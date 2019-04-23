package com.farm.ngo.farm.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;

public class PhoneActivity extends AppCompatActivity {
    ImageView cpku, cyso, cmy, cpuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonecall);
        cpku = (ImageView) findViewById(R.id.cpku);
        cyso = (ImageView) findViewById(R.id.cyso);
        cmy = (ImageView) findViewById(R.id.cmy);
        cpuk = (ImageView) findViewById(R.id.cpuk);
        if(!Mdetect.isUnicode()){
            ((TextView)findViewById(R.id.argi_phone_title)).setText(Rabbit.uni2zg(getString(R.string.argi_phone_title)));
            ((TextView)findViewById(R.id.m_argi_off)).setText(Rabbit.uni2zg(getString(R.string.m_argi_off)));
            ((TextView)findViewById(R.id.pa_argi_off)).setText(Rabbit.uni2zg(getString(R.string.pa_argi_off)));
            ((TextView)findViewById(R.id.y_argi_off)).setText(Rabbit.uni2zg(getString(R.string.y_argi_off)));
            ((TextView)findViewById(R.id.pku_argi_off)).setText(Rabbit.uni2zg(getString(R.string.pku_argi_off)));
        }
        cpku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:06221039"));
                if(ActivityCompat.checkSelfPermission(v.getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermission();
                }
                else{
                    startActivity(i);
                }

            }
        });
        cmy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:06240011"));
                if(ActivityCompat.checkSelfPermission(v.getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermission();
                }
                else{
                    startActivity(i);
                }

            }
        });
        cpuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:06245280"));
                if(ActivityCompat.checkSelfPermission(v.getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermission();
                }
                else{
                    startActivity(i);
                }

            }
        });
        cyso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:06230235"));
                if(ActivityCompat.checkSelfPermission(v.getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermission();
                }
                else{
                    startActivity(i);
                }

            }
        });
    }
private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
}

}