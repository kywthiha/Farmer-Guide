package com.farm.ngo.farm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.R;

import java.util.ArrayList;

public  class PwayloneAdapter extends RecyclerView.Adapter<PwayloneAdapter.holder> implements Filterable {
    private LayoutInflater mInflater ;
    ArrayList<Pwalyone> ary=new ArrayList<Pwalyone>();
    ArrayList<Pwalyone> filter_arr =new ArrayList<Pwalyone>();
   Activity c;
    private void requestPermission(){
        ActivityCompat.requestPermissions(c,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    public PwayloneAdapter(Activity context, ArrayList<Pwalyone> ary) {
    mInflater = LayoutInflater.from(context);
        this.ary = ary;
        this.filter_arr=ary;
        this.c=context;
    }


    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.paddressview,viewGroup, false);
        return new holder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        final Pwalyone mCurrent = filter_arr.get(i);
        holder.t1.setText(mCurrent.getName());
        holder.t2.setText(mCurrent.getAddress());
        holder.category.setText(mCurrent.getCategory());
        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+mCurrent.getPhoneno()));
                if(ActivityCompat.checkSelfPermission(v.getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermission();
                }
                else{
                   c.startActivity(i);
                }

            }
        });


    }







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
                    filter_arr =ary;
                }
                else{
                    ArrayList<Pwalyone> filteredList=new ArrayList<>();
                    for(Pwalyone pwalyone:ary){
                        if(pwalyone.getCategory().contains(charString) || pwalyone.getName().contains(charSequence)){
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

    class holder extends RecyclerView.ViewHolder{
        public final TextView t1;
        public final TextView t2;
         public final TextView category;
         public final ImageView t3;


        final PwayloneAdapter mAdapter;
        public holder(View itemView,PwayloneAdapter ad){
            super(itemView);
            category=(TextView)itemView.findViewById(R.id.category);
            t1=(TextView)itemView.findViewById(R.id.ptitle);
            t2=(TextView)itemView.findViewById(R.id.padd);
            t3=(ImageView) itemView.findViewById(R.id.ph);


               this.mAdapter=ad;
        }
    }


}
