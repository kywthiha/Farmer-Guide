package com.farm.ngo.farm.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.adapter.CropUserAdapter;
import com.farm.ngo.farm.farmstatic.RefStatic;
import com.farm.ngo.farm.model.Crop;
import com.farm.ngo.farm.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CropUserFragment extends Fragment {


    private Context mContext;

    RecyclerView recyclerView;
    CropUserAdapter cropAdapter;
    private DatabaseReference reference = RefStatic.stockRef;
    List<Crop> crops=new ArrayList<>();

    public CropUserFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_user, container, false);


        recyclerView = view.findViewById(R.id.recycler_stock);
        cropAdapter=new CropUserAdapter(mContext,crops);

        reference.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@android.support.annotation.NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Crop crop=(Crop)dataSnapshot.getValue(Crop.class);
                crops.add(crop);
                cropAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@android.support.annotation.NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Crop crop=dataSnapshot.getValue(Crop.class);
                for(int i=0;i<crops.size();i++){
                    if(crops.get(i).getC_Id().equals(crop.getC_Id())){
                        crops.get(i).setC_price(crop.getC_price());
                        cropAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                Crop crop=dataSnapshot.getValue(Crop.class);
                for(int i=0;i<crops.size();i++){
                    if(crops.get(i).getC_Id().equals(crop.getC_Id())){
                        crops.remove(i);
                        cropAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(@android.support.annotation.NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(cropAdapter);
        return view;
    }

}
