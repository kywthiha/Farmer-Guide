package com.farm.ngo.farm.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.farm.ngo.farm.fragment.DataFragment;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.Rabbit;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private String [] tabArray;
    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        String [] array = context.getResources().getStringArray(R.array.tab_array);
        if(!Mdetect.isUnicode()){
            for(int i=0,l=array.length;i<l;i++){
                array[i]=Rabbit.uni2zg(array[i]);
            }
        }
        tabArray=array;
    }

    @Override
    public Fragment getItem(int position) {
        DataFragment fragment = null;
        switch (position){
            case 0 :
                fragment = new DataFragment();
                fragment.setmContext(mContext);
                fragment.setTableName("grain_tech");
                break;
            case 1 :
                fragment = new DataFragment();
                fragment.setmContext(mContext);
                fragment.setTableName("grain_disease");
                break;
            case 2 :
                fragment = new DataFragment();
                fragment.setmContext(mContext);
                fragment.setTableName("grain_virus");
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }


}
