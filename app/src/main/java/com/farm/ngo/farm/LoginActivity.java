package com.farm.ngo.farm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.farm.ngo.farm.Fragment.PhoneNoView;
import com.farm.ngo.farm.Fragment.RegisterView;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.Model.Admin;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.Fragment.VerifyNoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements PhoneNoView.ButtonClickListener, VerifyNoView.VerifyButtonClickListener, RegisterView.ButtonClickeListenere {

    FragmentManager fragmentManager;
    User user = null;

    public static SharedPreferences preferences;
    public static SharedPreferences adminPreferences;
    public static SharedPreferences.Editor adminEditor;

    public static SharedPreferences.Editor editor;

    private UsingSQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //admin shareprefs
        adminPreferences=getApplicationContext().getSharedPreferences("adpref", 0);
        adminEditor=adminPreferences.edit();

        //For Shared preference to store data
        preferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = preferences.edit();
        if (preferences.getInt("afterlogin", 0) == 2) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else if (preferences.getInt("afterlogin", 0) == 1) {
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);
            finish();
        }
        //To set up database
        helper = new UsingSQLiteHelper(getApplicationContext());
//        try {
//          helper.createDataBase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        fragmentManager = getSupportFragmentManager();
        PhoneNoView phoneNoView = new PhoneNoView();
        fragmentManager.beginTransaction().add(R.id.pager_fragment, phoneNoView).commit();

        //ending setu up database


//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signOut();
//        helper = new UsingSQLiteHelper(getApplicationContext());
//
//        try {
//            helper.createDataBase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        userID = (EditText) findViewById(R.id.user_id);
//        userName = (EditText) findViewById(R.id.user_name);
//        userTownship = (EditText) findViewById(R.id.user_township);
//        userLogin = (Button) findViewById(R.id.user_login);
//        firebaseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user_ID = dataSnapshot.child("admin").getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//                final User user = new User(userID.getText().toString(), userName.getText().toString(), " ", " ", "Pakokku");
//                if(userID.getText().toString().equals(user_ID)){
//                    editor.putInt("afterlogin", 2);
//                    editor.commit();
//                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                    helper.sendUserData(user);
//                    editor.putInt("afterlogin", 1);
//                    editor.commit();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

    }

    @Override
    public void OnButtonClickListener(final String phonenumber) {
        final DatabaseReference adminDatabase = FirebaseDatabase.getInstance().getReference().child("admins").child(phonenumber);
        adminDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals(phonenumber)) {
                    Admin admin = dataSnapshot.getValue(Admin.class);
                    if(admin != null){
                        editor.putInt("afterlogin", 1);
                        editor.commit();
                        adminEditor.putString("id",admin.getId());
                        adminEditor.putString("city",admin.getTownship());
                        adminEditor.commit();
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //For user login condition
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users").child(phonenumber);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals(phonenumber)) {
                    User usertemp = dataSnapshot.getValue(User.class);
                    user = usertemp;
                    if (user != null) {
                        // If user exit
//                        helper.sendUserData(user);
                        editor.putInt("afterlogin", 2);
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                } else {
                    //User does not exit
                    VerifyNoView verifyNoView = new VerifyNoView();
                    verifyNoView.setmPhoneNumber(phonenumber);
                    fragmentManager.beginTransaction().replace(R.id.pager_fragment, verifyNoView).addToBackStack(null).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void OnVerifyButtonClickListener(final String phonenumber) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("User ID Checking");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (user == null) {
                    progressDialog.dismiss();
                    RegisterView registerView = new RegisterView();
                    registerView.setPhonenumber(phonenumber);
                    fragmentManager.beginTransaction().replace(R.id.pager_fragment, registerView).commit();
                }
            }
        };
        handler.postDelayed(runnable, 3000);

    }

    @Override
    public void RegisterClick(String phonenum, String name, String township) {
        DatabaseReference databaseuser = FirebaseDatabase.getInstance().getReference().child("users").child(phonenum);

        if (user == null) {
            User user = new User(phonenum, name, "", "", township);
            databaseuser.setValue(user);
            //helper.sendUserData(user);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "user alerady exit", Toast.LENGTH_LONG).show();
        }
    }
}
