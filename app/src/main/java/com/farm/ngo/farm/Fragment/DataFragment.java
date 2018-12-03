package com.farm.ngo.farm.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.farm.ngo.farm.Adapter.CustomGridviewAdapter;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.Model.Data;
import com.farm.ngo.farm.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {


    private Context mContext;

    private GridView dataListGridView;

    private UsingSQLiteHelper helper;

    private ArrayList<Data> dataList;

    private String tableName;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        dataListGridView = (GridView) view.findViewById(R.id.data_list_grid_view);


        helper = new UsingSQLiteHelper(this.mContext);


        try {
            dataList = helper.getDataList(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomGridviewAdapter adapter = new CustomGridviewAdapter(mContext, dataList);

        dataListGridView.setAdapter(adapter);

        return view;
    }


}
