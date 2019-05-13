package com.farm.ngo.farm.viewholder;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.fragment.DetailCropFragment;
import com.farm.ngo.farm.fragment.DialogConatct;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.model.Pwalyone;

import me.myatminsoe.mdetect.MMTextView;

public class ContactItemHolder extends RecyclerView.ViewHolder {
    MMTextView t1;
    CardView cardView1;
    View mView;
    public ContactItemHolder(@NonNull View itemView) {
        super(itemView);
        mView=itemView;
        t1=(MMTextView) itemView.findViewById(R.id.ptitle);
        cardView1=(CardView)itemView.findViewById(R.id.item);
    }
    public void bindToMessage(final Pwalyone cropItem){
        final Context mContext=mView.getContext();
        t1.setMMText(cropItem.getName(),null);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager ft = ((AppCompatActivity)mContext).getSupportFragmentManager();
                DialogConatct newFragment;
                newFragment = DialogConatct.newInstance(cropItem);
                newFragment.show(ft, "slideshow");
            }
        });


    }


}
