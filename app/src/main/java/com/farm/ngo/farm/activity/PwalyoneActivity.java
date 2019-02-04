package com.farm.ngo.farm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.farm.ngo.farm.adapter.PwayloneAdapter;
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.R;
import java.io.IOException;
import java.util.ArrayList;

public class PwalyoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwalyone);
        RecyclerView rc=(RecyclerView)findViewById(R.id.pr);
        ArrayList<Pwalyone> pp=new ArrayList<>();
        try {
            pp=new UsingSQLiteHelper(this).getPwalyoneList("pwalyone");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PwayloneAdapter adapter=new PwayloneAdapter(this,pp);
        rc.setAdapter(adapter);
        LinearLayoutManager k=new LinearLayoutManager(PwalyoneActivity.this,LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(k);

    }
}
