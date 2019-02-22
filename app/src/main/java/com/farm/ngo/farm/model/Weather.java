package com.farm.ngo.farm.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "weather")
public class Weather {
    @PrimaryKey
    private int id;
    private double coord_lon;
    private double coord_lit;
    private long weather_id;
    private String weather_main;
    private String weather_description;
    private String weather_icon;
    private double main_temp;
    private double main_pressure;
    private double main_sea_level;
    private double main_grnd_level;
    private double main_humidity;
    private double main_temp_min;
    private double main_temp_max;
    private double wind_speed;
    private double wind_deg;
    private String name_city;
    private double coluds_all;
    private double rain;
    private double snow;
    private long dt;//datetime second
    private boolean isTitle;
    private boolean isCurrent;

    public Weather() {
        this.rain=0;
        this.snow=0;
        this.coluds_all=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCoord_lon() {
        return coord_lon;
    }

    public void setCoord_lon(double coord_lon) {
        this.coord_lon = coord_lon;
    }

    public double getCoord_lit() {
        return coord_lit;
    }

    public void setCoord_lit(double coord_lit) {
        this.coord_lit = coord_lit;
    }

    public Long getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(Long weather_id) {
        this.weather_id = weather_id;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    public double getMain_temp() {
        return main_temp;
    }

    public void setMain_temp(double main_temp) {
        this.main_temp = main_temp;
    }

    public double getMain_pressure() {
        return main_pressure;
    }

    public void setMain_pressure(double main_pressure) {
        this.main_pressure = main_pressure;
    }

    public double getMain_sea_level() {
        return main_sea_level;
    }

    public void setMain_sea_level(double main_sea_level) {
        this.main_sea_level = main_sea_level;
    }

    public double getMain_grnd_level() {
        return main_grnd_level;
    }

    public void setMain_grnd_level(double main_grnd_level) {
        this.main_grnd_level = main_grnd_level;
    }

    public double getMain_humidity() {
        return main_humidity;
    }

    public void setMain_humidity(double main_humidity) {
        this.main_humidity = main_humidity;
    }

    public double getMain_temp_min() {
        return main_temp_min;
    }

    public void setMain_temp_min(double main_temp_min) {
        this.main_temp_min = main_temp_min;
    }

    public double getMain_temp_max() {
        return main_temp_max;
    }

    public void setMain_temp_max(double main_temp_max) {
        this.main_temp_max = main_temp_max;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public double getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(double wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    public double getColuds_all() {
        return coluds_all;
    }

    public void setColuds_all(double coluds_all) {
        this.coluds_all = coluds_all;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", coord_lon=" + coord_lon +
                ", coord_lit=" + coord_lit +
                ", weather_id=" + weather_id +
                ", weather_main='" + weather_main + '\'' +
                ", weather_description='" + weather_description + '\'' +
                ", weather_icon='" + weather_icon + '\'' +
                ", main_temp=" + main_temp +
                ", main_pressure=" + main_pressure +
                ", main_sea_level=" + main_sea_level +
                ", main_grnd_level=" + main_grnd_level +
                ", main_humidity=" + main_humidity +
                ", main_temp_min=" + main_temp_min +
                ", main_temp_max=" + main_temp_max +
                ", wind_speed=" + wind_speed +
                ", wind_deg=" + wind_deg +
                ", name_city='" + name_city + '\'' +
                ", coluds_all=" + coluds_all +
                ", rain=" + rain +
                ", snow=" + snow +
                ", dt=" + dt +
                '}';
    }
}
