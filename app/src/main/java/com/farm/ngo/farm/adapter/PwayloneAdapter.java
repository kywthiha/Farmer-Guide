package com.farm.ngo.farm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.farm.ngo.farm.fragment.DialogConatct;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.R;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import me.myatminsoe.mdetect.JobExecutor;
import me.myatminsoe.mdetect.MMTextView;

public  class PwayloneAdapter extends RecyclerView.Adapter<PwayloneAdapter.holder> implements Filterable {
    private LayoutInflater mInflater ;
    Pwalyone pwalyone;
    ArrayList<Pwalyone> ary=new ArrayList<Pwalyone>();
    ArrayList<Pwalyone> filter_arr =new ArrayList<Pwalyone>();
   Activity c;
    private void requestPermission(){
        ActivityCompat.requestPermissions(c,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    public PwayloneAdapter(Activity context, ArrayList<Pwalyone> ary) {
    mInflater = LayoutInflater.from(context);
        this.ary = ary;
        this.filter_arr=ary;
        this.c=context;
    }


    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.paddressview,viewGroup, false);
        return new holder(mItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        final Pwalyone mCurrent = filter_arr.get(i);
       holder.bindToMessage(mCurrent);


    }







    public int getItemCount() {
        return filter_arr.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString=charSequence.toString();
                if(charString.isEmpty()){
                    filter_arr =ary;
                }
                else{
                    ArrayList<Pwalyone> filteredList=new ArrayList<>();
                    for(Pwalyone pwalyone:ary){
                        if( pwalyone.getName().contains(charSequence)){
                            filteredList.add(pwalyone);
                        }
                    }
                    filter_arr =filteredList;

                }
                FilterResults filterResults=new FilterResults();
                filterResults.values= filter_arr;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filter_arr = (ArrayList<Pwalyone>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    class holder extends RecyclerView.ViewHolder{
        public final MMTextView t1;
        private final CardView cardView1;

        public holder(final View itemView){
            super(itemView);
            t1=(MMTextView) itemView.findViewById(R.id.ptitle);
            cardView1=(CardView)itemView.findViewById(R.id.item);
        }
        public void bindToMessage(final Pwalyone pwalyone){
           t1.setMMText(pwalyone.getName(),null);
            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager ft = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                    DialogConatct newFragment;
                        newFragment = DialogConatct.newInstance(pwalyone);
                    newFragment.show(ft, "slideshow");
                }
            });






        }
    }


}
