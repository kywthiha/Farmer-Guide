package com.farm.ngo.farm.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.adapter.CategoryAdapter;
import com.farm.ngo.farm.data.JsonRead;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactCategoryFragment extends Fragment {
    CategoryAdapter categoryAdapter;
    private String categoryfilename;
    private String josnfilename;

    public void setJosnfilename(String josnfilename) {
        this.josnfilename = josnfilename;
    }

    public void setCategoryfilename(String categoryfilename) {
        this.categoryfilename = categoryfilename;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment_main, container, false);
        RecyclerView rc=(RecyclerView)view.findViewById(R.id.pr);
        JsonRead jsonRead=new JsonRead(getActivity(),categoryfilename);
        ArrayList<String> categorys = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonRead.loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("category");
            for (int i = 0; i < m_jArry.length(); i++) {
                categorys.add(m_jArry.getString(i));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        categoryAdapter=new CategoryAdapter(getActivity(),categorys,josnfilename);
        rc.setAdapter(categoryAdapter);
        LinearLayoutManager k=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(k);


        return view;
    }


}
