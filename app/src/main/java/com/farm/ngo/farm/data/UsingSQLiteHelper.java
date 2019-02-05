package com.farm.ngo.farm.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.accountkit.AccountKit;
import com.farm.ngo.farm.model.CropItem;
import com.farm.ngo.farm.model.Pwalyone;
import com.farm.ngo.farm.model.Data;
import com.farm.ngo.farm.model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public List<CropItem> getCropItemList(String tableName) throws IOException {
        createDataBase();
        openDataBase();
        SQLiteDatabase db = this.getReadableDatabase();

        List<CropItem> dataList = new LinkedList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(new CropItem(cursor.getString(2), cursor.getString(0),tableName));
            }while(cursor.moveToNext());
        }
        cursor.close();
        close();
        return dataList;
    }
    public ArrayList<Pwalyone> getPwalyoneList(String tableName) throws IOException {
        createDataBase();
        openDataBase();
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Pwalyone> pwalyoneList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if(cursor.moveToFirst()) {
            do {
                pwalyoneList.add(new Pwalyone(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        close();
        return pwalyoneList;
    }

    //To read All data by Table Name from Database
    public Data getDataDetail(String tableName,String id) throws IOException {
        createDataBase();
        openDataBase();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("data info","data create");

        Data data=new Data();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName +" WHERE photo = ?", new String[]{id});

        if(cursor.moveToFirst()) {

               data=new Data(cursor.getString(0), cursor.getString(1), cursor.getString(2));
               Log.i("data info",data.getTitle());


        }
        cursor.close();
        close();
        return data;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
