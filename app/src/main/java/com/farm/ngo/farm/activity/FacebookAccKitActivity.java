package com.farm.ngo.farm.activity;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class FacebookAccKitActivity extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    Button mContinue;
    EditText mName;
    private String phonenumber;
    private int nextPermissionsRequestCode = 4000;
    private final Map<Integer, OnCompleteListener> permissionsListeners = new HashMap<>();

    private interface OnCompleteListener {
        void onComplete();
    }
    private void getCurrentUser(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                final TextView phoneNumber = (TextView) findViewById(R.id.phone_number);
                final PhoneNumber number = account.getPhoneNumber();
                phonenumber=number.toString();
                phoneNumber.setText(number == null ? null : number.toString());
            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user=UsingSQLiteHelper.getUser(getApplicationContext());
        if(user!=null) {
            Intent in = new Intent(FacebookAccKitActivity.this, QuestionAnswerActivity.class);
            in.putExtra("user", user);
            startActivity(in);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentUser();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_acc_kit);
        mContinue=(Button)findViewById(R.id.btnContinue);
        mName=(EditText)findViewById(R.id.username);
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseuser=FirebaseDatabase.getInstance().getReference().child("users").child(phonenumber);
                    User user=new User(phonenumber,mName.getText().toString(),"","","Pakokku");
                    databaseuser.setValue(user);
                    UsingSQLiteHelper.setUser(user,getApplicationContext());
                Log.i("facebook",user.getId());
                    Intent in=new Intent(FacebookAccKitActivity.this,QuestionAnswerActivity.class);
                    in.putExtra("user",user);
                    startActivity(in);
                    finish();
            }
        });

        if (AccountKit.getCurrentAccessToken() != null && savedInstanceState == null) {
           getCurrentUser();
        }
        else {
            phoneLogin();
        }
    }

    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        final AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder
                = new AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,
                AccountKitActivity.ResponseType.TOKEN);
        final AccountKitConfiguration configuration = configurationBuilder.build();
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configuration);
        OnCompleteListener completeListener = new OnCompleteListener() {
            @Override
            public void onComplete() {
                startActivityForResult(intent, APP_REQUEST_CODE);
            }
        };
        if (configuration.isReceiveSMSEnabled() && !canReadSmsWithoutPermission()) {
            final OnCompleteListener receiveSMSCompleteListener = completeListener;
            completeListener = new OnCompleteListener() {
                @Override
                public void onComplete() {
                    requestPermissions(
                            Manifest.permission.RECEIVE_SMS,
                            R.string.permissions_receive_sms_title,
                            R.string.permissions_receive_sms_message,
                            receiveSMSCompleteListener);
                }
            };
        }
        if (configuration.isReadPhoneStateEnabled() && !isGooglePlayServicesAvailable()) {
            final OnCompleteListener readPhoneStateCompleteListener = completeListener;
            completeListener = new OnCompleteListener() {
                @Override
                public void onComplete() {
                    requestPermissions(
                            Manifest.permission.READ_PHONE_STATE,
                            R.string.permissions_read_phone_state_title,
                            R.string.permissions_read_phone_state_message,
                            readPhoneStateCompleteListener);
                }
            };
        }
        completeListener.onComplete();
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
    private boolean isGooglePlayServicesAvailable() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        return googlePlayServicesAvailable == ConnectionResult.SUCCESS;
    }

    private boolean canReadSmsWithoutPermission() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        if (googlePlayServicesAvailable == ConnectionResult.SUCCESS) {
            return true;
        }


        return false;
    }

    private void requestPermissions(
            final String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final OnCompleteListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }

        checkRequestPermissions(
                permission,
                rationaleTitleResourceId,
                rationaleMessageResourceId,
                listener);
    }

    @TargetApi(23)
    private void checkRequestPermissions(
            final String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final OnCompleteListener listener) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }

        final int requestCode = nextPermissionsRequestCode++;
        permissionsListeners.put(requestCode, listener);

        if (shouldShowRequestPermissionRationale(permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(rationaleTitleResourceId)
                    .setMessage(rationaleMessageResourceId)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            requestPermissions(new String[] { permission }, requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            // ignore and clean up the listener
                            permissionsListeners.remove(requestCode);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            requestPermissions(new String[]{ permission }, requestCode);
        }
    }

    @TargetApi(23)
    @SuppressWarnings("unused")
    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           final @NonNull String permissions[],
                                           final @NonNull int[] grantResults) {
        final OnCompleteListener permissionsListener = permissionsListeners.remove(requestCode);
        if (permissionsListener != null
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsListener.onComplete();
        }
    }







}
