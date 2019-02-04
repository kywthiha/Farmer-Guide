package com.farm.ngo.farm.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.farm.ngo.farm.model.Chat;
import com.farm.ngo.farm.model.User;
import com.farm.ngo.farm.activity.ChatActivity;


public class ChatItemClickListener implements View.OnClickListener {
    private Chat c;
    private Context context;

    public ChatItemClickListener(Chat c,Context context) {
        this.c = c;
        this.context=context;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences=v.getContext().getSharedPreferences("adpref",0);
        User u= new User(c.getId(),c.getUsername(),"","",preferences.getString("city",""));
        Intent intent=new Intent(context,ChatActivity.class);
        intent.putExtra("user",u);
        Log.i("chat item clicked ee",c.getLastmessage());
        v.getContext().startActivity(intent);

    }
}
