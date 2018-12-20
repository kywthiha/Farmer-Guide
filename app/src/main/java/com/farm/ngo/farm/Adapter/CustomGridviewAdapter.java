package com.farm.ngo.farm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.farm.ngo.farm.DataViewActivity;
import com.farm.ngo.farm.Model.Data;
import com.farm.ngo.farm.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomGridviewAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Data> dataList;

    public CustomGridviewAdapter(Context context, ArrayList<Data> data) {

        this.mContext = context;
        this.dataList = data;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.data_list_item,parent,false);

        ImageView tech_image = (ImageView) view.findViewById(R.id.data_image);
        TextView data_title = (TextView) view.findViewById(R.id.data_title);
        CardView cardView=(CardView)view.findViewById(R.id.cardview) ;
        cardView.setBackgroundColor(Color.WHITE);
        Resources resources = mContext.getResources();
        data_title.setText(dataList.get(position).getTitle());
        tech_image.setImageResource(getImage(dataList.get(position).getImage_url()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DataViewActivity.class);
                intent.putExtra("object", dataList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent );
            }
        });
        return view;
    }
    public int getImage(String name){
        Resources resources=mContext.getResources();
        int resourceId=resources.getIdentifier(name,"drawable",mContext.getPackageName());
        if(resourceId==0){
            resourceId=resources.getIdentifier("f","drawable",mContext.getPackageName());
        }
        return resourceId;
    }
}
