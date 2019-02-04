package com.farm.ngo.farm.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farm.ngo.farm.model.Crop;
import com.farm.ngo.farm.R;

public class EditCropDialog extends Dialog implements View.OnClickListener {

    Dialog d;
    private Context context;
    LinearLayout yes,no;
    String crop;
    private EditText edCrop;
    Crop cc;

    TextView txtCropName;
    AddCropDialog.OnDialogResult onDialogResult;
    public EditCropDialog(Context context, Crop crop, AddCropDialog.OnDialogResult result) {
        super(context);
        this.context=context;
        this.onDialogResult=result;
        this.cc=crop;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_crop_dialog);
        yes = (LinearLayout) findViewById(R.id.edt_edit_crop_save);
        no = (LinearLayout) findViewById(R.id.edt_edit_crop_cancel);
        edCrop=(EditText)findViewById(R.id.edt_crop_price);
        txtCropName=(TextView) findViewById(R.id.txt_edt_crop_name);
        txtCropName.setText(cc.getC_name());
        edCrop.setText(cc.getC_price());
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==no){
            this.cancel();
        }
        if(v==yes){
            crop=edCrop.getText().toString();
            onDialogResult.finish(crop);
            this.cancel();
        }
    }

    public interface OnDialogResult{
        void finish(String result);
    }

}
