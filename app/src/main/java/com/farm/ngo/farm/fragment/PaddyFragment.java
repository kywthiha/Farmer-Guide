package com.farm.ngo.farm.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.adapter.ViewPagerAdapter;
import com.farm.ngo.farm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaddyFragment extends Fragment {

    private Context mContext;
    private TabLayout paddyTab;
    private ViewPager paddyPager;

    public PaddyFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paddy, container, false);

        //Declaration variable
        paddyTab = (TabLayout) view.findViewById(R.id.paddyTab);
        paddyPager = (ViewPager) view.findViewById(R.id.paddyPager);

        //Set Pager Adapter to View Pager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), mContext);
        paddyPager.setAdapter(adapter);

        //Set connection between tab and viewpager
        paddyTab.setupWithViewPager(paddyPager);
        paddyTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                paddyPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

}
