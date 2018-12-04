package com.farm.ngo.farm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CropDBHelper extends SQLiteOpenHelper {
public static final int DATABASE_VERSION=1;
public static final String DATABASE_NAME="crops";
    public CropDBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table crop (name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public List<String> getCrops(){
        List<String> cs=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor = database.query("crop", new String[]{"name"},null,null,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(0);
            cs.add(name);
        }
        Log.i("crops arr",cs.toString());
        return cs;
    }

    public void addCropList(String c){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",c);
        database.insert("crop", null, values);
    }

    public boolean isThereCrops(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor = database.query("crop", new String[]{"name"},null,null,null,null,null);
        if (cursor.getCount()<=0){
            Log.i("c","no crops");

            return false;
        }
        Log.i("c","yes crops");
        return true;
    }

    public void addCrops(){
        this.addCropList("ကုလားပဲ (၉၂၉)");
        this.addCropList("ကုလားပဲ (ဗီတူး)");
        this.addCropList("ပဲဆီ");
        this.addCropList("ႏွမ္းဆီ");
        this.addCropList("ၾကက္သြန္နီ");
        this.addCropList("ၾကက္သြန္ျဖဴႀကီး (သစ္)");
        this.addCropList("ၾကက္သြန္ျဖဴလတ္ (သစ္)");
        this.addCropList("ၾကက္သြန္ျဖဴေသး (သစ္)");

    }



}
