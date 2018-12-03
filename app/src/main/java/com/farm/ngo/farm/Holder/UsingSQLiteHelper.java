package com.farm.ngo.farm.Holder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.farm.ngo.farm.Model.Data;
import com.farm.ngo.farm.Model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UsingSQLiteHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";

    private static final String DB_NAME = "farm";

    private static final int DB_VERSION = 1;

    private final Context mContext;
    private SQLiteDatabase mDataBase;

    public UsingSQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        Log.w("UsingSQLiteHelper: ", DB_PATH);
        this.mContext = context;
    }
    public void createDataBase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e( "createDatabase","createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        Log.i("checkDataBase",dbFile.getAbsolutePath());
        return dbFile.exists();
    }

    public boolean openDataBase() throws SQLException{
        String path=DB_PATH+DB_NAME;
        mDataBase=SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Log.i("openDataBase",mDataBase.toString());
        return mDataBase!=null;
    }

    @Override
    public synchronized void close() {
        if(mDataBase!=null){
            mDataBase.close();
        }
        super.close();
    }

    private void copyDataBase() throws IOException {

        InputStream mInput = mContext.getAssets().open(DB_NAME);

        String outfileName = DB_PATH+DB_NAME;

        OutputStream mOutput = new FileOutputStream(outfileName);

        byte[] buffer = new byte[1024];

        int mLength;

        while ((mLength = mInput.read(buffer)) > 0) {

            mOutput.write(buffer, 0, mLength);

        }

        mOutput.flush();

        mInput.close();

        mOutput.close();

    }

    public String[] getData() {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + "other_crop", null);
        cursor.moveToFirst();


        return new String[]{cursor.getString(0), cursor.getString(1)};
    }

    //To read All data by Table Name from Database
    public ArrayList<Data> getDataList(String tableName) throws IOException {
        createDataBase();
        openDataBase();
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Data> dataList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(new Data(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                Log.w("getDataList: ", cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        close();
        return dataList;
    }


//    public void sendUserData(User user) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("id", user.getId());
//        values.put("name", user.getName());
//        values.put("profile_url", user.getProfile_url());
//        values.put("email", user.getEmail());
//        values.put("work","");
//        values.put("gender","");
//        values.put("township", user.getTownship());
//
//        db.insert(TABLE_NAME, null, values);
//    }

    public User getUser() {
       // SQLiteDatabase db = getWritableDatabase();
        User user = null;
//        /*Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
//        cursor.moveToFirst();*/

        user = new User("+959778673750","mg mg", "",
              "dsf", "Farmer","male","myaing");
      /*  while (cursor.moveToNext()){
            user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6));
        }*/
        return user;
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
        this.addCropList("rice");
        this.addCropList("peanut");
        this.addCropList("cucumber");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
