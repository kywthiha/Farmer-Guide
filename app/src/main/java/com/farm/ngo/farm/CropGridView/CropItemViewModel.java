package com.farm.ngo.farm.CropGridView;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.farm.ngo.farm.Holder.UsingSQLiteHelper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class CropItemViewModel extends AndroidViewModel {
    private LiveData<List<CropItem>> mAllCropItem;
    private String tableName;
    public CropItemViewModel(@NonNull Application application) {
        super(application);
        mAllCropItem =new MutableLiveData<>();

    }

    private List<CropItem> getDBCropItems() {
        List<CropItem> cropItemList=new LinkedList<>();
        try {
            UsingSQLiteHelper helper=new UsingSQLiteHelper(getApplication());
            cropItemList = helper.getCropItemList(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cropItemList;
    }

    public LiveData<List<CropItem>> getmAllCropItem(String tableName) {
        this.tableName=tableName;
        ((MutableLiveData<List<CropItem>>) mAllCropItem).postValue(getDBCropItems());
        return mAllCropItem;
    }


}
