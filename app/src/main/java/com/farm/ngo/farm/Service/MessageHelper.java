package com.farm.ngo.farm.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageHelper {
    public static DatabaseReference messageRef=FirebaseDatabase.getInstance().getReference("messages");
}
