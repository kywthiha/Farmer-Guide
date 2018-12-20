package com.farm.ngo.farm.auth.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.farm.ngo.farm.Holder.*;
import com.farm.ngo.farm.MainActivity;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.activity.QuestionAnswerActivity;
import com.farm.ngo.farm.preference_help.Helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.content.Intent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserLoginActivity extends FragmentActivity implements PhoneNoView.ButtonClickListener,VerifyNoView.VerifyButtonClickListener ,RegisterView.ButtonClickeListenere{

    FragmentManager fragmentManager;
    boolean userExists;
    public ProgressDialog progressDialog;
    public static final String username="fjsoijf9oej9ur90wur3489038";
    public static final String userid="0u903u90ruvklknxlkcnvlk";
    public static final String township="fjskfj0u9u90u90u90werjwjkla";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.user_id_checking));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        fragmentManager=getSupportFragmentManager();
        PhoneNoView phoneNoView=new PhoneNoView();
        fragmentManager.beginTransaction().add(R.id.main_layout, phoneNoView).commit();
    }

    @Override
    public void OnButtonClickListener(String phonenumber) {
        VerifyNoView verifyNoView=new VerifyNoView();
        verifyNoView.setmPhoneNumber(phonenumber);
        Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.main_layout,verifyNoView).addToBackStack(null).commit();
    }

    @Override
    public void OnVerifyButtonClickListener(final String phonenumber) {
        progressDialog.show();
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("users");
        database.child(phonenumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userExists=true;
                    progressDialog.dismiss();
                    User user=dataSnapshot.getValue(User.class);
                    savePreferences(user);
                    Intent in=new Intent(getApplicationContext(),QuestionAnswerActivity.class);
                    in.putExtra("user",user);
                    startActivity(in);
                    finish();


                }else {
                    userExists=false;
                        progressDialog.dismiss();
                        RegisterView registerView = new RegisterView();
                        registerView.setPhonenumber(phonenumber);
                        fragmentManager.beginTransaction().replace(R.id.main_layout, registerView).commit();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




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
    private void savePreferences(User user) {
        Helper helper=new Helper(this);
        helper.encryptAndStorePassword(username,user.getName());
       helper. encryptAndStorePassword(userid,user.getId());
       helper. encryptAndStorePassword(township,user.getTownship());

    }

    @Override
    public void RegisterClick(String phonenum,String name, String township) {
        DatabaseReference databaseuser=FirebaseDatabase.getInstance().getReference().child("users").child(phonenum);
        if(!userExists){
            User user=new User(phonenum,name,"","",township);
            databaseuser.setValue(user);
            savePreferences(user);
            Intent in=new Intent(UserLoginActivity.this,MainActivity.class);
            in.putExtra("user",user);
            startActivity(in);
            this.finish();

        }
        else {
            Toast.makeText(this,"user alerady exit",Toast.LENGTH_LONG).show();
        }

    }


}
