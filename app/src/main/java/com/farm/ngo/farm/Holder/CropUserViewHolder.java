package com.farm.ngo.farm.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farm.ngo.farm.Model.Crop;
import com.farm.ngo.farm.R;

public class CropUserViewHolder extends RecyclerView.ViewHolder implements  CropBinder{
    TextView crop_name,crop_price,cropCount;
    private Context context;
    LinearLayout btn_edit,btn_delete;
    ImageView menu;
    RelativeLayout linearEdit,relativeMenu;

    public CropUserViewHolder(View itemView) {
        super(itemView);

        context=itemView.getContext();
        crop_name=(TextView)itemView.findViewById(R.id.txt_crop_name);
        crop_price=(TextView)itemView.findViewById(R.id.txt_crop_price);
        cropCount=(TextView)itemView.findViewById(R.id.txt_crop_count);

        btn_edit=(LinearLayout) itemView.findViewById(R.id.btn_crop_edit);
        btn_delete=(LinearLayout) itemView.findViewById(R.id.btn_crop_delete);
        menu=(ImageView)itemView.findViewById(R.id.img_menu);
        linearEdit=(RelativeLayout) itemView.findViewById(R.id.linear_edit);
        relativeMenu=(RelativeLayout)itemView.findViewById(R.id.relative_menu);


    }

    @Override
    public void onBind(Crop c, int position) {

        this.crop_name.setText(c.getC_name());
        this.crop_price.setText(c.getC_price()+" Ks");
        this.cropCount.setText(c.getCount());
        this.menu.setVisibility(View.GONE);
        this.linearEdit.setVisibility(View.GONE);
        this.relativeMenu.setVisibility(View.GONE);

    }



}