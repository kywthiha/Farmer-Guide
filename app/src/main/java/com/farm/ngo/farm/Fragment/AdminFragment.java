package com.farm.ngo.farm.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.Adapter.AdminAdapter;
import com.farm.ngo.farm.Model.Admin;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.AddAdminDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment {
    //db ref
    DatabaseReference adminReference=FirebaseDatabase.getInstance().getReference("admins");

    //Recyclers
    private RecyclerView recyclerMyaing;
    private RecyclerView recyclerPauk;
    private RecyclerView recyclerChuk;
    private RecyclerView recyclerYesagyo;

    //Adapters
    private AdminAdapter adapterMyaing;
    private AdminAdapter adapterPauk;
    private AdminAdapter adapterChuk;
    private AdminAdapter adapterYesagyo;

    //Lists
   private List<Admin> listMyaing=new ArrayList<>();
   private List<Admin> listPauk=new ArrayList<>();
   private List<Admin> listChuk=new ArrayList<>();
   private List<Admin> listYesagyo=new ArrayList<>();

    //layoumanager
   private LinearLayoutManager managerMyaing;
   private LinearLayoutManager managerPauk;
   private LinearLayoutManager managerChauk;
   private LinearLayoutManager managerYesagyo;

   //Floating add btn
    FloatingActionButton btnAddAdmin;

    //Context
    Context context;
    public AdminFragment(Context context){
        this.context=context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.admin_fragment,null);

        //get layout manager
        managerChauk=new LinearLayoutManager(getActivity());
        managerPauk=new LinearLayoutManager(getActivity());
        managerMyaing=new LinearLayoutManager(getActivity());
        managerYesagyo=new LinearLayoutManager(getActivity());

        //init recyclers
        recyclerChuk=view.findViewById(R.id.recycler_chauk);
        recyclerMyaing=view.findViewById(R.id.recycler_myaing);
        recyclerYesagyo=view.findViewById(R.id.recycler_yesagyo);
        recyclerPauk=view.findViewById(R.id.recycler_pauk);

        //init adapters
        adapterChuk=new AdminAdapter(getActivity(),listChuk);
        adapterPauk=new AdminAdapter(getActivity(),listPauk);
        adapterYesagyo=new AdminAdapter(getActivity(),listYesagyo);
        adapterMyaing=new AdminAdapter(getActivity(),listMyaing);

        //set up manager
        recyclerPauk.setLayoutManager(managerPauk);
        recyclerChuk.setLayoutManager(managerChauk);
        recyclerMyaing.setLayoutManager(managerMyaing);
        recyclerYesagyo.setLayoutManager(managerYesagyo);

        //set up adapter
        recyclerMyaing.setAdapter(adapterMyaing);
        recyclerPauk.setAdapter(adapterPauk);
        recyclerYesagyo.setAdapter(adapterYesagyo);
        recyclerChuk.setAdapter(adapterChuk);

        //btn
        btnAddAdmin=view.findViewById(R.id.btn_add_admin);
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  AddAdminDialog addAdminDialog=new AddAdminDialog(getActivity(), new AddAdminDialog.OnDialogResult() {
                      @Override
                      public void finish(String ph, String name,String township) {
                          adminReference.child("+95"+ph).setValue(new Admin("+95"+ph,name,township));
                      }
                  });
                  addAdminDialog.show();
            }

        });

        //lists add

        adminReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Admin admin=dataSnapshot.getValue(Admin.class);
                if(admin.getTownship().equals("myaing")){
                    listMyaing.add(admin);
                    adapterMyaing.notifyDataSetChanged();
                }
                if(admin.getTownship().equals("pauk")){
                    listPauk.add(admin);
                    adapterPauk.notifyDataSetChanged();
                }
                if (admin.getTownship().equals("yesagyo")){
                    listYesagyo.add(admin);
                    adapterYesagyo.notifyDataSetChanged();
                }
                if(admin.getTownship().equals("chauk")){
                    listChuk.add(admin);
                    adapterChuk.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Admin admin=dataSnapshot.getValue(Admin.class);
                    //////////////////////////
                if(admin.getTownship().equals("myaing")){
                    for(int i=0;i<listMyaing.size();i++){
                        if(listMyaing.get(i).getId().equals(admin.getId())){
                            listMyaing.remove(i);
                            adapterMyaing.notifyDataSetChanged();
                        }
                    }
                }
                ///////////////////////////////
                if(admin.getTownship().equals("pauk")){
                    for(int i=0;i<listPauk.size();i++){
                        if(listPauk.get(i).getId().equals(admin.getId())){
                            listPauk.remove(i);
                            adapterPauk.notifyDataSetChanged();
                        }
                    }
                }
                //////////////////////////////
                if (admin.getTownship().equals("yesagyo")){
                    for(int i=0;i<listYesagyo.size();i++){
                        if(listYesagyo.get(i).getId().equals(admin.getId())){
                            listYesagyo.remove(i);
                            adapterYesagyo.notifyDataSetChanged();
                        }
                    }
                }
                ////////////////////////////////
                if(admin.getTownship().equals("chauk")){
                    for(int i=0;i<listChuk.size();i++){
                        if(listChuk.get(i).getId().equals(admin.getId())){
                            listChuk.remove(i);
                            adapterChuk.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
