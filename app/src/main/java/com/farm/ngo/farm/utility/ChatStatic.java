package com.farm.ngo.farm.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatStatic {
   static SharedPreferences preferences;
   public static  DatabaseReference getChatRef(Context context){
       preferences=context.getSharedPreferences("adpref",0);
       return FirebaseDatabase.getInstance().getReference("chat").child(preferences.getString("city",""));
   }
}
