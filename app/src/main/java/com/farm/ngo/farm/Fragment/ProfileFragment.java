package com.farm.ngo.farm.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farm.ngo.farm.FarmStatic.RefStatic;
import com.farm.ngo.farm.MainActivity;
import com.farm.ngo.farm.auth.ui.*;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.LoginActivity;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.preference_help.Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.farm.ngo.farm.UserEdit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
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

    public ProfileFragment(Context context) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init views
        btn=(TextView) view.findViewById(R.id.btn);
        edit=(RelativeLayout)view.findViewById(R.id.editlayout);
        show=(RelativeLayout)view.findViewById(R.id.showlayout);
        showname=(TextView)view.findViewById(R.id.showname);
        showwork=(TextView)view.findViewById(R.id.showwork);
        showgender=(TextView)view.findViewById(R.id.showgender);
        showaddress=(TextView)view.findViewById(R.id.showaddress);
        ename=(EditText)view.findViewById(R.id.ename);
        ework=(EditText)view.findViewById(R.id.ework);
        eaddress=(EditText)view.findViewById(R.id.eaddress);
        boy=(RadioButton)view.findViewById(R.id.boy);
        girl=(RadioButton)view.findViewById(R.id.girl);
        profilepicture=(ImageView)view.findViewById(R.id.profilepicture);
        eimg=(ImageView)view.findViewById(R.id.eimg);
        phoneView=(TextView)view.findViewById(R.id.phone);
     final   User u=UsingSQLiteHelper.getUser(getActivity());
        final DatabaseReference databaseuser=FirebaseDatabase.getInstance().getReference().child("users").child(u.getId());
        final String name = u.getName();
        phoneView.setText(u.getId());
        user=Helper.getUserProfile(this.getActivity());
        showname.setText(name);
        if(user.getWork()!=null) {
            showwork.setText(user.getType());
        }
        if(user.getGender()!=null)
        showgender.setText(user.getGender());

        if(user.getAddress()!=null )
        showaddress.setText(user.getAddress());



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getVisibility()==View.GONE){
                    edit.setVisibility(View.VISIBLE);
                    show.setVisibility(View.GONE);
                    ename.setText(String.valueOf(showname.getText()));
                    ename.setSelection(showname.getText().length());
                    ework.setText(String.valueOf(showwork.getText()));
                    eaddress.setText(String.valueOf(showaddress.getText()));

                    if(String.valueOf(showgender.getText()).equals("မ")){
                        girl.setChecked(true);
                        profilepicture.setImageResource(R.drawable.profilegirl);
                    }
                    else {
                        boy.setChecked(true);
                        profilepicture.setImageResource(R.drawable.profileboy);
                    }
                    btn.setText("Save");
                    eimg.setImageResource(R.drawable.ic_save_black_24dp);
                }
                else{

                    show.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                    showname.setText(String.valueOf(ename.getText()));
                    showwork.setText(String.valueOf(ework.getText()));
                    showaddress.setText(String.valueOf(eaddress.getText()));
                    if(boy.isChecked()==true){
                        showgender.setText("က်ား");
                        profilepicture.setImageResource(R.drawable.profileboy);
                    }
                    else{
                        showgender.setText("မ");
                        profilepicture.setImageResource(R.drawable.profilegirl);
                    }
                    btn.setText("Edit");
                    eimg.setImageResource(R.drawable.edit_crop);
                    User userpro=null;
                    userpro=new User(u.getId(),showname.getText().toString(),showaddress.getText().toString(),"",showwork.getText().toString(),showgender.getText().toString(),u.getTownship());
                    databaseuser.setValue(userpro);
                    RefStatic.chatRef.child(u.getTownship()).child(u.getId()).child("username").setValue(showname.getText().toString());
                    Helper helper=new Helper(getActivity());
                    Helper.saveUserProfile(getActivity(),userpro);
                    helper.encryptAndStorePassword("fjsoijf9oej9ur90wur3489038",showname.getText().toString());

                }

            }
        });






        logOut = (Button) view.findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(userid, getActivity().MODE_PRIVATE).edit();
                editor.putString("token","");
                editor.commit();
                startActivity(new Intent(getActivity(),UserLoginActivity.class));
                getActivity().finish();

            }
        });
        return view;
    }

}
