package com.farm.ngo.farm.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.ngo.farm.Service.LoadingDialog;
import com.farm.ngo.farm.auth.ui.*;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    public static final String username="fjsoijf9oej9ur90wur3489038";
    public static final String userid="0u903u90ruvklknxlkcnvlk";
    public static final String township="fjskfj0u9u90u90u90werjwjkla";

    private Button logOut;
    private User user;
    RelativeLayout edit,show;
    TextView showname,showwork,showgender,showaddress;
    EditText ename,ework,eaddress;
    RadioButton boy,girl;
    TextView phoneView;
    TextView btn;
    ImageView profilepicture,eimg;
    User trmpuser;

    public ProfileFragment(Context context) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final LoadingDialog pd = new LoadingDialog(getActivity(),"ေက်းဇူးျပဳ၍ေခတၱခဏေစာင့္ေပးပါ....");
        pd.show();
        final WebView wb=(WebView)view.findViewById(R.id.webview);
        wb.getSettings().setAppCacheMaxSize(54*1024*1024);
        wb.getSettings().setAppCachePath(getActivity().getApplicationContext().getCacheDir().getAbsolutePath());
        wb.getSettings().setAllowFileAccess(true);
        wb.getSettings().setAppCacheEnabled(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        if(!isNetWorkAvailable()){
            wb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        wb.loadUrl("https://drive.google.com/open?id=1yp4ZGUd7G33iQvnzJICBrR5Ow0HWb5Va");
        wb.setWebViewClient(new HelloWebViewClient());
        wb.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(view.getContext(), description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = wb.getUrl();

            }



        });

        return view;

    }
    private boolean isNetWorkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
    public class HelloWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            return false;
        }}


    }


