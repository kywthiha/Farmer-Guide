package com.farm.ngo.farm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.viewholder.ContactItemHolder;

import java.util.Collections;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactItemHolder> {
    private final LayoutInflater mInflater;
    private List<Pwalyone> mCropItem = Collections.emptyList(); // Cached copy of words

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ContactItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.paddressview, parent, false);
        return new ContactItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactItemHolder holder, int position) {
        Pwalyone current = mCropItem.get(position);
        holder.bindToMessage(current);
    }

    public void setWords(List<Pwalyone> cropItems) {
        mCropItem = cropItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCropItem.size();
    }

}
