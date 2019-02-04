package com.farm.ngo.farm.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.farm.ngo.farm.R;

public class AddCropDialog extends Dialog implements View.OnClickListener {
    Dialog d;
    private Context context;
    LinearLayout yes,no;
    String crop;
    private EditText edCrop;
    OnDialogResult onDialogResult;
    public AddCropDialog(Context context, OnDialogResult result) {
        super(context);
        this.context=context;
        this.onDialogResult=result;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_crop_dialog);
        yes = (LinearLayout) findViewById(R.id.add_crop_add);
        no = (LinearLayout) findViewById(R.id.add_crop_cancel);
        edCrop=(EditText)findViewById(R.id.edt_crop);
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
