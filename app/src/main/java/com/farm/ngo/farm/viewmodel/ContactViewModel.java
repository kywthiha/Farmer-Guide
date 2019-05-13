package com.farm.ngo.farm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.farm.ngo.farm.data.JsonRead;
import com.farm.ngo.farm.data.UsingSQLiteHelper;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.model.Pwalyone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ContactViewModel extends AndroidViewModel {
    private LiveData<List<Pwalyone>> mAllCropItem;
    private String tableName,category;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        mAllCropItem =new MutableLiveData<>();

    }

    private List<Pwalyone> getDBCropItems() {
        List<Pwalyone> cropItemList=new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(new JsonRead(getApplication(),tableName).loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("data");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Pwalyone pwalyone=new Pwalyone();
                pwalyone.setName(jo_inside.getString("name"));
                pwalyone.setAddress(jo_inside.getString("address"));
                pwalyone.setCategory(jo_inside.getString("category"));
                JSONObject loc=jo_inside.getJSONObject("location");
                double [] ary={loc.getDouble("lat"),loc.getDouble("lon")};
                pwalyone.setLocation(ary);
                JSONArray phone_no=jo_inside.getJSONArray("phone_no");
                ArrayList<String> ph_nos=new ArrayList<>();
                for (int ii = 0; ii < phone_no.length(); ii++) {
                    ph_nos.add(phone_no.getString(ii));
                }
                pwalyone.setPhoneno(ph_nos);
                if(pwalyone.getCategory().contains(category))
                    cropItemList.add(pwalyone);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cropItemList;
    }

    public LiveData<List<Pwalyone>> getmAllCropItem(String tableName,String category) {
        this.tableName=tableName;
        this.category=category;
        ((MutableLiveData<List<Pwalyone>>) mAllCropItem).postValue(getDBCropItems());
        return mAllCropItem;
    }


}
