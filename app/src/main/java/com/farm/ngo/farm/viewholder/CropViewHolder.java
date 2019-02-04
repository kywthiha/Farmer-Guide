package com.farm.ngo.farm.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farm.ngo.farm.farmstatic.RefStatic;
import com.farm.ngo.farm.model.Crop;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.service.AddCropDialog;
import com.farm.ngo.farm.service.DeleteCropDialog;
import com.farm.ngo.farm.service.EditCropDialog;
import com.farm.ngo.farm.utility.UiUtil;

public class CropViewHolder extends RecyclerView.ViewHolder implements  CropBinder{
    TextView crop_name,crop_price,cropCount;
    private Context context;
    LinearLayout btn_edit,btn_delete;
    ImageView menu;
    RelativeLayout linearEdit;
    public CropViewHolder(View itemView) {
        super(itemView);

        context=itemView.getContext();
        crop_name=(TextView)itemView.findViewById(R.id.txt_crop_name);
        crop_price=(TextView)itemView.findViewById(R.id.txt_crop_price);
        cropCount=(TextView)itemView.findViewById(R.id.txt_crop_count);

        btn_edit=(LinearLayout) itemView.findViewById(R.id.btn_crop_edit);
        btn_delete=(LinearLayout) itemView.findViewById(R.id.btn_crop_delete);
        menu=(ImageView)itemView.findViewById(R.id.img_menu);
        linearEdit=(RelativeLayout) itemView.findViewById(R.id.linear_edit);


    }

    @Override
    public void onBind(Crop c, int position) {

        this.crop_name.setText(c.getC_name());
        this.crop_price.setText(c.getC_price()+" Ks");
        this.cropCount.setText(c.getCount());

        CropListener listener=new CropListener(c);
        btn_edit.setOnClickListener(listener);
        btn_delete.setOnClickListener(listener);
        menu.setOnClickListener(listener);


    }

    public class CropListener implements View.OnClickListener{
        Crop c;
        public CropListener(Crop c) {
            this.c = c;
        }

        @Override
        public void onClick(final View v) {
            if(v==btn_edit)
            {
                final EditCropDialog dialog=new EditCropDialog(v.getContext(),c, new AddCropDialog.OnDialogResult() {
                    @Override
                    public void finish(String result) {
                        Log.i("edit","edit reached");
                         RefStatic.stockRef.child(c.getC_Id()).child("c_price").setValue(result);
                        if (v.getContext() instanceof Activity) {
                            Window window = ((Activity) v.getContext()).getWindow();
                            UiUtil.showToast(context,"stock edited");
                        }

                    }
                });
                dialog.show();
            }
            if(v==btn_delete){
                final DeleteCropDialog deleteCropDialogk=new DeleteCropDialog(v.getContext(), c, new DeleteCropDialog.OnDialogResult() {
                    @Override
                    public void finish() {
                        RefStatic.stockRef.child(c.getC_Id()).setValue(null);
                        if (v.getContext() instanceof Activity) {
                            Window window = ((Activity) v.getContext()).getWindow();
                             UiUtil.showToast(context,"stock item deleted");
                        }
                    }
                });
                deleteCropDialogk.show();
            }

            if(v==menu){
                if(linearEdit.getVisibility()==View.VISIBLE){
                    linearEdit.setVisibility(View.GONE);
                }
                else {
                    linearEdit.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}
