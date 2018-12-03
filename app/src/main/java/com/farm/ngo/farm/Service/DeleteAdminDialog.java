package com.farm.ngo.farm.Service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farm.ngo.farm.Model.Admin;
import com.farm.ngo.farm.R;

public class DeleteAdminDialog extends Dialog implements View.OnClickListener {
    private Context context;
    LinearLayout yes,no;
    Admin admin;

    TextView txtCropName;
    OnDialogResult onDialogResult;
    public DeleteAdminDialog(Context context, Admin admin, OnDialogResult result) {
        super(context);
        this.context=context;
        this.onDialogResult=result;
        this.admin=admin;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_crop_dialog);
        yes = (LinearLayout) findViewById(R.id.edt_delete_crop_delete);
        no = (LinearLayout) findViewById(R.id.edt_delete_crop_cancel);

        txtCropName=(TextView)findViewById(R.id.txt_ask_question);
        txtCropName.setText("Are you sure you want to delete "+ admin.getId()+" from admin list?");
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==no){
            this.cancel();
        }
        if(v==yes){
            onDialogResult.finish();
            this.cancel();
        }
    }

    public interface OnDialogResult{
        void finish();
    }

}
