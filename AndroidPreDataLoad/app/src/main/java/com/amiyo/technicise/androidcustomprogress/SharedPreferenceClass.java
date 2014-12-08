package com.amiyo.technicise.androidcustomprogress;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by technicise on 5/12/14.
 */
public class SharedPreferenceClass {

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "MyPref";
    // All Shared Preferences Keys

    public static final String KEY_SET_JSON_DATA = "KEY_SET_JSON_DATA";
    public static final String KEY_SET_JSON_ADDRESS = "KEY_SET_JSON_ADDRESS";

    public SharedPreferenceClass(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void SetListData(String ListID)

    {
        editor.remove(KEY_SET_JSON_DATA);
        editor.putString(KEY_SET_JSON_DATA, ListID);
        editor.commit();

    }
    public String GetListData()
    {
        String  ListID= pref.getString(KEY_SET_JSON_DATA, null);
        return ListID;
    }

    public void SetProviderLatLang(String ProviderLatLang)

    {
        editor.remove(KEY_SET_JSON_ADDRESS);
        editor.putString(KEY_SET_JSON_ADDRESS, ProviderLatLang);
        editor.commit();

    }
    public String GetProviderLatLang()
    {
        String  ProviderLatLang= pref.getString(KEY_SET_JSON_ADDRESS, null);
        return  ProviderLatLang;
    }
}
