package com.farm.ngo.farm.activity;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.ngo.farm.utility.NetWorkUtail;
import com.farm.ngo.farm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherFiveDatyActivity extends AppCompatActivity {
    RecyclerView rc;
    private final ArrayList<String> ary=new ArrayList<>();
    public void addVehicle( String string ) {
        ary.add( string );
    }
    String city = "lat=21.3343&lon=95.0944";// for pakokku
    String OPEN_WEATHER_MAP_API = "cccd055e7a485c62e5ed11986aaf08c1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_weatherfiveday);
        taskLoadUp(city);
        ArrayList<String> kyaw=(ArrayList<String>)getIntent().getStringArrayListExtra("currentweather");
        TextView temp=(TextView)findViewById(R.id.temp);
        TextView hum=(TextView)findViewById(R.id.hum);
        TextView pres=(TextView)findViewById(R.id.pres);
        temp.setText(kyaw.get(0));
        hum.setText(kyaw.get(1));
        pres.setText(kyaw.get(2));
        rc=(RecyclerView)findViewById(R.id.ryrc);


    }
    public void taskLoadUp(String query) {
        if (NetWorkUtail.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }



    class DownloadWeather extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        protected String doInBackground(String...args) {
            String xml = NetWorkUtail.excuteGet("http://api.openweathermap.org/data/2.5/forecast?" + args[0] +
                    "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                ArrayList<String> weatherin=new ArrayList<>();
                if (json != null) {
                    JSONArray lis=json.getJSONArray("list");
                    for(int i=0;i<lis.length();i++){
                        JSONObject list = lis.getJSONObject(i);
                        JSONObject details = list.getJSONArray("weather").getJSONObject(0);
                        JSONObject main = list.getJSONObject("main");
                        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM\nh:mm a");
                        DateFormat df = DateFormat.getDateTimeInstance();
                        //  String in=String.valueOf(Html.fromHtml(NetWorkUtail.setWeatherIcon(details.getInt("id"),
                        //  list.getJSONObject("sys").getLong("sunrise") * 1000,
                        //  list.getJSONObject("sys").getLong("sunset") * 1000)));
                        String in=sdf.format(new Date(list.getLong("dt") * 1000))+"~"+String.format("%.0f", (main.getDouble("temp"))) + "°C"+"~"+details.getString("icon");
                        weatherin.add(in);
                        Log.i("kk",sdf.format(new Date(list.getLong("dt") * 1000)));}
                    com.farm.ngo.farm.adapter.weatherfivedayAdapter adapter=new com.farm.ngo.farm.adapter.weatherfivedayAdapter(WeatherFiveDatyActivity.this,weatherin);
                    rc.setAdapter(adapter);
                    LinearLayoutManager k=new LinearLayoutManager(WeatherFiveDatyActivity.this,LinearLayoutManager.VERTICAL, false);
                    //k.setStackFromEnd(true);
                    rc.setLayoutManager(k);

                    //String ary[]={"ic_cloud_queue_black_24dp,CLEAR SKY,20 C,11:20PM","ic_cloud_queue_black_24dp,CLEAR SKY,20 C,11:20PM","ic_cloud_queue_black_24dp,CLEAR SKY,20 C,11:20PM","ic_cloud_queue_black_24dp,CLEAR SKY,20 C,11:20PM","ic_cloud_queue_black_24dp,CLEAR SKY,20 C,11:20PM","ic_cloud_queue_black_24dp,CLEAR SKY,20 C,11:20PM"};

                    //JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    //JSONObject main = json.getJSONObject("main");
                    //DateFormat df = DateFormat.getDateTimeInstance();
                    //SimpleDateFormat sdf=new SimpleDateFormat("h:mm a");
                    //cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    //detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    //currentTemperatureField.setText(String.format("%.0f", main.getDouble("temp")) + "°C");
                    //humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    // pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    //updatedField.setText(sdf.format(new Date(json.getLong("dt") * 1000)));




                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }}