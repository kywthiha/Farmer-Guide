package com.farm.ngo.farm.dao;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.farm.ngo.farm.model.Weather;

import java.util.List;


@Dao
public interface WeatherDaoo {
    @Insert
    void insert(Weather data);

    @Query("DELETE FROM weather")
    void deleteAll();

    @Query("SELECT * FROM weather ORDER BY id ASC")
    LiveData<List<Weather>> getAllWeather();
}
