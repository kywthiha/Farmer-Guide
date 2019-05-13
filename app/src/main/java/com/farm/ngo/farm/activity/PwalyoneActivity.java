package com.farm.ngo.farm.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.farm.ngo.farm.adapter.PwayloneAdapter;
import com.farm.ngo.farm.data.JsonRead;
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import com.farm.ngo.farm.fragment.ContactCategoryFragment;
import com.farm.ngo.farm.fragment.PaddyFragment;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PwalyoneActivity extends AppCompatActivity {
   private PwayloneAdapter adapter;
    private ArrayList<Pwalyone> pp;
    FragmentManager fg=getSupportFragmentManager();
    FragmentTransaction transaction = fg.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwalyone);
        if(!Mdetect.isUnicode())
            getSupportActionBar().setTitle(Rabbit.uni2zg(getString(R.string.pwayyone_label)));
        ContactCategoryFragment homeFragment = new ContactCategoryFragment();
        homeFragment.setCategoryfilename("PwalyoneCategory");
        homeFragment.setJosnfilename("pkucityfarmguide");
        transaction.add(R.id.view_pager, homeFragment);
        transaction.commit();

    }




}
