package com.farm.ngo.farm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.viewholder.CropUserViewHolder;
import com.farm.ngo.farm.model.Crop;
import com.farm.ngo.farm.R;

import java.util.List;

public class CropUserAdapter extends RecyclerView.Adapter<CropUserViewHolder> {

    private Context context;
    private List<Crop> crops;

    public CropUserAdapter(Context context, List<Crop> crops) {
        this.context = context;
        this.crops = crops;
    }

    @NonNull
    @Override
    public CropUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_item, parent, false);
        return new CropUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropUserViewHolder holder, int position) {
        Crop crop=crops.get(position);
        holder.onBind(crop,position);
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }
}