package com.farm.ngo.farm.service;

import android.util.Log;

import com.farm.ngo.farm.model.Chat;
import com.farm.ngo.farm.model.Message;
import com.farm.ngo.farm.model.MessageType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class ChatHelper {

    public static DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("chat");
    public static    boolean userExists=false;
    private static String adminId=null;

    public static DatabaseReference getChatRef(String township){
        return chatRef.child(township);
    }

    public static void sendChat(String adminid, final String userid, Message mg, String username, boolean admin){
        boolean isPhoto=false;
        if(mg.getMessageType() == (MessageType.image)){
            isPhoto=true;
        }
        if(adminId==null){
            adminId=adminid;
            chatRef=chatRef.child(adminId);
        }

        if(admin){
            Log.i("trace","user is admin");
            chatRef.child(userid).child("id").setValue(mg.getUser_id());
            chatRef.child(userid).child("lastmessage").setValue(mg.getText());
            chatRef.child(userid).child("date").setValue(ServerValue.TIMESTAMP);
            chatRef.child(userid).child("seen").setValue(mg.getSeen());
            chatRef.child(userid).child("admin").setValue(true);
            chatRef.child(userid).child("photo").setValue(isPhoto);

        }else {

            Log.i("trace","user id is"+userid);
            chatRef.child(userid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        userExists=true;
                    }else {
                        userExists=false;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if (!userExists){
                Log.i("trace","id is null");
                chatRef.child(userid).setValue(new Chat(userid,username,"",mg.getText(),mg.getSeen(),admin,isPhoto,ServerValue.TIMESTAMP));
            }else {
                Log.i("trace","id is not null");
                chatRef.child(userid).child("admin").setValue(false);
                chatRef.child(userid).child("photo").setValue(isPhoto);
                chatRef.child(userid).child("id").setValue(mg.getUser_id());
                chatRef.child(userid).child("lastmessage").setValue(mg.getText());
                chatRef.child(userid).child("date").setValue(ServerValue.TIMESTAMP);
                chatRef.child(userid).child("seen").setValue(mg.getSeen());
            }
        }




    }




}
