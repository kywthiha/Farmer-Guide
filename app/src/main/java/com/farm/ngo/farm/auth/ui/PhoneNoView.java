package com.farm.ngo.farm.auth.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.farm.ngo.farm.R;
import com.google.firebase.auth.FirebaseAuth;


public class PhoneNoView extends Fragment {
    public EditText fieldPhoneNumber;
    FirebaseAuth mAuth;
    public interface ButtonClickListener{
        void OnButtonClickListener(String phonenumber);
    }
    public ButtonClickListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.phone_no_view,container,false);
        fieldPhoneNumber=(EditText)rootview.findViewById(R.id.fieldPhoneNumber);
        Button button=(Button)rootview.findViewById(R.id.btnNext);
        mAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePhoneNumber()) {
                    return;
                }
                mCallback.OnButtonClickListener(fieldPhoneNumber.getText().toString());

            }
        });
        return rootview;
    }
    private void signOut() {
        mAuth.signOut();
    }
    private boolean validatePhoneNumber() {
        String phoneNumber = fieldPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            fieldPhoneNumber.setError("Please enter phone number.");
            return false;
        }
        else if(!TextUtils.isDigitsOnly(phoneNumber)){
            fieldPhoneNumber.setError("Invalid phone number.");
            return false;
        }
        else if(!phoneNumber.startsWith("09")){
            fieldPhoneNumber.setError("Please phone number start 09");
            return false;
        }

        return true;
    }

}
