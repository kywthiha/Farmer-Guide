package com.farm.ngo.farm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.Holder.CropViewHolder;
import com.farm.ngo.farm.Model.Crop;
import com.farm.ngo.farm.R;

import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropViewHolder> {

    private Context context;
    private List<Crop> crops;

    public CropAdapter(Context context, List<Crop> crops) {
        this.context = context;
        this.crops = crops;
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_item, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        Crop crop=crops.get(position);
        holder.onBind(crop,position);
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }
}
