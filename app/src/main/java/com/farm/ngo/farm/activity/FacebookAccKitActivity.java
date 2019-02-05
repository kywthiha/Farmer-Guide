package com.farm.ngo.farm.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.AccountKitLoginResult;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import com.farm.ngo.farm.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacebookAccKitActivity extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    private String phonenumber;
    Button mContinue;
    Spinner mTownship;
    EditText mName;

    private void getCurrentUser(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                phonenumber=account.getPhoneNumber().toString();
                Toast.makeText(getApplicationContext(),phonenumber,Toast.LENGTH_SHORT).show();
                Log.i("facebook 1",phonenumber);
                //registerUser(phonenumber);


            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        startChart();
    }
    private void startChart(){
        User user= UsingSQLiteHelper.getUser(this);
        if(user!=null){
            Intent in=new Intent(getApplicationContext(),QuestionAnswerActivity.class);
            in.putExtra("user",user);
            startActivity(in);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_acc_kit);
        mContinue=(Button)findViewById(R.id.btnContinue);
        mTownship=(Spinner)findViewById(R.id.usertownship);
        mName=(EditText)findViewById(R.id.username);
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseuser=FirebaseDatabase.getInstance().getReference().child("users").child(phonenumber);
                    User user=new User(phonenumber,mName.getText().toString(),"","","Pakokku");
                    databaseuser.setValue(user);
                    UsingSQLiteHelper.setUser(user,getApplicationContext());
                Log.i("facebook",user.getId());
//                    Intent in=new Intent(FacebookAccKitActivity.this,QuestionAnswerActivity.class);
//                    in.putExtra("user",user);
//                    startActivity(in);
//                    finish();
            }
        });

        if (AccountKit.getCurrentAccessToken() != null && savedInstanceState == null) {
           startChart();
        }
        else {
            phoneLogin();
        }
    }

    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }




    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                finish();
//                showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                finish();
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    getCurrentUser();

                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
//                goToMyLoggedInActivity();
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
    public void registerUser(final String phonenumber) {
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("users");
        database.child(phonenumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User user=dataSnapshot.getValue(User.class);
                    UsingSQLiteHelper.setUser(user,getApplicationContext());
                    Intent in=new Intent(getApplicationContext(),QuestionAnswerActivity.class);
                    in.putExtra("user",user);
                    startActivity(in);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
