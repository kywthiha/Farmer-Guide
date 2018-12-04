package com.farm.ngo.farm;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.farm.ngo.farm.Fragment.AdminFragment;
import com.farm.ngo.farm.Fragment.AdminNewsFragment;
import com.farm.ngo.farm.Fragment.ConversationFragment;
import com.farm.ngo.farm.Fragment.NewsFragment;
import com.farm.ngo.farm.Fragment.PriceFragment;

public class AdminActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    public static NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setTitle("Conversation");
        setTitleColor(Color.WHITE);

        ConversationFragment fragment = new ConversationFragment(getApplicationContext());
        transaction.replace(R.id.admin_pager, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                for (int i = 0; i < navView.getMenu().size(); i++){
                    navView.getMenu().getItem(i).setChecked(false);
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_conversation :
                        setTitle("Conversation");
                        FragmentTransaction ctransaction = getSupportFragmentManager().beginTransaction();
                        ConversationFragment cFragment = new ConversationFragment(getApplicationContext());
                        ctransaction.replace(R.id.admin_pager, cFragment);
                        ctransaction.commit();
                        break;
                    case R.id.nav_post:
                        setTitle("Post");
                        FragmentTransaction ntransaction = getSupportFragmentManager().beginTransaction();
                        AdminNewsFragment nFragment = new AdminNewsFragment();
                        ntransaction.replace(R.id.admin_pager, nFragment);
                        ntransaction.commit();
                        break;
                    case R.id.nav_price :
                        setTitle("Price");
                        FragmentTransaction ptransaction = getSupportFragmentManager().beginTransaction();
                        PriceFragment pFragment = new PriceFragment(getApplicationContext());
                        ptransaction.replace(R.id.admin_pager, pFragment);
                        ptransaction.commit();
                        break;
                    case R.id.nav_log_out :
                        LoginActivity.editor.putInt("afterlogin", 0);
                        LoginActivity.editor.commit();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_account:
                        setTitle("Admin Account Management");
                        FragmentTransaction accTransation = getSupportFragmentManager().beginTransaction();
                        AdminFragment adminFragment = new AdminFragment(getApplicationContext());
                        accTransation.replace(R.id.admin_pager, adminFragment);
                        accTransation.commit();

                    default: break;
                }
                return true;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }
    }
}
