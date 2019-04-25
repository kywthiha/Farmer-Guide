package com.farm.ngo.farm.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.activity.PwalyoneActivity;
import com.farm.ngo.farm.adapter.ContactPhoneAdapter;
import com.farm.ngo.farm.adapter.PwayloneAdapter;
import com.farm.ngo.farm.model.Pwalyone;

import java.util.ArrayList;

import me.myatminsoe.mdetect.MMTextView;

public class DialogConatct extends DialogFragment {
    private String TAG = DialogConatct.class.getSimpleName();
    Pwalyone pwalyone;
    RecyclerView phonelistview;
    private ContactPhoneAdapter contactPhoneAdapter;


    static public DialogConatct newInstance(Pwalyone pwalyone) {
        DialogConatct f = new DialogConatct();
        Bundle bundle=new Bundle();
        bundle.putSerializable("pwa",pwalyone);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_detail, container, false);
        phonelistview=(RecyclerView)v.findViewById(R.id.phone_list);
        MMTextView name=(MMTextView)v.findViewById(R.id.name);
        MMTextView category=(MMTextView)v.findViewById(R.id.category);
        MMTextView addresss=(MMTextView)v.findViewById(R.id.txtaddress);
        ImageView btnDirection=(ImageView)v.findViewById(R.id.btnDircetion);
        name.setMMText(pwalyone.getName(),null);
        addresss.setMMText(pwalyone.getAddress(),null);
        category.setMMText(pwalyone.getCategory(),null);
        contactPhoneAdapter=new ContactPhoneAdapter(getActivity(),pwalyone.getPhoneno());
        phonelistview.setAdapter(contactPhoneAdapter);
        LinearLayoutManager k=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        phonelistview.setLayoutManager(k);
        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+pwalyone.getLocation().latitude+","+pwalyone.getLocation().longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pwalyone= (Pwalyone) getArguments().getSerializable("pwa");
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    }

}
