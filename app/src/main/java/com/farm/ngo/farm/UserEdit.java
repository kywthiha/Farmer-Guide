package com.farm.ngo.farm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.Model.Chat;
import com.farm.ngo.farm.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserEdit extends AppCompatActivity {
    EditText name,work,gender;
    TextView saveBtn,txtPhno;
    private User user;

    //db
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_fragment);

        name=findViewById(R.id.edt_name);
        work=findViewById(R.id.edt_work);
        gender=findViewById(R.id.edt_gender);

        txtPhno=findViewById(R.id.txt_phno);

        //get user
        final UsingSQLiteHelper helper = new UsingSQLiteHelper(getApplicationContext());
        user = helper.getUser();
        database= FirebaseDatabase.getInstance().getReference().child("users").child(user.getId());


        //set up edit texts
        name.setText(user.getName());
        work.setText(user.getWork());
        gender.setText(user.getGender());
        txtPhno.setText(user.getId());

        saveBtn=findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.setValue(new User(txtPhno.getText().toString(),name.getText().toString(),null,work.getText().toString(),gender.getText().toString(),null,user.getTownship()));

            }
        });
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u=dataSnapshot.getValue(User.class);
              //  helper.sendUserData(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
