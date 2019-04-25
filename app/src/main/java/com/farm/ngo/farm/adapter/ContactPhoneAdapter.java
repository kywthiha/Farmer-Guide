package com.farm.ngo.farm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.farm.ngo.farm.R;


import java.util.ArrayList;

public class ContactPhoneAdapter extends RecyclerView.Adapter<ContactPhoneAdapter.holder> {
    private LayoutInflater mInflater ;
    Activity c;
    ArrayList<String> ary=new ArrayList<String>();
    public ContactPhoneAdapter(Activity context, ArrayList<String> ary) {
        mInflater = LayoutInflater.from(context);
        this.ary = ary;
        this.c=context;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(c,new String[]{Manifest.permission.CALL_PHONE},1);
    }


    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.contact_phone_item,viewGroup, false);
        return new holder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        final String mCurrent = ary.get(i);
        holder.phoneView.setText(mCurrent);
        holder.btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+mCurrent));
                if(ActivityCompat.checkSelfPermission(view.getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
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
        public final TextView phoneView;
        private final ImageView btnPhone;


        final ContactPhoneAdapter mAdapter;
        public holder(final View itemView, ContactPhoneAdapter ad){
            super(itemView);
            phoneView=(TextView) itemView.findViewById(R.id.phone_number);
            btnPhone=(ImageView) itemView.findViewById(R.id.phone_btn);
            this.mAdapter=ad;
            btnPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"hello",Toast.LENGTH_SHORT).show();
                }
            });



        }
    }
}
