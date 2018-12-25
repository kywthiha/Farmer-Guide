package com.farm.ngo.farm.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.farm.ngo.farm.CropGridView.CropItem;
import com.farm.ngo.farm.CropGridView.CropItemViewModel;
import com.farm.ngo.farm.CropGridView.CropList;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.R;
import java.util.List;

public class DataFragment extends Fragment {


    private Context mContext;

    private UsingSQLiteHelper helper;

    private List<CropItem> dataList;

    private String tableName;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    private CropItemViewModel cropItemViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.data_list_grid_view);
        final CropList adapter = new CropList(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        cropItemViewModel = ViewModelProviders.of(this).get(CropItemViewModel.class);
        cropItemViewModel.getmAllCropItem(tableName).observe(this, new Observer<List<CropItem>>() {

            @Override
            public void onChanged(@Nullable List<CropItem> cropItemList) {
                adapter.setWords(cropItemList);
            }
        });





        return view;
    }


}
