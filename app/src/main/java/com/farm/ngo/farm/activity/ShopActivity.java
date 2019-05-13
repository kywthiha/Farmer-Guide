package com.farm.ngo.farm.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.adapter.PwayloneAdapter;
import com.farm.ngo.farm.fragment.ContactCategoryFragment;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {
   private PwayloneAdapter adapter;
    private ArrayList<Pwalyone> pp;
    FragmentManager fg=getSupportFragmentManager();
    FragmentTransaction transaction = fg.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwalyone);
        if(!Mdetect.isUnicode())
            getSupportActionBar().setTitle(Rabbit.uni2zg(getString(R.string.shop_label)));
        ContactCategoryFragment homeFragment = new ContactCategoryFragment();
        homeFragment.setCategoryfilename("shopcategory");
        homeFragment.setJosnfilename("shoplist");
        transaction.add(R.id.view_pager, homeFragment);
        transaction.commit();

    }




}
