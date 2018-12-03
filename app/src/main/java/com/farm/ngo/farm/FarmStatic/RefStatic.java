package com.farm.ngo.farm.FarmStatic;

import android.app.Application;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RefStatic extends Application {
    //all references for app
    public final static DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("chat");
    public final static DatabaseReference userREf=FirebaseDatabase.getInstance().getReference("user");
    public final static DatabaseReference adminRef=FirebaseDatabase.getInstance().getReference("admin");
    public final static DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("post");
    public final static DatabaseReference messageRef=FirebaseDatabase.getInstance().getReference("messages");
    public final static DatabaseReference stockRef=FirebaseDatabase.getInstance().getReference("stock");
}
