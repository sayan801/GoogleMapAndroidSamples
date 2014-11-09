package com.app.mycuratioui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mycuratioui.ApiUrls;
import com.app.mycuratioui.SharedPreferenceClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProviderLocationTab extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMarkerClickListener {

    GoogleMap mapLocation;
    FragmentManager fragmentManager;
    SupportMapFragment supportMapFragment;
    SharedPreferenceClass sharedPrefClassObj;
    public Double providerLatitude, providerLongitude;
    public String Name;

    ApiUrls myApiUrls;

    Map<String, String> latLongHashMap = new HashMap<String, String>();

    String latlonURL,hospitalname,fax, address, city, state, zipcode, countyname, phonenumber, hospitaltype, hospitalowner, type,
            userInputtedZip, jsonStrServiceCall, providerName=null, providerAddress, getLatLongFromAddressURL, npiProviderPhNo, npiProviderAddess;

    TextView txtResultsLocationFragment, txtNoOfResultLocationFragment;
    ListView listFragmentLocation;
    String urlValueLocationFragment;

    public Button callProvider;
    TextView TxtphNumber;
    Button BtnSubmit, BtnCancel, BtnNavigation, BtnCall;
    Dialog dialog;
    JSONObject jsObject, jsonObject;
    JSONArray jArray;
    JSONArray latlngArry;
    ServiceHandler shProvideraddress = new ServiceHandler();
    HashMap<Integer, Marker> visibleMarkers = new HashMap<Integer, Marker>();
    HashMap<Integer, Marker> visibleMarkersGreen = new HashMap<Integer, Marker>();
    int k=0, alfaValue =0;
    private TransparentProgressDialog pd;
    private android.os.Handler h;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_location_tab);

        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());

        BtnNavigation = (Button)findViewById(R.id.providerNavigation);
        BtnNavigation.setOnClickListener(this);
        BtnNavigation.setEnabled(false);

        BtnCall = (Button) findViewById(R.id.ProviderCall);
        BtnCall.setEnabled(false);

        fragmentManager = getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapProviderDetails);
        mapLocation = supportMapFragment.getMap();
        mapLocation.setPadding(0, 0, 0, 50); //Zoom in-Out Button will be Visisble properly
        mapLocation.clear();
        mapLocation.setOnMarkerClickListener(this);
        pd = new TransparentProgressDialog(this, R.drawable.spinner);
        r = new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        };

        myApiUrls = new ApiUrls();
        latlonURL = myApiUrls.getLatLongFromAddress;
        try
        {

            ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
            String url = myApiUrls.getHospitalOfficeDataByNpi;
            url= url+sharedPrefClassObj.getProviderNPIid();
//            url= url+1881696136; //1548211295
           listViewLoaderTask.execute(url);
        }
        catch (Exception exp)
        {
            //
        }

