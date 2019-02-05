package com.farm.ngo.farm.viewholder;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.fragment.DetailCropFragment;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.R;

public class CropItemHolder extends RecyclerView.ViewHolder {
    ImageView cropImage;
    View mView;
    TextView cropTitle;
    public CropItemHolder(@NonNull View itemView) {
        super(itemView);
        mView=itemView;
        cropImage=(ImageView)itemView.findViewById(R.id.crop_image);
        cropTitle=(TextView)itemView.findViewById(R.id.crop_title);
    }
    public void bindToMessage(final CropItem cropItem){
        final Context mContext=mView.getContext();
        Glide.with(mContext).load(getImage(cropItem.getIamgeUrl()))
                .into(cropImage);
        cropTitle.setText(cropItem.getTitle());
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                DetailCropFragment newFragment = DetailCropFragment.newInstance(cropItem);
                newFragment.show(fragmentManager, "slideshow");

            }
        });


    }
    public Drawable getImage(String name){
        final Context mContext=mView.getContext();
        Resources resources=mContext.getResources();
        int resourceId=resources.getIdentifier(name,"drawable",mContext.getPackageName());
        if(resourceId==0){
            resourceId=resources.getIdentifier("f","drawable",mContext.getPackageName());
        }
        return resources.getDrawable(resourceId);
    }

}
