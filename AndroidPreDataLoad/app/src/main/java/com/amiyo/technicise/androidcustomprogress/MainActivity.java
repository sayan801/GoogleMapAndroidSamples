package com.amiyo.technicise.androidcustomprogress;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    private final String TAG = "CustomProgressBarActivity ";
    /**
     * URl to parse the json array object.
     */
    protected static String url ="http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/" +
            "getProviderInfoByPartialNameZipDistance/davis/66213/1";
    protected static String address = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/" +
            "geocoding/getLatLongFromAddress/";


    App app;
   // DataTransporter dataTransporter;
   SharedPreferenceClass sharedPrefClassObj;

    /**
     * Layout Views.
     */
    public Button BtnLoad;
    public ProgressBar MyProgressBar;
    public String jsonResponse,jsonData,callAddress,provideraddress;

    public String ProviderFirstLineBusinessMailingAddress,ProviderBusinessMailingAddressCityName,ProviderBusinessMailingAddressStateName,
                  ProviderBusinessMailingAddressPostalCode,ProviderSecondLineBusinessMailingAddress;

    public JSONArray jsonLatLongArray;
    public JSONObject jsonObjectLatLng;
    public ArrayList<HashMap<String, Object>> arrayListLatLong = new ArrayList<HashMap<String, Object>>();
    public String ProviderLatitude, ProviderLongitude;
    public ServiceHandler serviceHandler = new ServiceHandler();
    public ImageView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplication();
        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());

        MyProgressBar = (ProgressBar) findViewById(R.id.CustomProgressBar);
        BtnLoad = (Button) findViewById(R.id.ButtonLoad);

        ActionBarDynamic actionbardynamic = new ActionBarDynamic();
        String colorCode = "#54D66A";
        actionbardynamic.DynamicActionBar(getActionBar(), colorCode);
        ActionBar ab = getActionBar();
        ab.setCustomView(R.layout.custom_actionbar_layout);

        view = (ImageView) findViewById(android.R.id.home);
        view.setPadding(20, 0, 0, 0);

        TextView TvActionbarTitle = (TextView) findViewById(R.id.TvActionBarTitle);
        TvActionbarTitle.setText("PROVIDER DETAILS");
        TvActionbarTitle.setTextColor(Color.parseColor("#FFFFFF"));

    }

    public void LoadingData(View i) {

       new PareJSON().execute();

    }


    /**
     * Private Class for Parsing the JSON over the Internet.
     */
    private class PareJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Creating instance of service handler class.
            ServiceHandler serviceHandler = new ServiceHandler();

            // Making a request to url and getting the response
            jsonResponse = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            parseLatLang(jsonResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            MyProgressBar.setVisibility(View.GONE);

            app.setDataLoadOnListView(sharedPrefClassObj);

            //Fire an Intent.
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.setAction(SharedPreferenceClass.INTENT_ACTION_OPEN);
            startActivity(intent);
        }
    }


    public void parseLatLang(String jsonData) {
        try {

            sharedPrefClassObj.setJSONData(jsonResponse);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("npidata");

                sharedPrefClassObj.setLat(jsonArray.length());
                sharedPrefClassObj.setLang(jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    String[] lat_lang = getLatLong(jsonArray.getJSONObject(i));
                    sharedPrefClassObj.getLat()[i] = lat_lang[0];
                    sharedPrefClassObj.getLang()[i] = lat_lang[1];
                }
        } catch (JSONException error) {
            Log.e(TAG, error.toString());
            error.printStackTrace();
        }
    }

    private String[] getLatLong(JSONObject jsonObject) {
        HashMap<String, Object> provider = new HashMap<String, Object>();
        String[] lat_lang = new String[2];
        try {
            if (jsonObject.has("Provider First Line Business Practice Location Address")) {
                ProviderFirstLineBusinessMailingAddress = jsonObject.getString("Provider First Line Business Practice Location Address");
            } else {
                ProviderFirstLineBusinessMailingAddress = " ";
            }

            if (jsonObject.has("Provider Second First Line Business Practice Location Address")) {
                ProviderSecondLineBusinessMailingAddress = jsonObject.getString("Provider Second First Line Business Practice Location Address");
            } else {
                ProviderSecondLineBusinessMailingAddress = " ";
            }

            if (jsonObject.has("Provider Business Practice Location Address City Name")) {
                ProviderBusinessMailingAddressCityName = jsonObject.getString("Provider Business Practice Location Address City Name");
            } else {
                ProviderBusinessMailingAddressCityName = " ";
            }

            if (jsonObject.has("Provider Business Practice Location Address State Name")) {
                ProviderBusinessMailingAddressStateName = jsonObject.getString("Provider Business Practice Location Address State Name");
            } else {
                ProviderBusinessMailingAddressStateName = " ";
            }

            if (jsonObject.has("Provider Business Practice Location Address Postal Code")) {
                ProviderBusinessMailingAddressPostalCode = jsonObject.getString("Provider Business Practice Location Address Postal Code");
            } else {
                ProviderBusinessMailingAddressPostalCode = " ";
            }

            //take first 5 digit of provider Zip code
            ProviderBusinessMailingAddressPostalCode = ProviderBusinessMailingAddressPostalCode
                    .substring(0, Math.min(ProviderBusinessMailingAddressPostalCode.length(), 5));

            provideraddress = ProviderFirstLineBusinessMailingAddress + ", " +
                    ProviderSecondLineBusinessMailingAddress + ", " +
                    ProviderBusinessMailingAddressCityName + ", " +
                    ProviderBusinessMailingAddressStateName + ", " +
                    ProviderBusinessMailingAddressPostalCode;

            provideraddress = provideraddress.replace(" ", "+");
            provideraddress = provideraddress.replace("?", "");   //'Disallowed Key Characters.'
            provideraddress = provideraddress.replace("#", "");

            if (provideraddress == null || provideraddress.isEmpty())
                provideraddress = "addressnotfound"; // will return 'unknown' lat-long and prevented app crash

            /////////////// API CALL //////////////////

            callAddress = address + provideraddress;
            jsonData = serviceHandler.makeServiceCall(callAddress, ServiceHandler.GET);
            JSONObject JObjectFetchAddress = new JSONObject(jsonData);
            jsonLatLongArray = JObjectFetchAddress.getJSONArray("result");

            jsonObjectLatLng = jsonLatLongArray.getJSONObject(0);

            if (jsonObjectLatLng.getString("status").equals("OK")) {
                ProviderLatitude = String.valueOf(jsonObjectLatLng.getDouble("latitude"));
                ProviderLongitude = String.valueOf(jsonObjectLatLng.getDouble("longitude"));

                provider.put("ProviderLatitude", ProviderLatitude);
                provider.put("ProviderLongitude", ProviderLongitude);

                lat_lang[0] = ProviderLatitude;
                lat_lang[1] = ProviderLongitude;
                arrayListLatLong.add(provider);
            }
        } catch (Exception error) {
            Log.e(TAG, error.toString());
        }

        return lat_lang;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}