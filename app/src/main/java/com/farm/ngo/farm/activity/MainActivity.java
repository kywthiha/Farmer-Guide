package com.farm.ngo.farm.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.farm.ngo.farm.fragment.CropUserFragment;
import com.farm.ngo.farm.fragment.HomeFragment;
import com.farm.ngo.farm.fragment.ProfileFragment;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.NavigationCustomDone;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener ,NavigationCustomDone {

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
        showMenuButtom();
        switch (item.getItemId()){
            case android.R.id.home :

                clearBackStack();
                FragmentTransaction Transaction = getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragmentmain = new HomeFragment(getApplicationContext(), R.id.view_pager);
                Transaction.replace(R.id.view_pager, homeFragmentmain);
               Transaction.disallowAddToBackStack();
                Transaction.commit();
                return true;


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        showMenuButtom();
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        showMenuButtom();
    }
    private void showMenuButtom(){
        if(btnNavView.getVisibility()==View.GONE)
            btnNavView.setVisibility(View.VISIBLE);

    }
    private void hideMenuButtom(){
        if(btnNavView.getVisibility()==View.VISIBLE)
            btnNavView.setVisibility(View.GONE);

    }

    @Override
    public void OnButtonClickListener() {
       hideMenuButtom();
    }
}
