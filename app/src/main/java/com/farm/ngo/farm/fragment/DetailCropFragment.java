package com.farm.ngo.farm.fragment;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.model.Data;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class DetailCropFragment extends DialogFragment {
    private String TAG = DetailCropFragment.class.getSimpleName();
    PhotoView img;
    CropItem cropItem;
    private Data data;

    private TextView dataTitle, dataBody;
    private ImageView imageView;
    private ImageButton btnClose;


    static public DetailCropFragment newInstance(CropItem cropItem) {
        DetailCropFragment detailCropFragment = new DetailCropFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("cropitem",cropItem);
        detailCropFragment.setArguments(bundle);
        return detailCropFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_data_view, container, false);
        img=(PhotoView) v.findViewById(R.id.image_preview);
        btnClose=(ImageButton)v.findViewById(R.id.btn_close);
        dataTitle = (TextView) v.findViewById(R.id.data_title);
        dataBody = (TextView) v.findViewById(R.id.data_body);
        imageView=(ImageView)v.findViewById(R.id.image_view);
        try {
            data=new UsingSQLiteHelper(getActivity()).getDataDetail(cropItem.getTablename(),cropItem.getIamgeUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataTitle.setText(data.getTitle());
        dataTitle.setVisibility(View.GONE);
        imageView.setImageDrawable(getImage(data.getImage_url()));
        dataBody.setText(data.getBody());
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }
    public Drawable getImage(String name){
        Resources resources=this.getResources();
        int resourceId=resources.getIdentifier(name,"drawable",getActivity().getPackageName());
        if(resourceId==0){
            resourceId=resources.getIdentifier("paddy","drawable",getActivity().getPackageName());
        }

        return resources.getDrawable(resourceId);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cropItem= (CropItem) getArguments().getSerializable("cropitem");
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }



    }

