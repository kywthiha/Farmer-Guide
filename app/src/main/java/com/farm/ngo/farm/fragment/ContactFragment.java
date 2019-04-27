package com.farm.ngo.farm.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.adapter.PwayloneAdapter;
import com.farm.ngo.farm.data.JsonRead;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactFragment extends Fragment {
   private PwayloneAdapter adapter;
    private ArrayList<Pwalyone> pp;
    private String category;
    private String jsonfilename;

    public void setJsonfilename(String jsonfilename) {
        this.jsonfilename = jsonfilename;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_fragmentview, container, false);
        JsonRead jsonRead=new JsonRead(getActivity(),jsonfilename);
        RecyclerView rc=(RecyclerView)view.findViewById(R.id.pr);
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
                if(pwalyone.getCategory().contains(category))
                pwalyones.add(pwalyone);

            }
            pp=pwalyones;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter=new PwayloneAdapter(getActivity(),pp);
        rc.setAdapter(adapter);
        LinearLayoutManager k=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(k);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.menu_search);
        SearchView searchView=(SearchView)item.getActionView();
        SearchManager searchManager=(SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        if(item!=null){
            searchView=(SearchView)item.getActionView();
        }
        if(searchView!=null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
        }
    }
}
