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
import com.farm.ngo.farm.model.Weather;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WeatherListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final LayoutInflater mInflater;
    private List<Weather> mWeathers=Collections.EMPTY_LIST;
    private final int TITLE_ITEM=0;
    private final int MEMBER_ITEM=1;
    private final int CURRENT_WEATHER=3;

    public WeatherListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==CURRENT_WEATHER){
            View titleView=mInflater.inflate(R.layout.current_weather,parent,false);
            CurrentWeatherViewHolder currentWeatherViewHolder = new CurrentWeatherViewHolder(titleView);
            return currentWeatherViewHolder;
        }
        if(viewType==TITLE_ITEM) {
            View titleView=mInflater.inflate(R.layout.three_hours_weather_title,parent,false);
            WeatherTitleViewHolder weatherTitleViewHolder = new WeatherTitleViewHolder(titleView);
            return weatherTitleViewHolder;
        }
        View itemView=mInflater.inflate(R.layout.three_hours_weather_item,parent,false);
            WeatherItemViewHolder weatherItemViewHolder=new WeatherItemViewHolder(itemView);
            return weatherItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Weather weather=mWeathers.get(position);
        switch (holder.getItemViewType()){
            case TITLE_ITEM:
                final WeatherTitleViewHolder sendViewHolder=(WeatherTitleViewHolder) holder;
                sendViewHolder.bindToData(weather);
                break;
            case MEMBER_ITEM:
                final WeatherItemViewHolder receiveViewHolder=(WeatherItemViewHolder) holder;
                receiveViewHolder.bindToData(weather);
                break;
            case CURRENT_WEATHER:
                final CurrentWeatherViewHolder currentWeatherViewHolder=(CurrentWeatherViewHolder) holder;
                currentWeatherViewHolder.bindToData(weather);
                break;

        }

    }

    public void setmWeathers(List<Weather> weathers){
        mWeathers = weathers;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        Weather weather=mWeathers.get(position);
        if(weather.isCurrent())
            return CURRENT_WEATHER;
        if(weather.isTitle()){
            return TITLE_ITEM;
        }
        return MEMBER_ITEM;
    }


    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    class WeatherItemViewHolder extends RecyclerView.ViewHolder{
        private final ImageView weather_icon;
        private final TextView weather_time_txt;
        private final TextView weather_condition_txt;
        private final TextView heat_amount_degree_celcius;
        private final TextView humidity_txt;
        private final TextView wind_speed;
        private final View view;

        public WeatherItemViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            weather_icon =(ImageView) itemView.findViewById(R.id.weather_icon);
            weather_time_txt =(TextView) itemView.findViewById(R.id.weather_time_txt);
            weather_condition_txt = (TextView)itemView.findViewById(R.id.weather_condition_txt);
            humidity_txt=(TextView) itemView.findViewById(R.id.humidity_txt);
            wind_speed =(TextView) itemView.findViewById(R.id.wind_speed);
            heat_amount_degree_celcius = (TextView)itemView.findViewById(R.id.heat_amount_degree_celcius);
        }
        public void bindToData(Weather weather){
            Glide.with(view.getContext())
                    .load("http://openweathermap.org/img/w/"+weather.getWeather_icon()+".png")
                    .into(weather_icon);
            SimpleDateFormat sdf=new SimpleDateFormat("h:mm a");
            weather_time_txt.setText(sdf.format(new Date(weather.getDt()* 1000)));
            weather_condition_txt.setText(weather.getWeather_description());
            heat_amount_degree_celcius.setText(String.format("%.0f", (weather.getMain_temp())) + "°C");
            humidity_txt.setText("humidity - "+Math.round(weather.getMain_humidity())+"%");
            wind_speed.setText("wind - "+new DecimalFormat("#.0").format((weather.getWind_speed()*2.23693629))+" mph");


        }
    }
    class WeatherTitleViewHolder extends RecyclerView.ViewHolder{
        private final TextView title_View;
        private final ImageView weather_icon;
        private final TextView weather_time_txt;
        private final TextView weather_condition_txt;
        private final TextView heat_amount_degree_celcius;
        private final TextView humidity_txt;
        private final TextView wind_speed;
        private final View view;

        public WeatherTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            title_View =(TextView) itemView.findViewById(R.id.weather_title_txt);
            weather_icon =(ImageView) itemView.findViewById(R.id.weather_icon);
            weather_time_txt =(TextView) itemView.findViewById(R.id.weather_time_txt);
            weather_condition_txt = (TextView)itemView.findViewById(R.id.weather_condition_txt);
            humidity_txt=(TextView) itemView.findViewById(R.id.humidity_txt);
            wind_speed =(TextView) itemView.findViewById(R.id.wind_speed);
            heat_amount_degree_celcius = (TextView)itemView.findViewById(R.id.heat_amount_degree_celcius);
        }
        public void bindToData(Weather weather){
            SimpleDateFormat sdf=new SimpleDateFormat("EEE, MMM d, yyyy");
            String title_date=sdf.format(new Date(weather.getDt()*1000));
            sdf=new SimpleDateFormat("d");
            Integer curr_date=Integer.parseInt(sdf.format(new Date(System.currentTimeMillis())));
            sdf=new SimpleDateFormat("d");
            Integer get_date=Integer.parseInt(sdf.format(new Date(weather.getDt()*1000)));
            if(curr_date==get_date)
                title_date+="  Today";
            else if(curr_date==get_date-1)
                title_date+=" Tommorow";
            title_View.setText(title_date);
            Glide.with(view.getContext())
                    .load("http://openweathermap.org/img/w/"+weather.getWeather_icon()+".png")
                    .into(weather_icon);
            sdf=new SimpleDateFormat("h:mm a");
            weather_time_txt.setText(sdf.format(new Date(weather.getDt()* 1000)));
            weather_condition_txt.setText(weather.getWeather_description());
            heat_amount_degree_celcius.setText(String.format("%.0f", (weather.getMain_temp())) + "°C");
            humidity_txt.setText("humidity - "+Math.round(weather.getMain_humidity())+"%");
            wind_speed.setText("wind - "+new DecimalFormat("#.0").format((weather.getWind_speed()*2.23693629))+" mph");



        }
    }
    class CurrentWeatherViewHolder extends RecyclerView.ViewHolder{
        private final TextView country_txtView;
        private final ImageView weather_icon;
        private final TextView time_txtView;
        private final TextView weather_condition_txt;
        private final TextView current_temp;
        private final TextView humidity_txt;
        private final TextView wind_speed;
        private final TextView temp_max;
        private final TextView temp_min;
        private final View view;

        public CurrentWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            country_txtView =(TextView) itemView.findViewById(R.id.current_location_city);
            weather_icon =(ImageView) itemView.findViewById(R.id.weather_icon);
            time_txtView =(TextView) itemView.findViewById(R.id.current_weather_time);
            weather_condition_txt = (TextView)itemView.findViewById(R.id.weather_condition_txt);
            current_temp =(TextView) itemView.findViewById(R.id.current_temp);
            humidity_txt=(TextView) itemView.findViewById(R.id.humidity_value);
            wind_speed =(TextView) itemView.findViewById(R.id.wind_speed);
            temp_max =(TextView) itemView.findViewById(R.id.temp_max);
            temp_min =(TextView) itemView.findViewById(R.id.temp_min);
        }
        public void bindToData(Weather weather){
            Glide.with(view.getContext())
                    .load("http://openweathermap.org/img/w/"+weather.getWeather_icon()+".png")
                    .into(weather_icon);
            country_txtView.setText(weather.getName_city());
            SimpleDateFormat sdf=new SimpleDateFormat("h:mm a");
            time_txtView.setText(sdf.format(new Date(weather.getDt()* 1000)));
            weather_condition_txt.setText(weather.getWeather_description());
            current_temp.setText(String.format("%.0f", (weather.getMain_temp())) + "°C");
            humidity_txt.setText(Math.round(weather.getMain_humidity())+"%");
            wind_speed.setText(new DecimalFormat("#.0").format((weather.getWind_speed()*2.237))+" mph");
            temp_min.setText(String.format("%.0f", (weather.getMain_temp_min())) + "°C");
            temp_max.setText(String.format("%.0f", (weather.getMain_temp_max())) + "°C");



        }
    }
}
