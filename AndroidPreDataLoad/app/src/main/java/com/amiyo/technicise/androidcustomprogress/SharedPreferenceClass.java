package com.amiyo.technicise.androidcustomprogress;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by technicise on 5/12/14.
 */
public class SharedPreferenceClass {

    public static String INTENT_ACTION_OPEN = "INTENT_ACTION_OPEN";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MyPref";

    public static final String KEY_SET_JSON_DATA = "KEY_SET_JSON_DATA";
    public static final String KEY_SET_JSON_ADDRESS = "KEY_SET_JSON_ADDRESS";
    public SharedPreferenceClass(Context context){
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void setJSONData(String ListID) {
        editor.remove(KEY_SET_JSON_DATA);
        editor.putString(KEY_SET_JSON_DATA, ListID);
        editor.commit();
      }

    public String getJSONData() {
        return pref.getString(KEY_SET_JSON_DATA, null);
    }

    private String[] lat;

    public void setLat(int length) {
        lat = new String[length];
    }

    public String[] getLat( ) {
        return lat;
    }

    private String[] lang;

    public void setLang(int length) {
        lang = new String[length];
    }

    public String[] getLang( ) {
        return lang;
    }


    private String[] firstName;

    public void setfirstName(int length) {
        firstName = new String[length];
    }

    public String[] getfirstName( ) {
        return firstName;
    }


}
