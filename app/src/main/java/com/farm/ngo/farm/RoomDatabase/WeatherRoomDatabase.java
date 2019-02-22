package com.farm.ngo.farm.RoomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.farm.ngo.farm.dao.WeatherDaoo;
import com.farm.ngo.farm.model.Weather;

import javax.security.auth.callback.Callback;

@Database(entities = {Weather.class},version = 1)
public abstract class WeatherRoomDatabase extends RoomDatabase {
    public abstract WeatherDaoo weatherDaoo();
    private static volatile WeatherRoomDatabase INSTANCE;
    static public WeatherRoomDatabase getDatabase(Context context){
        if(INSTANCE==null){
            synchronized (WeatherRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),WeatherRoomDatabase.class,"weather").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback=new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WeatherDaoo mDao;

        PopulateDbAsync(WeatherRoomDatabase db) {
            mDao = db.weatherDaoo();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }

}
