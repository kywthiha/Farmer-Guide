package com.farm.ngo.farm.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.farm.ngo.farm.RoomDatabase.WeatherRoomDatabase;
import com.farm.ngo.farm.dao.WeatherDaoo;
import com.farm.ngo.farm.model.Weather;

import java.util.List;


public class WeatherRepository {
    private WeatherDaoo weatherDao;
    private LiveData<List<Weather>> mAllWeather;
    public WeatherRepository(Application application){
        WeatherRoomDatabase db=WeatherRoomDatabase.getDatabase(application);
        weatherDao=db.weatherDaoo();
        mAllWeather=weatherDao.getAllWeather();
    }

    public LiveData<List<Weather>> getmAllWeather() {
        return mAllWeather;
    }
    public void insert(Weather weather){
        new insertAsyncTask(weatherDao).execute(weather);
    }
    public void delete(){
        new deleteAsyncTask(weatherDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Weather,Void,Void>{
        WeatherDaoo weatherDaoo;
        insertAsyncTask(WeatherDaoo weatherDaoo){
            this.weatherDaoo=weatherDaoo;
        }


        @Override
        protected Void doInBackground(Weather... weathers) {
            weatherDaoo.insert(weathers[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Void,Void,Void>{
        WeatherDaoo weatherDaoo;
        deleteAsyncTask(WeatherDaoo weatherDaoo){
            this.weatherDaoo=weatherDaoo;
        }


        @Override
        protected Void doInBackground(Void... weathers) {
            weatherDaoo.deleteAll();
            return null;
        }
    }
}
