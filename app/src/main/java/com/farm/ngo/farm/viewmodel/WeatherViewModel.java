package com.farm.ngo.farm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.farm.ngo.farm.model.Weather;
import com.farm.ngo.farm.repository.WeatherRepository;

import java.util.List;


public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository mWeatherRepository;
    private LiveData<List<Weather>> mWeather;
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mWeatherRepository=new WeatherRepository(application);
        mWeather=mWeatherRepository.getmAllWeather();
    }


    public LiveData<List<Weather>> getmWeather() {
        return mWeather;
    }
    public void insert(Weather weather){
        mWeatherRepository.insert(weather);
    }
    public void delete(){
        mWeatherRepository.delete();
    }


}
