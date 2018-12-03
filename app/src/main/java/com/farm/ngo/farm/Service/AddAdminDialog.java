package com.farm.ngo.farm.Service;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.farm.ngo.farm.R;

import java.util.ArrayList;
import java.util.List;

public class AddAdminDialog extends Dialog implements View.OnClickListener {
    Dialog d;
    private Context context;
    LinearLayout yes,no;
    String ph,name;
    private EditText edt_ph,edt_name;
    OnDialogResult onDialogResult;
    Spinner spinnerTownship;
    List<String> townships=new ArrayList<>();
    ArrayAdapter<String> townadapter;
    public AddAdminDialog(Context context, OnDialogResult result) {
        super(context);
        this.context=context;
        this.onDialogResult=result;
        townships.add("pauk");
        townships.add("myaing");
        townships.add("yesagyo");
        townships.add("chauk");
        townadapter=new ArrayAdapter<>(context,R.layout.spinner_item, townships);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_admin_dialog);
        spinnerTownship = (Spinner) findViewById(R.id.spinner_township);
        spinnerTownship.setAdapter(townadapter);
        yes = (LinearLayout) findViewById(R.id.add_admin_btn);
        no = (LinearLayout) findViewById(R.id.add_admin_cancel);
        edt_ph = (EditText) findViewById(R.id.edt_admin_ph);
        edt_name = (EditText) findViewById(R.id.edt_admin_name);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==no){
           this.cancel();
        }
        if(v==yes){
            ph=edt_ph.getText().toString();
            name=edt_name.getText().toString();
            onDialogResult.finish(ph,name,spinnerTownship.getSelectedItem().toString());
            this.cancel();
        }
    }

    public interface OnDialogResult{
        void finish(String ph, String name, String townshop);
    }

}
