package com.farm.ngo.farm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import com.farm.ngo.farm.model.User;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import android.content.Intent;



public class UserLoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


    }

    @Override
    protected void onStart() {
        super.onStart();
        User user= UsingSQLiteHelper.getUser(this);
        if(user!=null){
            Intent in=new Intent(getApplicationContext(),QuestionAnswerActivity.class);
            in.putExtra("user",user);
            startActivity(in);
            this.finish();
        }
    }



}
