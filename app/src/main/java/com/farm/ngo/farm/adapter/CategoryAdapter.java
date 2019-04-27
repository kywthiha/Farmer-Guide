package com.farm.ngo.farm.adapter;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.farm.ngo.farm.R;
import com.farm.ngo.farm.fragment.ContactFragment;
import com.farm.ngo.farm.fragment.DialogConatct;
import com.farm.ngo.farm.model.Pwalyone;

import java.util.ArrayList;

import me.myatminsoe.mdetect.MMTextView;

public  class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.holder> {
    private LayoutInflater mInflater ;
    ArrayList<String> ary=new ArrayList<>();
    private String jsonfilename;

   Activity c;

    public CategoryAdapter(Activity context, ArrayList<String> ary,String jsonfilename) {
    mInflater = LayoutInflater.from(context);
        this.ary = ary;
        this.jsonfilename=jsonfilename;
        this.c=context;
    }


    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.category_item,viewGroup, false);
        return new holder(mItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        final String mCurrent = ary.get(i);
       holder.bindToMessage(mCurrent);


    }

    public int getItemCount() {
        return ary.size();
    }



    class holder extends RecyclerView.ViewHolder{
        public final MMTextView t1;
        private final CardView cardView1;

        public holder(final View itemView){
            super(itemView);
            t1=(MMTextView) itemView.findViewById(R.id.ptitle);
            cardView1=(CardView)itemView.findViewById(R.id.item);
        }
        public void bindToMessage(final String category){
           t1.setMMText(category,null);
            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fg = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                    ContactFragment contactFragment=new ContactFragment();
                   contactFragment.setCategory(category);
                   contactFragment.setJsonfilename(jsonfilename);
                    FragmentTransaction ft=fg.beginTransaction();
                    ft.replace(R.id.view_pager,contactFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });






        }
    }


}
