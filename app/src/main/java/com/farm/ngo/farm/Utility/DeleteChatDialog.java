package com.farm.ngo.farm.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farm.ngo.farm.Model.Chat;
import com.farm.ngo.farm.R;

public class DeleteChatDialog extends Dialog implements View.OnClickListener {
    Dialog d;
    private Context context;
    LinearLayout yes,no;
    String crop;
    Chat cc;

    TextView txtCropName;
    OnDialogResult onDialogResult;
    public DeleteChatDialog(Context context, Chat chat, OnDialogResult result) {
        super(context);
        this.context=context;
        this.onDialogResult=result;
        this.cc=chat;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_crop_dialog);
        yes = (LinearLayout) findViewById(R.id.edt_delete_crop_delete);
        no = (LinearLayout) findViewById(R.id.edt_delete_crop_cancel);

        txtCropName=(TextView)findViewById(R.id.txt_ask_question);
        txtCropName.setText("Are you sure you want to delete "+ cc.getUsername()+" from chat list?");
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==no){
            this.cancel();
        }
        if(v==yes){
            onDialogResult.onYesClicked();
            this.cancel();
        }
    }

    public interface OnDialogResult{
        void onYesClicked();
    }

}
