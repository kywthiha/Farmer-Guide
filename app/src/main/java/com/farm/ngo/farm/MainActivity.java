package com.farm.ngo.farm;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.farm.ngo.farm.Fragment.CropUserFragment;
import com.farm.ngo.farm.Fragment.HomeFragment;
import com.farm.ngo.farm.Fragment.NewsFragment;
import com.farm.ngo.farm.Fragment.PriceFragment;
import com.farm.ngo.farm.Fragment.ProfileFragment;
import com.farm.ngo.farm.Fragment.QuestionAnswerFragment;
import com.farm.ngo.farm.Model.News;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    private BottomNavigationView btnNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNavView = (BottomNavigationView) findViewById(R.id.bottomnavigation);
        btnNavView.setOnNavigationItemSelectedListener(this);
        HomeFragment homeFragment = new HomeFragment(getApplicationContext(), R.id.view_pager);
        transaction.replace(R.id.view_pager, homeFragment);
        transaction.commit();
    }
//    private String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("other_crop.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return json;
//    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++){
            fm.popBackStack();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.btn_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_home :
                clearBackStack();
                FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment(getApplicationContext(), R.id.view_pager);
                homeTransaction.replace(R.id.view_pager, homeFragment);
                homeTransaction.disallowAddToBackStack();
                homeTransaction.commit();
                return true;
            case R.id.nav_price :
                clearBackStack();
                FragmentTransaction priceTransaction = getSupportFragmentManager().beginTransaction();
                CropUserFragment cropUserFragment = new CropUserFragment(getApplicationContext());
                priceTransaction.replace(R.id.view_pager, cropUserFragment);
                priceTransaction.disallowAddToBackStack();
                priceTransaction.commit();
                return true;
            case R.id.nav_profile :
                clearBackStack();
                FragmentTransaction profileTransaction = getSupportFragmentManager().beginTransaction();
                ProfileFragment profileFragment = new ProfileFragment(getApplicationContext());
                profileTransaction.replace(R.id.view_pager, profileFragment);
                profileTransaction.disallowAddToBackStack();
                profileTransaction.commit();
                return true;

            default: break;
        }
        return false;
    }
}
