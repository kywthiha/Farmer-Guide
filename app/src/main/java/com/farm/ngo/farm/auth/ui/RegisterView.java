package com.farm.ngo.farm.auth.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.farm.ngo.farm.R;


public class RegisterView  extends Fragment {
    private String phonenumber;
    Button mContinue;
    Spinner mTownship;
    EditText mName;

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public interface ButtonClickeListenere{
        void RegisterClick(String phonenum, String name, String township);
    }
    private ButtonClickeListenere mCallback;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ButtonClickeListenere) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.register_view,container,false);
        mContinue=(Button)rootView.findViewById(R.id.btnContinue);
        mTownship=(Spinner)rootView.findViewById(R.id.usertownship);
        mName=(EditText)rootView.findViewById(R.id.username);
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.RegisterClick(phonenumber,mName.getText().toString(),mTownship.getSelectedItem().toString());
            }
        });

        return rootView;
    }
}
