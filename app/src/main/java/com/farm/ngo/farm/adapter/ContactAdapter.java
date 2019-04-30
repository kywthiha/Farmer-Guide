package com.farm.ngo.farm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.viewholder.ContactItemHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactItemHolder> implements Filterable {
    private final LayoutInflater mInflater;
    private List<Pwalyone> mCropItem = Collections.emptyList(); // Cached copy of words
    private List<Pwalyone> filter_arr=Collections.emptyList();

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
        Pwalyone current = filter_arr.get(position);
        holder.bindToMessage(current);
    }

    public void setWords(List<Pwalyone> cropItems) {
        mCropItem = cropItems;
        filter_arr=cropItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filter_arr.size();
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString=charSequence.toString();
                if(charString.isEmpty()){
                    filter_arr =mCropItem;
                }
                else{
                    ArrayList<Pwalyone> filteredList=new ArrayList<>();
                    for(Pwalyone pwalyone:mCropItem){
                        if( pwalyone.getName().contains(charSequence)){
                            filteredList.add(pwalyone);
                        }
                    }
                    filter_arr =filteredList;

                }
                FilterResults filterResults=new FilterResults();
                filterResults.values= filter_arr;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filter_arr = (ArrayList<Pwalyone>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

}
