package com.farm.ngo.farm.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.farm.ngo.farm.Model.Admin;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.DeleteAdminDialog;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AdminViewHolder extends RecyclerView.ViewHolder implements AdminBinder{
    private TextView phoneNo;
    private ImageView deleteAdmin;
    private View cityIndicator;

    public AdminViewHolder(View itemView) {
        super(itemView);
        phoneNo=(TextView)itemView.findViewById(R.id.txt_phno);
        deleteAdmin=(ImageView)itemView.findViewById(R.id.img_delete_admin);
        cityIndicator=(View)itemView.findViewById(R.id.id_city_indication);

    }


    @Override
    public void onAdminBind(final Admin admin, int position) {

        if(admin.getTownship().equals("pauk")){
            this.cityIndicator.setBackgroundColor(this.cityIndicator.getContext().getResources().getColor(R.color.pauk_color));
        }
        if(admin.getTownship().equals("chauk")){
            this.cityIndicator.setBackgroundColor(this.cityIndicator.getContext().getResources().getColor(R.color.chauk_color));
        }
        if(admin.getTownship().equals("myaing")){
            this.cityIndicator.setBackgroundColor(this.cityIndicator.getContext().getResources().getColor(R.color.myaing_color));
        }
        if(admin.getTownship().equals("yesagyo")){
            this.cityIndicator.setBackgroundColor(this.cityIndicator.getContext().getResources().getColor(R.color.yso_color));
        }

        this.phoneNo.setText(admin.getId());
        this.deleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAdminDialog adminDialog=new DeleteAdminDialog(v.getContext(), admin, new DeleteAdminDialog.OnDialogResult() {
                    @Override
                    public void finish() {
                        FirebaseDatabase.getInstance().getReference("admins").child(admin.getId()).setValue(null);
                    }
                });
                adminDialog.show();
            }
        });
    }
}
