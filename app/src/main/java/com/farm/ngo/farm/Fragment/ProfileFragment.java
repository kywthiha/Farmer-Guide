package com.farm.ngo.farm.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.LoginActivity;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.UserEdit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button logOut;
    private User user;

    private TextView txtPhone,txtName,btnEdit,txtWork,txtGender;

    public ProfileFragment(Context context) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init views
        txtName=view.findViewById(R.id.txt_name);
        txtPhone=view.findViewById(R.id.txt_phno);
        txtWork=view.findViewById(R.id.txt_wor);
        txtGender=view.findViewById(R.id.txt_gender);

        btnEdit=view.findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UserEdit.class);
                startActivity(intent);
            }
        });


        UsingSQLiteHelper helper = new UsingSQLiteHelper(getActivity());
        user = helper.getUser();

        //bind data
        txtPhone.setText(user.getId());
        txtName.setText(user.getName());
        if(user.getWork()!=null){
            txtWork.setText(user.getWork());
        }
        if(user.getGender()!=null){
            txtGender.setText(user.getGender());
        }

        logOut = (Button) view.findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.editor.putInt("afterlogin", 0);
                LoginActivity.editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

}
