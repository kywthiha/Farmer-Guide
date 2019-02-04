package com.farm.ngo.farm.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public  class weatherfivedayAdapter extends RecyclerView.Adapter<weatherfivedayAdapter.holder>{
    private LayoutInflater mInflater ;

    ArrayList<String> aryy=new ArrayList<>();

    Context c;
    public weatherfivedayAdapter(Context context,ArrayList<String> aryy) {
        this.c=context;
        mInflater = LayoutInflater.from(context);
        this.aryy = aryy;
    }


    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.weather,viewGroup, false);
        return new holder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, i+1);
        Date tomorrow = calendar.getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        String mCurrent = aryy.get(i);
        //

        String in[]=mCurrent.split(",");
        String imagename="http://openweathermap.org/img/w/"+ in[2] +".png";
       holder.t2.setText(dayFormat.format(tomorrow));
        Glide.with(c).load(imagename).into(holder.t1);
        holder.t3.setText(in[1]);
       // holder.t4.setText(in[2]);
       //++ holder.t4.setText(in[3]);

    }







    public int getItemCount() {
        return aryy.size();
    }
    class holder extends RecyclerView.ViewHolder{
        public final ImageView t1;
        public final TextView t2;
        public final TextView t3;
       // public final TextView t4;

        final weatherfivedayAdapter mAdapter;
        public holder(View itemView, weatherfivedayAdapter ad){
            super(itemView);
            t1=(ImageView) itemView.findViewById(R.id.weather_icon);
           t2=(TextView)itemView.findViewById(R.id.dayname);
            t3=(TextView)itemView.findViewById(R.id.current_temperature_field);
           // t4=(TextView)itemView.findViewById(R.id.updated_field);
            this.mAdapter=ad;
        }
    }

}