//        txtResultsLocationFragment = (TextView) findViewById(R.id.txtNoOfResultsLocationFragment);
//        txtNoOfResultLocationFragment = (TextView) findViewById(R.id.txtNoOfResultsLocationFragment);
//        listFragmentLocation =(ListView) findViewById(R.id.drawer_content_Fragment_location);
        urlValueLocationFragment = myApiUrls.getProviderInfoByNPI+sharedPrefClassObj.getProviderNPIid();
        userInputtedZip = sharedPrefClassObj.getProviderSearchLocation();

        callProvider = (Button) findViewById(R.id.ProviderCall);
        callProvider.setOnClickListener(this);

      }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ProviderCall:

                dialog = new Dialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.style.TransparentProgressDialog));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.custom_provider_phnumber_dialog);
                dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, Color.parseColor("#FFFFFF"));

                TxtphNumber=(TextView)dialog.findViewById(R.id.phNumber);

                TxtphNumber.setText(npiProviderPhNo);
                Log.d("npiProviderPhNo 5",npiProviderPhNo+" ");

                BtnSubmit = (Button) dialog.findViewById(R.id.BtnCalling);
                BtnCancel = (Button) dialog.findViewById(R.id.ButtonCancel);
                BtnSubmit.setOnClickListener(this);
                BtnCancel.setOnClickListener(this);

                dialog.show();

                break;

            case R.id.BtnCalling:
                dialog.hide();
                try
                {
                    Intent intentCalling = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + npiProviderPhNo));
                    startActivity(intentCalling);
                }
                catch (Exception exCalling)
                {
                    Toast.makeText(this,"Call application not found",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ButtonCancel:
                dialog.hide();
                break;

            case R.id.providerNavigation:
                npiProviderAddess = npiProviderAddess.replace(" ","+");
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", npiProviderAddess);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        try
        {
            float alfa = marker.getAlpha();
            final int i =  Float.valueOf(alfa).intValue();
            if(i==111)
            {
                alfaValue = 0;
            }
            else
            {
                alfaValue = i;
            }
            listFragmentLocation.post(new Runnable()
            {
                @Override
                public void run()
                {
                    //auto-Scroll the ListView position
                    listFragmentLocation.smoothScrollToPositionFromTop(alfaValue,alfaValue);

                    //programatically click on ListView row Item for corresponding marker Click
                    listFragmentLocation.performItemClick(listFragmentLocation.getAdapter().getView(alfaValue, null, null),alfaValue, listFragmentLocation.getItemIdAtPosition(alfaValue));
                }
            });
        }
        catch (Exception excepMarker)
        {

        }
        return false;
    }

    /**
     * AsyncTask to parse json data and load ListView
     */
    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>
    {

        JSONObject jObject;
        List<HashMap<String, Object>> npidata = null;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd.show();

        }
        // Doing the parsing of xml data in a non-ui thread
        @Override
        protected SimpleAdapter doInBackground(String... url)
        {
            try
            {
                String getJsonURL= url[0];
                Log.d("getJsonURL",getJsonURL+" ");
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();
                // Making a request to url and getting response
                String  strJson = sh.makeServiceCall(getJsonURL, ServiceHandler.GET);
                Log.d("strJson Location",strJson+" ");
                jObject = new JSONObject(strJson);

                // Getting the parsed data as a List construct
                npidata = parse(jObject);
                //Log.d("commonutil3-3","commonutil3-3");
            }
            catch (Exception e)
            {
                //Log.d("Exception",e.toString());
            }
            // Keys used in Hashmap
            String[] from = {"type", "name", "address", "phonenumber", "fax"};
            int[] to = {R.id.locationType, R.id.locationName, R.id.locationAddress, R.id.locationPhoneNo, R.id.locationFaxNo};
            SimpleAdapter adapter = new SimpleAdapter(ProviderLocationTab.this, npidata, R.layout.list_item_provideraddressdetails_location_fragment, from, to);

            return adapter;

        }

        /**
         * Invoked by the Android on "doInBackground" is executed
         */
        @Override
        protected void onPostExecute(SimpleAdapter adapter)
        {
            try
            {
                if (adapter.getCount() > 0)
                {
                    //show no of Search results at textview on Slider
                    txtNoOfResultLocationFragment.setText("  " + adapter.getCount() + " RESULTS");
                    // Setting adapter for the listview
                    listFragmentLocation.setAdapter(adapter);
                    for (int i = 0; i < adapter.getCount(); i++)
                    {
                        HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
                        hm.put("position", i);
                    }
                    listFragmentLocation.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> argo, View view, int i, long l)
                        {
                            try
                            {
                                BtnNavigation.setEnabled(true);
                                BtnCall.setEnabled(true);
                                TextView tvPhNo = (TextView) view.findViewById(R.id.locationPhoneNo);
                                npiProviderPhNo = tvPhNo.getText().toString();
                                TextView tvPhAddress = (TextView) view.findViewById(R.id.locationAddress);
                                npiProviderAddess = tvPhAddress.getText().toString();
                                zoomAddress(i);
                            }
                            catch (Exception excp)
                            {
                                //
                            }
                        }
                    });
                    plotToMap(); //will plot the provider on map
                }
                else
                {
                      txtNoOfResultLocationFragment.setText("  0 RESULTS");
                    // errorDialogShow();
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
                pd.dismiss();
        }

    }

    /* start #### */
    // Receives a JSONObject and returns a list
    public List<HashMap<String,Object>> parse(JSONObject jObject)
    {
        try
        {
            // Retrieves all the elements in the 'countries' array
            jArray = jObject.getJSONArray("provider_hospital_details");
        }
        catch (JSONException e)
        {
            //e.printStackTrace();
        }

        return getCountries(jArray);
    }

    private List<HashMap<String, Object>> getCountries(JSONArray jCountries)
    {
        List<HashMap<String, Object>> countryList = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> provider = null;

        try
        {

            for(int i=0; i < jCountries.length() ; i++)
            {
                // Call getCountry with country JSON object to parse the country
                provider = getCountry((JSONObject)jCountries.get(i));
                countryList.add(provider);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return countryList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCountry(JSONObject c)
    {
        HashMap<String, Object> provider = new HashMap<String, Object>();
        try
        {
            if (c.has("hospitalname"))
            {
                hospitalname = c.getString("hospitalname");
            }
            else
            {
                hospitalname = "Not available";
            }

            if (c.has("address"))
            {
                address = c.getString("address");
            }
            else
            {
                if (c.has("adr_ln_1"))
                {
                    address = c.getString("adr_ln_1");
                }
                else if (c.has("adr_ln_2"))
                {
                    address += c.getString("adr_ln_2");
                }
                else
                {
                    address = " ";
                }
            }


            if (c.has("city"))
            {
                city = c.getString("city");
            }
            else
            {
                if (c.has("cty"))
                {
                    city = c.getString("cty");
                }
                else
                {
                    city = " ";
                }
            }

            if (c.has("state"))
            {
                state = c.getString("state");
            }
            else
            {
                if (c.has("st"))
                {
                    state = c.getString("st");
                }
                else
                    state = " ";
            }

            if (c.has("zipcode"))
            {
                zipcode = c.getString("zipcode");
            }
            else
            {
                if (c.has("zip"))
                {
                    zipcode = c.getString("zip");
                }
                else
                    zipcode = " ";
            }
            if (c.has("countyname"))
            {
                countyname = c.getString("countyname");
            }
            else
            {
                countyname = " ";
            }

            if(c.has("phonenumber"))
            {
                phonenumber = c.getString("phonenumber");
            }
            else
            {
                phonenumber = "Not available";
            }
            if(c.has("fax"))
            {
                fax = c.getString("fax");
            }
            else
            {
                fax = "Not available";
            }
            if(c.has("hospitaltype"))
            {
                hospitaltype = c.getString("hospitaltype");
            }
            else
            {
                hospitaltype = "Not available";
            }
            if(c.has("hospitalowner"))
            {
                hospitalowner = c.getString("hospitalowner");
            }
            else
            {
                hospitalowner = " ";
            }
            if(c.has("type"))
            {
                type = c.getString("type");
            }
            else
            {
                type = " ";
            }

            getLatLongFromAddressURL = latlonURL + address+city+state+zipcode;
            getLatLongFromAddressURL= getLatLongFromAddressURL.replace(" ","+");
            getLatLongFromAddressURL= getLatLongFromAddressURL.replace("++","+");
            getLatLongFromAddressURL= getLatLongFromAddressURL.replace("+++","+");
            //Log.d("getLatLongFromAddressURL",getLatLongFromAddressURL+" ");
            if(!getLatLongFromAddressURL.isEmpty())
            {
                jsonStrServiceCall = shProvideraddress.makeServiceCall(getLatLongFromAddressURL, ServiceHandler.GET);

                jsObject = new JSONObject(jsonStrServiceCall);
                latlngArry = jsObject.getJSONArray("result");
                //Log.d("latlngArry", latlngArry + " ");
                jsonObject = latlngArry.getJSONObject(0);
                //Log.d("jsonObject",jsonObject+" ");
                if(jsonObject.getString("status").equals("OK"))
                {
                    providerLatitude = jsonObject.getDouble("latitude");
                    providerLongitude = jsonObject.getDouble("longitude");

                    latLongHashMap.put("latitude" + k, providerLatitude.toString());
                    latLongHashMap.put("longitude" + k, providerLongitude.toString());
                }
                else
                {
                    latLongHashMap.put("latitude" + k, null);
                    latLongHashMap.put("longitude" + k, null);
                }
                latLongHashMap.put("name" + k, hospitalname);
                latLongHashMap.put("address" + k, address);
                k++;
            }
            if(type.equals("hospital"))
            {
                provider.put("type", hospitaltype);
            }
            else if(type.equals("office"))
            {
                provider.put("type", "Office");
            }
            else
            {
                provider.put("type", "Not available");
            }

            provider.put("name", hospitalname);
            provider.put("address", address);
            provider.put("phonenumber", "Phone : "+phonenumber);
            provider.put("fax", "Fax : "+fax);

        }
        catch (JSONException e)
        {
            Log.d("crash","crash");
            e.printStackTrace();
        }
        return provider;
    }
/* End ### */

    public void plotToMap()
    {
        if (latLongHashMap.size() > 0)
        {
            for (int loop = 0; loop < latLongHashMap.size() / 4; loop++)
            {
                if (latLongHashMap.get("latitude" + loop).equals(null)
                        || Double.valueOf(latLongHashMap.get("longitude" + loop)).equals(null))
                {
                    //lat long not Found...
                }
                else
                {
                    providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + loop));
                    providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + loop));

                    if(loop == 0)
                    {
                        //pd.dismiss();
                        mapLocation.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(providerLatitude - 0.02, providerLongitude + 0.001), 8));
                    }
                    providerName = latLongHashMap.get("name" + loop);
                    providerAddress = latLongHashMap.get("address" + loop);

                    Marker marker = mapLocation.addMarker(new MarkerOptions()
                            .position(new LatLng(providerLatitude, providerLongitude))
                            .title(providerName)
                            .snippet(" " + providerAddress)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerplot)));

                    if(loop == 0)
                        marker.setAlpha(111);
                    else
                        marker.setAlpha(loop);

                    visibleMarkers.put(loop, marker);
                }
            }
        }
        else
        {
            ////errorDialogShow();
        }
    }
    public void zoomAddress(int RowId)
    {
        if (providerLatitude.equals(null) || providerLongitude.equals(null))
        {
            //lat long not Found...
        }
        else
        {
            // '111' is hard-coded value for last green marker object/value
            if(visibleMarkersGreen.size() > 0)
            {
                visibleMarkersGreen.get(111).remove();
            }
            if(visibleMarkers.size() > 0)
            {
                for(int m=0; m<visibleMarkers.size(); m++)
                {
                    visibleMarkers.get(m).setVisible(true); //black marker
                }
            }
            visibleMarkers.get(RowId).setVisible(false); //black marker

            providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + RowId));
            providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + RowId));

            mapLocation.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(providerLatitude - 0.002, providerLongitude + 0.001), 15)); //12 6ilo

            providerName = latLongHashMap.get("name" + RowId);
            providerAddress = latLongHashMap.get("address" + RowId);

            Marker marker = mapLocation.addMarker(new MarkerOptions()
                    .position(new LatLng(providerLatitude, providerLongitude))
                    .title(providerName)
                    .snippet(" " + providerAddress)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerplotgreen)));

            marker.showInfoWindow();
            // '111' is hard-coded value for last green marker object/value
            visibleMarkersGreen.put(111, marker);
            marker.showInfoWindow();
        }
    }
    private class TransparentProgressDialog extends Dialog
    {

        private ImageView iv;

        public TransparentProgressDialog(Context context, int resourceIdOfImage)
        {
            super(context, R.style.TransparentProgressDialog);
            WindowManager.LayoutParams wlmp = getWindow().getAttributes();
            wlmp.gravity = Gravity.CENTER_HORIZONTAL;
            getWindow().setAttributes(wlmp);
            setTitle(null);
            setCancelable(true);
            setOnCancelListener(null);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv = new ImageView(context);
            iv.setImageResource(resourceIdOfImage);
            layout.addView(iv, params);
            addContentView(layout, params);
        }

        @Override
        public void show()
        {
            super.show();
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(1000);
            iv.setAnimation(anim);
            iv.startAnimation(anim);
        }
    }
}