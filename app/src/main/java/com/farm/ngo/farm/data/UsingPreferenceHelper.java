package com.farm.ngo.farm.data;
import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.accountkit.AccountKit;
import com.farm.ngo.farm.model.User;

public class UsingPreferenceHelper {
    private static final String FARMUSER ="k987487823kyea";
    private static final String CURRENT_WEATER_DATA="u98u89y8klfnskhf";
    private static final String weather_description="78978klsfjkl";
    private static final String weather_icon="kjfiieojf";
    private static final String main_temp="ekjie23";
    private static final String date_time="e424cfsfse11";
    private static final String sunrise="9uhi9shf";
    private static final String sunset="uru89unifjsk";
    private static final String humidity="hieuriow";
    private static final String pressure="8y978jfhshf";
    public static void setUser(User user, Context mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(FARMUSER, mContext.MODE_PRIVATE).edit();
        editor.putString("username",user.getName());
        editor.putString("userid",user.getId());
        editor.commit();

    }

    public static User getUser(Context mContext) {
        User user = null;
        SharedPreferences preferences = mContext.getSharedPreferences(FARMUSER, mContext.MODE_PRIVATE);
        if( AccountKit.getCurrentAccessToken() != null && !preferences.getString("userid","").equals("")) {

            user = new User(preferences.getString("userid", ""), preferences.getString("username", ""), "pakokku", "", "hello ", "male", "Pakokku");
        }
        return user;
    }




}
