package com.farm.ngo.farm.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.farm.ngo.farm.Adapter.CropAdapter;
import com.farm.ngo.farm.AdminActivity;
import com.farm.ngo.farm.Holder.UsingSQLiteHelper;
import com.farm.ngo.farm.Model.Crop;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.AddCropDialog;
import com.farm.ngo.farm.Utility.UiUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends Fragment {


    private static DatabaseReference reference=FirebaseDatabase.getInstance().getReference("stock");
    BottomSheetBehavior bottomSheetBehavior;
    Spinner dropdown,cropCount;
    EditText cropPrice;

    //recycler
    CropAdapter cropAdapter;
    List<Crop> crops=new ArrayList<>();
    RecyclerView cropRecycler;
    List<String> items=new ArrayList<>();
    List<String> counts=new ArrayList<>();
    ArrayAdapter<String> adapter, countAdapter;
    UsingSQLiteHelper helper;

    private LinearLayoutCompat addCrop;
    private LinearLayout addLayout, cancelLayout;

    private Context mContext;

    AddCropDialog dialog;

    public PriceFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price, container, false);


        LinearLayout llBottomSheet = (LinearLayout) view.findViewById(R.id.bottom_sheet);
        cropPrice= view.findViewById(R.id.edt_crop_price);
        cropRecycler= view.findViewById(R.id.recycler_stock);
        addCrop = (LinearLayoutCompat) view.findViewById(R.id.addCrop);
        addLayout = (LinearLayout) view.findViewById(R.id.onUpload);
        cancelLayout = (LinearLayout) view.findViewById(R.id.onCancel);
        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCrop(v);
            }
        });
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCrop(v);
            }
        });
        cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel(v);
            }
        });
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
            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {

            }
        });

        cropAdapter=new CropAdapter(mContext,crops);
        cropRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        cropRecycler.setAdapter(cropAdapter);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);


        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.setHideable(true);



        dropdown = view.findViewById(R.id.spinner_crops);
        cropCount=(Spinner) view.findViewById(R.id.spinner_crop_count);


        helper=new UsingSQLiteHelper(mContext);
        if(!helper.isThereCrops()) {
            Log.i("crop","crp");
            helper.addCrops();
        }
        //add db crops to array
        for(String s:helper.getCrops()){
            items.add(s);
        }
        items.add("add more");
        dropdown.setSelection(0);

        //count
        counts.add("1 tin");
        counts.add("1 cup");
        counts.add("1 pyi");

        countAdapter=new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_dropdown_item, counts);
        cropCount.setAdapter(countAdapter);

        adapter= new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String more=items.get(position).toString();
                if(more.equals("add more")){
                    dropdown.setSelection(0);
                    dialog =new AddCropDialog(getActivity(), new AddCropDialog.OnDialogResult() {
                        @Override
                        public void finish(String result) {
                            if(result.equals("")){
                                UiUtil.showToast(mContext,"failed to create crop item");
                            }
                            if(!result.equals("")){
                                helper.addCropList(result);
                                items.add(items.size()-2,result);
                                adapter.notifyDataSetChanged();
                                int i=0;
                                int pos=0;
                                for(String item:items){
                                    if(item.equals(result)){
                                        pos=i;
                                        break;
                                    }
                                    i++;
                                }
                                dropdown.setSelection(pos);
                            }else {
                                dropdown.setSelection(0);
                            }
                        }
                    });

                    dialog.show();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void createNewCrop(View view) {
        showBottomSheet();
    }

    private void showBottomSheet() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        }
    }

    public void onCancel(View view) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    public void onAddCrop(View view) {
        String cName,cPrice,cCount;

        cName=dropdown.getSelectedItem().toString();
        cCount=cropCount.getSelectedItem().toString();
        cPrice=cropPrice.getText().toString();
        if(!(cPrice.equals(""))) {
            String key = reference.push().getKey();

            reference.child(key).setValue(new Crop(key, cName, cPrice,cCount));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            UiUtil.showToast(mContext,"crop created");
        }else {
            UiUtil.showToast(mContext,"failed to create a crop");
        }
    }


}
