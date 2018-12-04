package com.farm.ngo.farm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import com.farm.ngo.farm.Holder.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.ngo.farm.Class.Function;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.activity.QuestionAnswerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements LocationListener {

    //Declaration
    TextView detailsField, currentTemperatureField, weatherIcon, updatedField;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Pakokku, MM";
    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "cbfdb21fa1793c10b14b6b6d00fbef03";
    /* Please Put your API KEY here */

    Context mContext;
    Double x = 21.333093;
    Double y = 94.986282;

    CardView paddyCard, otherCropCard, questionAndAnswerCard, newsCard;

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
        weatherIcon.setTypeface(weatherFont);
        taskLoadUp(city);


        //For Actio of layout
        paddyCard = (CardView) rootView.findViewById(R.id.paddy_card);
        otherCropCard = (CardView) rootView.findViewById(R.id.other_crop_card);
        questionAndAnswerCard = (CardView) rootView.findViewById(R.id.question_answer_card);
        newsCard = (CardView) rootView.findViewById(R.id.news_card);

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
                User u= UsingSQLiteHelper.getUser(getActivity());
                Intent in = new Intent(getActivity(),QuestionAnswerActivity.class);
                in.putExtra("user",u);
                in.putExtra("phone_number","09771616178");
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
            }
        });


        return rootView;
    }

    public void taskLoadUp(String query) {
        if (Function.isNetworkAvailable(mContext)) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void setFragmentFocus (Fragment fragmentFocus){
        fragmentFocus.getView().setFocusableInTouchMode(true);
        fragmentFocus.getView().requestFocus();
        fragmentFocus.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    HomeFragment homeFragment = new HomeFragment(mContext, R.id.view_pager);
                    transaction.replace(R.id.view_pager, homeFragment);
                    transaction.commit();
                }
                return true;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        x = location.getLatitude();
        y = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


//    class DownloadWeather extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        protected String doInBackground(String... args) {
//            String xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/forecast?lat=" + x + "&lon=" + y +
//                    "&appid=" + OPEN_WEATHER_MAP_API);
//            return xml;
//        }
//
//        @Override
//        protected void onPostExecute(String xml) {
//
//            try {
//                JSONObject json = new JSONObject(xml);
//                Log.i("JSON", json.length() + "*****");
//                for (int i = 0; i < json.length(); i++) {
//                    Log.i("LIST", json.getJSONArray("list").getJSONObject(i).toString());
//                    JSONObject object = json.getJSONArray("list").getJSONObject(i);
//                    JSONObject details = object.getJSONArray("weather").getJSONObject(0);
//                    JSONObject main = object.getJSONObject("main");
//                    DateFormat df = DateFormat.getDateInstance();
//                    String detailsField, currentTemperatureField, humidityField, pressureField, updatedField, weatherIcon;
//                    detailsField = details.getString("description").toUpperCase(Locale.US);
//                    currentTemperatureField = String.format("%.2f", (main.getDouble("temp") -  273.15)) + "°";
//                    humidityField = "Humidity: " + main.getString("humidity") + "%";
//                    pressureField = "Pressure: " + main.getString("pressure") + " hPa";
//                    updatedField = df.format(new Date(object.getLong("dt") * 1000));
//                    weatherIcon = "http://openweathermap.org/img/w/" + details.getString("icon") + ".png";
//
//                }
//                weatherRecyclerView.setAdapter(adapter);
//
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }


    //For WeatherData Loading

    class DownloadWeather extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... args) {
            String xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
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

                    //cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.0f", main.getDouble("temp")) + "°C");
                    //humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    // pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(sdf.format(new Date(json.getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));


                }
            } catch (JSONException e) {
                Toast.makeText(mContext, "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
