package com.farm.ngo.farm.activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwalyone);
        JsonRead jsonRead=new JsonRead(this,"pkucityfarmguide");
        if(!Mdetect.isUnicode())
        getSupportActionBar().setTitle(Rabbit.uni2zg(getString(R.string.pwayyone_label)));
        RecyclerView rc=(RecyclerView)findViewById(R.id.pr);
        pp=new ArrayList<>();
            try {
                JSONObject obj = new JSONObject(jsonRead.loadJSONFromAsset());
                JSONArray m_jArry = obj.getJSONArray("data");
                ArrayList<Pwalyone> pwalyones = new ArrayList<>();
                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    Pwalyone pwalyone=new Pwalyone();
                    pwalyone.setName(jo_inside.getString("name"));
                    pwalyone.setAddress(jo_inside.getString("address"));
                    pwalyone.setCategory(jo_inside.getString("category"));
                    JSONObject loc=jo_inside.getJSONObject("location");
                    double [] ary={loc.getDouble("lat"),loc.getDouble("lon")};
                    pwalyone.setLocation(ary);
                    JSONArray phone_no=jo_inside.getJSONArray("phone_no");
                    ArrayList<String> ph_nos=new ArrayList<>();
                    for (int ii = 0; ii < phone_no.length(); ii++) {
                        ph_nos.add(phone_no.getString(ii));
                    }
                    pwalyone.setPhoneno(ph_nos);
                    pwalyones.add(pwalyone);

                }
                pp=pwalyones;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        adapter=new PwayloneAdapter(this,pp);
        rc.setAdapter(adapter);
        LinearLayoutManager k=new LinearLayoutManager(PwalyoneActivity.this,LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(k);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.menu_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
}
