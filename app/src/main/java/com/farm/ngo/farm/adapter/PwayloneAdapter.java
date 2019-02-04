package com.farm.ngo.farm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.R;

import java.util.ArrayList;

public  class PwayloneAdapter extends RecyclerView.Adapter<PwayloneAdapter.holder>{
    private LayoutInflater mInflater ;
    ArrayList<Pwalyone>ary=new ArrayList<Pwalyone>();
   Activity c;
    private void requestPermission(){
        ActivityCompat.requestPermissions(c,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    public PwayloneAdapter(Activity context, ArrayList<Pwalyone> ary) {
    mInflater = LayoutInflater.from(context);
        this.ary = ary;
        this.c=context;
    }


    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.paddressview,viewGroup, false);
        return new holder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        final Pwalyone mCurrent = ary.get(i);
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
        return ary.size();
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
