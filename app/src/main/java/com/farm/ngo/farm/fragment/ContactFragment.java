package com.farm.ngo.farm.fragment;


import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.farm.ngo.farm.adapter.ContactAdapter;
import com.farm.ngo.farm.adapter.PwayloneAdapter;
import com.farm.ngo.farm.data.JsonRead;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;
import com.farm.ngo.farm.viewmodel.ContactViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
   private ContactAdapter adapter;
    private ArrayList<Pwalyone> pp;
    private String category;
    private String jsonfilename;
    private ContactViewModel contactViewModel;

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
        adapter=new ContactAdapter(getActivity());
        rc.setAdapter(adapter);
        LinearLayoutManager k=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(k);
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getmAllCropItem(jsonfilename,category).observe(this, new Observer<List<Pwalyone>>() {

            @Override
            public void onChanged(@Nullable List<Pwalyone> cropItemList) {
                adapter.setWords(cropItemList);
            }
        });
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
                    if(!Mdetect.isUnicode())
                        s=Rabbit.zg2uni(s);
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        }
    }
}
