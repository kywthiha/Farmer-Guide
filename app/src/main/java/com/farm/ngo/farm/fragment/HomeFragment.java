package com.farm.ngo.farm.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.ngo.farm.activity.FacebookAccKitActivity;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.NetWorkUtail;
import com.farm.ngo.farm.activity.PwalyoneActivity;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.NavigationCustomDone;
import com.farm.ngo.farm.activity.WeatherFiveDatyActivity;
import com.farm.ngo.farm.activity.ShopActivity;
import com.farm.ngo.farm.activity.UserLoginActivity;
import com.farm.ngo.farm.utility.Rabbit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
    public NavigationCustomDone mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (NavigationCustomDone) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }
    //Declaration
    TextView detailsField, currentTemperatureField, weatherIcon, updatedField;
    ProgressBar loader;
    Typeface weatherFont;
    CardView weahter;
    String city = "lat=21.3343&lon=95.0944";
    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "cccd055e7a485c62e5ed11986aaf08c1";
    /* Please Put your API KEY here */
    Context mContext;
    CardView paddyCard, otherCropCard, questionAndAnswerCard, newsCard, pwalyoneCardView,farmStore;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private Double Lat,Lon;
    private final int REQUEST_LOCATION_PERMISSION = 356;
    public HomeFragment(Context mContext, int pagerId) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //For Weatherset Data
        updatedField = (TextView) rootView.findViewById(R.id.updated_field);
        detailsField = (TextView) rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView) rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(mContext.getAssets(), "weathericons-regular-webfont.ttf");
        weahter=(CardView)rootView.findViewById(R.id.onedayweather);
        weatherIcon.setTypeface(weatherFont);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if(!Mdetect.isUnicode()){
            TextView title=(TextView)rootView.findViewById(R.id.title);
            TextView moe_menu=(TextView)rootView.findViewById(R.id.moe_menu_txt);
            TextView other_crop=(TextView)rootView.findViewById(R.id.othoer_crop_txt);
            TextView qa=(TextView)rootView.findViewById(R.id.qa_txt);
            TextView news=(TextView)rootView.findViewById(R.id.news_txt);
            TextView argi_shop=(TextView)rootView.findViewById(R.id.argi_shop_txt);
            TextView pwaylone=(TextView)rootView.findViewById(R.id.pwaylone_txt);

            title.setText(Rabbit.uni2zg(getString(R.string.weathertitle)));
            moe_menu.setText(Rabbit.uni2zg(getString(R.string.moe_rice)));
            other_crop.setText(Rabbit.uni2zg(getString(R.string.otherfruit)));
            qa.setText(Rabbit.uni2zg(getString(R.string.q_and_a)));
            news.setText(Rabbit.uni2zg(getString(R.string.news)));
            argi_shop.setText(Rabbit.uni2zg(getString(R.string.argi_shops)));
            pwaylone.setText(Rabbit.uni2zg(getString(R.string.broken_center)));
        }
        //For Actio of layout
        paddyCard = (CardView) rootView.findViewById(R.id.paddy_card);
        farmStore = (CardView) rootView.findViewById(R.id.farmstore);
        otherCropCard = (CardView) rootView.findViewById(R.id.other_crop_card);
        questionAndAnswerCard = (CardView) rootView.findViewById(R.id.question_answer_card);
        newsCard = (CardView) rootView.findViewById(R.id.news_card);
        pwalyoneCardView = (CardView) rootView.findViewById(R.id.pwalyone);
        farmStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ShopActivity.class);
                getActivity().startActivity(i);
            }
        });
        pwalyoneCardView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent i=new Intent(getActivity(),PwalyoneActivity.class);
                                                    getActivity().startActivity(i);
                                                }
                                            }
        );
        paddyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                PaddyFragment homeFragment = new PaddyFragment(mContext);
                transaction.replace(R.id.view_pager, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        otherCropCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction cTransaction = getFragmentManager().beginTransaction();
                DataFragment cropFragment = new DataFragment();
                cropFragment.setmContext(mContext);
                cropFragment.setTableName("other_crop");
                cTransaction.replace(R.id.view_pager, cropFragment);
                cTransaction.addToBackStack(null);
                cTransaction.commit();
            }
        });

        questionAndAnswerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),FacebookAccKitActivity.class);
                getActivity().startActivity(in);
            }
        });

        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction nTransaction = getFragmentManager().beginTransaction();
                NewsFragment newsFragment = new NewsFragment(mContext, false);
                nTransaction.replace(R.id.view_pager, newsFragment);
                nTransaction.addToBackStack(null);
                nTransaction.commit();
                mCallback.OnButtonClickListener();
            }
        });
        weahter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),WeatherFiveDatyActivity.class);
                startActivity(i);

            }
        });

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){

            // Permission is granted
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;

                                Lat=mLastLocation.getLatitude();
                                Lon= mLastLocation.getLongitude();
                                taskLoadUp("lat="+Lat+"&lon="+Lon);

                            } else {
                                //mLocationTextView.setText(R.string.no_location);
                            }
                        }
                    });
        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {
                Toast.makeText(getContext(),"Location permension is needed to weather status",Toast.LENGTH_SHORT).show();

            }

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);



        }




        return rootView;
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
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
                               taskLoadUp("lat="+Lat+"&lon="+Lon);

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
                    Toast.makeText(getContext(),
                            "location_permission_denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void taskLoadUp(String query) {
        if (NetWorkUtail.isNetworkAvailable(mContext)) {
            if(Lat!=null && Lon!=null){
            DownloadWeather task = new DownloadWeather();
            task.execute(query);}
            else{
                Toast.makeText(mContext,"No find loction",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadWeather extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... args) {
            String xml = NetWorkUtail.excuteGet("http://api.openweathermap.org/data/2.5/weather?" + args[0] +
                    "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.0f", main.getDouble("temp")) + "°C");
                    updatedField.setText(sdf.format(new Date(json.getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(NetWorkUtail.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));
                    final ArrayList<String> goweather=new ArrayList<String>();
                    goweather.add(String.format("%.0f", main.getDouble("temp")) + "°C");
                    goweather.add("စိုထိုင္းဆ =" + main.getString("humidity") + "%");
                    goweather.add("ေလထုဖိအား =" + main.getString("pressure") + " hPa");
                }
            } catch (JSONException e) {
                Toast.makeText(mContext, "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
