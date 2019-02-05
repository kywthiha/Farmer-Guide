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
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.R;
import java.io.IOException;
import java.util.ArrayList;

public class PwalyoneActivity extends AppCompatActivity {
   private PwayloneAdapter adapter;
    private ArrayList<Pwalyone> pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwalyone);
        RecyclerView rc=(RecyclerView)findViewById(R.id.pr);
        pp=new ArrayList<>();
        try {
            pp=new UsingSQLiteHelper(this).getPwalyoneList("pwalyone");
        } catch (IOException e) {
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
