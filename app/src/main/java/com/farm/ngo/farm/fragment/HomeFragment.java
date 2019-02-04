package com.farm.ngo.farm.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.farm.ngo.farm.utility.NetWorkUtail;
import com.farm.ngo.farm.activity.PwalyoneActivity;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.NavigationCustomDone;
import com.farm.ngo.farm.activity.WeatherFiveDatyActivity;
import com.farm.ngo.farm.activity.ShopActivity;
import com.farm.ngo.farm.activity.UserLoginActivity;

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
    Double x = 21.333093;
    Double y = 94.986282;

    CardView paddyCard, otherCropCard, questionAndAnswerCard, newsCard, pwalyoneCardView,farmStore;

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


        taskLoadUp(city);


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
               // User u= UsingSQLiteHelper.getUser(getActivity());
                Intent in = new Intent(getActivity(),UserLoginActivity.class);
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


        return rootView;
    }

    public void taskLoadUp(String query) {
        if (NetWorkUtail.isNetworkAvailable(mContext)) {
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
                    weahter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(getActivity(),WeatherFiveDatyActivity.class);
                            i.putStringArrayListExtra("currentweather",goweather);
                            startActivity(i);

                        }
                    });

                }
            } catch (JSONException e) {
                Toast.makeText(mContext, "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
