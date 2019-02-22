package com.farm.ngo.farm.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.adapter.WeatherListAdapter;
import com.farm.ngo.farm.model.Weather;
import com.farm.ngo.farm.utility.DownloadWeather;
import com.farm.ngo.farm.utility.NetWorkUtility;
import com.farm.ngo.farm.viewmodel.WeatherViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;


public class WeatherFiveDatyActivity extends AppCompatActivity {
    private WeatherViewModel weatherViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    private final int REQUEST_LOCATION_PERMISSION = 356;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private Double Lat,Lon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherfiveday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.weather_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        final WeatherListAdapter adapter = new WeatherListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getmWeather().observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(List<Weather> weathers) {
                adapter.setmWeathers(weathers);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

        {
            @Override
            public void onRefresh () {
                Refresh();

            }
        });
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocation();
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLastLocation = location;

                            Lat=mLastLocation.getLatitude();
                            Lon= mLastLocation.getLongitude();
                            Refresh();

                        } else {
                            //mLocationTextView.setText(R.string.no_location);
                        }
                    }
                });



    }

    public void Refresh() {
        if(NetWorkUtility.isNetworkAvailable(getApplicationContext()) ){
            if(Lat!=null && Lon!=null)
            new DownloadWeather(Lat,Lon,getApplication()).execute();
            else
                Toast.makeText(getApplicationContext(),"No Find Location",Toast.LENGTH_LONG).show();
        }

        else{
            Toast.makeText(getApplicationContext(),"Please your internet on",Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);

        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            //Log.d(TAG, "getLocation: permissions granted");
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;

                                Lat=mLastLocation.getLatitude();
                                Lon= mLastLocation.getLongitude();
                                Refresh();

                            } else {
                                //mLocationTextView.setText(R.string.no_location);
                            }
                        }
                    });

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            "location_permission_denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}