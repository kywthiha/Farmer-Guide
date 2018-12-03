package com.farm.ngo.farm.Service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farm.ngo.farm.Model.Crop;
import com.farm.ngo.farm.R;


public class DeleteCropDialog extends Dialog implements View.OnClickListener {
    Dialog d;
    private Context context;
    LinearLayout yes,no;
    String crop;
    Crop cc;

    TextView txtCropName;
    DeleteCropDialog.OnDialogResult onDialogResult;
    public DeleteCropDialog(Context context, Crop crop, DeleteCropDialog.OnDialogResult result) {
        super(context);
        this.context=context;
        this.onDialogResult=result;
        this.cc=crop;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_crop_dialog);
        yes = (LinearLayout) findViewById(R.id.edt_delete_crop_delete);
        no = (LinearLayout) findViewById(R.id.edt_delete_crop_cancel);

        txtCropName=(TextView)findViewById(R.id.txt_ask_question);
        txtCropName.setText("Are you sure you want to delete "+ cc.getC_name()+" from stock list?");
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
