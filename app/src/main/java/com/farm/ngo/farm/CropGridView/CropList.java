package com.farm.ngo.farm.CropGridView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;
import com.farm.ngo.farm.R;
public class CropList extends RecyclerView.Adapter<CropItemHolder> {



    private final LayoutInflater mInflater;
    private List<CropItem> mCropItem = Collections.emptyList(); // Cached copy of words

    public CropList(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CropItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.crop_item, parent, false);
        return new CropItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CropItemHolder holder, int position) {
        CropItem current = mCropItem.get(position);
        holder.bindToMessage(current);
    }

    public void setWords(List<CropItem> cropItems) {
        mCropItem = cropItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCropItem.size();
    }
}


