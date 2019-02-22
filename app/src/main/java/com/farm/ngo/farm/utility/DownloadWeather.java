package com.farm.ngo.farm.utility;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import com.farm.ngo.farm.model.Weather;
import com.farm.ngo.farm.repository.WeatherRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DownloadWeather extends AsyncTask<Void, Void, List<Weather> >{
    private WeatherRepository mWeatherRepository;
    private double LAT;
    private double LON;
   public DownloadWeather(double lat, double lon, Application application) {
       this.LAT=lat;
       this.LON=lon;
       mWeatherRepository=new WeatherRepository(application);
   }
    private String OPEN_WEATHER_MAP_API = "cccd055e7a485c62e5ed11986aaf08c1";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    protected List<Weather> doInBackground(Void... args) {
        String city = "lat="+this.LAT+"&lon="+this.LON;
        String currenttext = NetWorkUtility.excuteGet("http://api.openweathermap.org/data/2.5/weather?" + city +
                "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
        String text = NetWorkUtility.excuteGet("http://api.openweathermap.org/data/2.5/forecast?" +city +
                "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
        List<Weather> weatherList=new LinkedList<>();
        try {
            //current weather data
            JSONObject jsoncurrent = new JSONObject(currenttext);

            if (jsoncurrent != null) {
                    Weather weather=new Weather();
                    weather.setId(0);
                    if(jsoncurrent.has("name")){
                        weather.setName_city(jsoncurrent.getString("name"));
                    }

                        JSONObject coord=jsoncurrent.getJSONObject("coord");
                        weather.setCoord_lit(coord.getDouble("lat"));
                        weather.setCoord_lon(coord.getDouble("lon"));

                    JSONObject description = jsoncurrent.getJSONArray("weather").getJSONObject(0);
                    weather.setWeather_main(description.getString("main"));
                    weather.setWeather_id(description.getLong("id"));
                    weather.setWeather_description(description.getString("description"));
                    weather.setWeather_icon(description.getString("icon"));
                    JSONObject main = jsoncurrent.getJSONObject("main");
                    weather.setMain_temp_min(main.getDouble("temp_min"));
                    weather.setMain_temp_max(main.getDouble("temp_max"));
                    weather.setMain_temp(main.getDouble("temp"));
                    weather.setMain_pressure(main.getDouble("pressure"));
                    if(main.has("sea_level"))
                        weather.setMain_sea_level(main.getDouble("sea_level"));
                    if(main.has("grnd_level"))
                        weather.setMain_grnd_level(main.getDouble("grnd_level"));
                    weather.setMain_humidity(main.getDouble("humidity"));
                    JSONObject wind = jsoncurrent.getJSONObject("wind");
                    weather.setWind_speed(wind.getDouble("speed"));
                    weather.setWind_deg(wind.getDouble("deg"));
                    if(jsoncurrent.has("rain")) {
                        JSONObject rain = jsoncurrent.getJSONObject("rain");
                        if(rain.has("3h"))
                            weather.setRain(rain.getDouble("3h"));
                    }
                    if(jsoncurrent.has("snow")) {
                        JSONObject snow = jsoncurrent.getJSONObject("snow");
                        if(snow.has("3h"))
                            weather.setSnow(snow.getDouble("3h"));
                    }
                    if(jsoncurrent.has("clouds")){
                        JSONObject clouds = jsoncurrent.getJSONObject("clouds");
                        if(clouds.has("all"))
                            weather.setColuds_all(clouds.getDouble("all"));
                    }
                    weather.setDt(jsoncurrent.getLong("dt"));
                    weather.setCurrent(true);
                    weatherList.add(weather);
                }


//forecast weather data fourday
                JSONObject json = new JSONObject(text);
            if (json != null) {
                JSONArray lis = json.getJSONArray("list");
                String date="";
                for (int i = 0,len=lis.length(); i < len; i++) {
                    Weather weather=new Weather();
                    JSONObject list = lis.getJSONObject(i);
                    weather.setId(i+1);
                    if(json.has("city")) {
                        JSONObject city_json = json.getJSONObject("city");
                        if(city_json.has("name"))
                        weather.setName_city(city_json.getString("name"));
                        JSONObject coord=city_json.getJSONObject("coord");
                        weather.setCoord_lit(coord.getDouble("lat"));
                        weather.setCoord_lon(coord.getDouble("lon"));
                    }
                    JSONObject description = list.getJSONArray("weather").getJSONObject(0);
                    weather.setWeather_main(description.getString("main"));
                    weather.setWeather_id(description.getLong("id"));
                    weather.setWeather_description(description.getString("description"));
                    weather.setWeather_icon(description.getString("icon"));
                    JSONObject main = list.getJSONObject("main");
                    weather.setMain_temp_min(main.getDouble("temp_min"));
                    weather.setMain_temp_max(main.getDouble("temp_max"));
                    weather.setMain_temp(main.getDouble("temp"));
                    weather.setMain_pressure(main.getDouble("pressure"));
                    if(main.has("sea_level"))
                        weather.setMain_sea_level(main.getDouble("sea_level"));
                    if(main.has("grnd_level"))
                    weather.setMain_grnd_level(main.getDouble("grnd_level"));
                    weather.setMain_humidity(main.getDouble("humidity"));
                    JSONObject wind = list.getJSONObject("wind");
                    weather.setWind_speed(wind.getDouble("speed"));
                    weather.setWind_deg(wind.getDouble("deg"));
                    if(list.has("rain")) {
                        JSONObject rain = list.getJSONObject("rain");
                        if(rain.has("3h"))
                        weather.setRain(rain.getDouble("3h"));
                    }
                    if(list.has("snow")) {
                        JSONObject snow = list.getJSONObject("snow");
                        if(snow.has("3h"))
                        weather.setSnow(snow.getDouble("3h"));
                    }
                    if(list.has("clouds")){
                        JSONObject clouds = list.getJSONObject("clouds");
                        if(clouds.has("all"))
                            weather.setColuds_all(clouds.getDouble("all"));
                    }
                    weather.setDt(list.getLong("dt"));
                    Date datedt=new Date(list.getLong("dt")*1000);
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    String curdate=format.format(datedt);
                    if(!curdate.equals(date))
                        weather.setTitle(true);
                    date=curdate;
                    weatherList.add(weather);
                }


            }

        } catch (JSONException e) {

            Log.e("err",e.getMessage()+e);

        }
        return weatherList;
    }

    @Override
    protected void onPostExecute(List<Weather> weatherList) {
        mWeatherRepository.delete();
        for(Weather weather:weatherList){
            mWeatherRepository.insert(weather);

        }


    }
}