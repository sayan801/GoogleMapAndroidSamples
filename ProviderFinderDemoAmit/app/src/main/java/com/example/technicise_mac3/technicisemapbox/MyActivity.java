package com.example.technicise_mac3.technicisemapbox;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.events.MapListener;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.GpsLocationProvider;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.views.MapController;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.MapViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyActivity extends Activity implements View.OnClickListener
{

    ///mapbox variables
    MapView mv;
    MapController mc;
    MapController mcZoom;
    private String currentMap = null;
    private UserLocationOverlay myLocationOverlay;
    public Marker myposmarker;

    public HashMap<Integer, Marker> greenmarkers = new HashMap<Integer, Marker>();
    public HashMap<Integer, Marker> blackmarkers  = new HashMap<Integer, Marker>();

    public TransparentProgressDialog pd;

    /**
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue)
    {
        if (context == null) {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    ServiceHandler shProvideraddress = new ServiceHandler();

    // flag for Internet connection status
    Boolean isInternetPresent = false, isloading = false, showAllProvider = false;
    // Connection detector class

    public Dialog custom_connection_dialog, custom_zip_error, custom_show_all_providers;
    //// public Button TurnAgain;

    protected Context context;
    public  ListView list;

    int listviewNoOfRow , markerPositionBlack = 0,markerPositionGreen =0, totalNoOfResult = 0, k=0, alfaValue = 0, listItemPotion = 0,  number = 1, loadMoreData = 0;
    // 'k' is just increase latLongHashMap position value 'n' times, j is increment value to 0-4 and after 4 its become again 0 and will continue
    Double providerLatitude = null, providerLongitude = null;
ApiUrls myApiUrls;


    private android.os.Handler h;
    private Runnable r;
    String latlonURL,EmergencyURL, emergencyTaxcode, specialityTaxcode, specialityURL, npiProvideraddress, npiProviderPhNo, gender, entity_type, ProviderNamePrefixText, ProviderFirstName, ProviderLastName,
            ProviderOrganizationName, ProviderFirstLineBusinessMailingAddress, ProviderBusinessMailingAddressCityName, ProviderBusinessMailingAddressStateName, ProviderBusinessMailingAddressFaxNumber,
            ProviderBusinessMailingAddressPostalCode, displayname, HealthcareProviderTaxonomyCode_1, txonomyUrl, distance, userInputtedZip, jsonStrServiceCall, providerAddress = null, providerName=null,
            npiIDOfProvider, nameOfProvider, addressOfProvider, zoomProviderAddress, zoomProviderName, getLatLongFromAddressURL, npiID, provider2ndaddress, getJsonURL, ProviderSecondLineBusinessMailingAddress;

    JSONObject jsObject, jsonObject;
    JSONArray jArray, latlngArry;

    public Button TryAgain, TurnAgain, BtnTryAgain, btnLoadMore, btnShowAllProviders;
    public TextView searchDescription, searchDescriptionAll, tvNoOfResults, mTextViewSpeciality, mTextViewName, mTextViewAddress, mTextViewNpiId;

    List<HashMap<String, Object>> npidata = null;
    HashMap<Integer, Marker> visibleMarkersGreen = new HashMap<Integer, Marker>();
    HashMap<Integer, Marker> visibleMarkers = new HashMap<Integer, Marker>();
    Map<String, String> latLongHashMap = new HashMap<String, String>();
    Map<Integer, Integer> markerDetailsBlack = new HashMap<Integer, Integer>();
    Map<Integer, Integer> markerDetailsGreen = new HashMap<Integer, Integer>();
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<HashMap<String, String>>();

    ListView mListView;
    MyAdapter adapter;
    //private MyTask task;
    JSONObject jObject;
    String providerSearchLocation,  providerNameString, fullTextSearchLocation; //provider text as search type of provider

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        pd = new TransparentProgressDialog(this, R.drawable.spinner);
pd.show();
        //mapbox related objects
        mv = (MapView) findViewById(R.id.mapview);
        mc = new MapController(mv);
        mcZoom= new MapController(mv);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setMapViewListener(new MapViewListener() {
            @Override
            public void onShowMarker(MapView mapView, Marker marker) {
                Toast.makeText(getApplicationContext(),"lol",Toast.LENGTH_LONG);
            }

            @Override
            public void onHidemarker(MapView mapView, Marker marker) {
                Toast.makeText(getApplicationContext(),"lol",Toast.LENGTH_LONG);
            }

            @Override
            public void onTapMarker(MapView mapView, Marker marker) {
                Toast.makeText(getApplicationContext(),"lol",Toast.LENGTH_LONG);
            }

            @Override
            public void onLongPressMarker(MapView mapView, Marker marker) {
                Toast.makeText(getApplicationContext(),"lol",Toast.LENGTH_LONG);
            }

            @Override
            public void onTapMap(MapView mapView, ILatLng iLatLng) {
                Toast.makeText(getApplicationContext(),"lol",Toast.LENGTH_LONG);
            }

            @Override
            public void onLongPressMap(MapView mapView, ILatLng iLatLng) {
                Toast.makeText(getApplicationContext(),"lol",Toast.LENGTH_LONG);
            }
        });

        myApiUrls= new ApiUrls();
        latlonURL = myApiUrls.getLatLongFromAddress.toString();
        // currentMap = "amit007.jnedbdll";
        //brunosan.map-cyglrrfu

        // Adds an icon that shows location
        myLocationOverlay = new UserLocationOverlay(new GpsLocationProvider(this), mv);
        myLocationOverlay.setDrawAccuracyEnabled(true);

        getJsonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderInfoByPartialNameZipDistance/davis/66213/5";

        mListView = (ListView) findViewById(R.id.drawer_content);
        adapter = new MyAdapter(this, R.layout.list_item_provideronmap);
        new MyTask().execute(getJsonURL);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> argo, View view, int i, long l) {

                Marker tempMarkerBlack = blackmarkers.get(i);
                mv.removeMarker(tempMarkerBlack);


                Marker tempMarkerGreen = greenmarkers.get(i);
                mv.addMarker(tempMarkerGreen);
                LatLng locationGreen = tempMarkerGreen.getPoint();
                mv.setZoom(15);
                mc.animateTo(new LatLng(locationGreen.getLatitude(), locationGreen.getLongitude()), true);


//                String providerClassification=((TextView)view.findViewById(R.id.provider)).getText().toString();
//                String taxonomyCode=((TextView)view.findViewById(R.id.tvTaxonomyCode)).getText().toString();
//                sharedPrefClassObj.setProviderNameSearchTerm(providerClassification);
//                sharedPrefClassObj.setProviderType(taxonomyCode);
//                goToLocationActivity();

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    /** Async Task that send a request to url * */
    class MyAdapter extends ArrayAdapter<String>
    {
        LayoutInflater inflater;

        public MyAdapter(Context context, int textViewResourceId)
        {
            super(context, textViewResourceId);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
            return mArrayList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = inflater.inflate(R.layout.list_item_provideronmap, null);

            mTextViewSpeciality = (TextView) convertView.findViewById(R.id.TxtSpeciality);
            mTextViewName = (TextView) convertView.findViewById(R.id.TxtProviderName);
            mTextViewAddress = (TextView) convertView.findViewById(R.id.TxtProviderAddress);
            mTextViewNpiId = (TextView) convertView.findViewById(R.id.TxtProviderNPIid);

            mTextViewSpeciality.setText(mArrayList.get(position).get("speciality" + position));
            mTextViewName.setText(mArrayList.get(position).get("name" + position));
            mTextViewAddress.setText(mArrayList.get(position).get("address"+position));
            mTextViewNpiId.setText(mArrayList.get(position).get("npiid"+position));

            return convertView;
        }
    }
    private class MyTask extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... arg0)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            Log.d("getJsonURL sh",getJsonURL+"");
            // Making a request to url and getting response
            String  strJson = sh.makeServiceCall(arg0[0], ServiceHandler.GET);

            try
            {
                JSONObject jObject = new JSONObject(strJson);
                npidata = parse(jObject);
            }
            catch (Exception ex)
            {
                //
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            mListView.setAdapter(adapter);
            markProviderLocationOnMapUsingMapBox();
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
            jArray = jObject.getJSONArray("npidata");
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
        //default first time load max. 10 result
        try
        {
            for (int i = 0; i < jCountries.length(); i++)
            {
                // Call getCountry with country JSON object to parse the country
                provider = getCountry((JSONObject) jCountries.get(i));
                countryList.add(provider);
            }
        }
        catch (Exception e) { }
        return countryList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCountry(JSONObject c)
    {
        Log.d("getCountry",c+"");
        HashMap<String, Object> provider = new HashMap<String, Object>();
        try
        {
            if(c.has("NPI"))
            {
                npiID = c.getString("NPI");
            }
            else
            {
                npiID = "0000000000";
            }
            if(c.has("Entity Type Code"))
            {
                entity_type = c.getString("Entity Type Code");
            }
            else
            {
                entity_type = "";
            }

            if(entity_type.equals("1"))
            {
                if(c.has("Provider Name Prefix Text"))
                {
                    ProviderNamePrefixText = c.getString("Provider Name Prefix Text");
                }
                else
                {
                    ProviderNamePrefixText = "";
                }
                if(c.has("Provider First Name"))
                {
                    ProviderFirstName = c.getString("Provider First Name");
                }
                else
                {
                    ProviderFirstName = "";
                }

                if(c.has("Provider Last Name"))
                {
                    ProviderLastName = c.getString("Provider Last Name");
                }
                else
                {
                    ProviderLastName = "";
                }
                providerName = ProviderNamePrefixText+" "+ProviderFirstName+" "+ProviderLastName;
            }
            else if(entity_type.equals("2"))
            {
                if(c.has("Provider Organization Name"))
                {
                    ProviderOrganizationName = c.getString("Provider Organization Name");
                    npiProviderPhNo = c.getString("Provider Business Mailing Address Telephone Number");
                }
                else
                {
                    ProviderOrganizationName = "";
                }
                providerName = ProviderOrganizationName;
            }
            else
            {
                providerName="Unknown Name";
            }

            if (c.has("Provider First Line Business Practice Location Address"))
            {
                ProviderFirstLineBusinessMailingAddress = c.getString("Provider First Line Business Practice Location Address");
            }
            else
            {
                ProviderFirstLineBusinessMailingAddress = " ";
            }

            if (c.has("Provider Second First Line Business Practice Location Address"))
            {
                ProviderSecondLineBusinessMailingAddress = c.getString("Provider Second First Line Business Practice Location Address");
            }
            else
            {
                ProviderSecondLineBusinessMailingAddress = " ";
            }


            if (c.has("Provider Business Practice Location Address City Name"))
            {
                ProviderBusinessMailingAddressCityName = c.getString("Provider Business Practice Location Address City Name");
            }
            else
            {
                ProviderBusinessMailingAddressCityName = " ";
            }

            if (c.has("Provider Business Practice Location Address State Name"))
            {
                ProviderBusinessMailingAddressStateName = c.getString("Provider Business Practice Location Address State Name");
            }
            else
            {
                ProviderBusinessMailingAddressStateName = " ";
            }

            if (c.has("Provider Business Practice Location Address Postal Code"))
            {
                ProviderBusinessMailingAddressPostalCode = c.getString("Provider Business Practice Location Address Postal Code");
            }
            else
            {
                ProviderBusinessMailingAddressPostalCode = " ";
            }
            if (c.has("Provider Business Practice Location Address Fax Number"))
            {
                ProviderBusinessMailingAddressFaxNumber = c.getString("Provider Business Practice Location Address Fax Number");
            }
            else
            {
                ProviderBusinessMailingAddressFaxNumber = " ";
            }

            if(c.has("Provider Business Practice Location Address Telephone Number"))
            {
                npiProviderPhNo = c.getString("Provider Business Practice Location Address Telephone Number");
            }
            else
            {
                npiProviderPhNo = "not found";
            }

            //take first 5 digit of provider Zip code
            ProviderBusinessMailingAddressPostalCode = ProviderBusinessMailingAddressPostalCode.substring(0, Math.min(ProviderBusinessMailingAddressPostalCode.length(), 5));

            //sharedPrefClassObj.setProviderBusinessAddress(ProviderFirstLineBusinessMailingAddress);
            //sharedPrefClassObj.setProviderPracticeAddress(provider2ndaddress);

            npiProvideraddress =
                    ProviderFirstLineBusinessMailingAddress + ", " +
                            ProviderSecondLineBusinessMailingAddress  + ", " +
                            ProviderBusinessMailingAddressCityName + ", " +
                            ProviderBusinessMailingAddressStateName +", "+
                            ProviderBusinessMailingAddressPostalCode;

            if(c.has("Provider Gender Code"))
            {
                gender = c.getString("Provider Gender Code");
            }
            else
            {
                gender = "not found";
            }

            if(c.has("Healthcare Provider Taxonomy Code_1"))
            {
                HealthcareProviderTaxonomyCode_1 = c.getString("Healthcare Provider Taxonomy Code_1");
            }
            else
            {
                displayname = "Speciality not found";
            }

            npiProvideraddress = npiProvideraddress.replace(" ", "+");
            npiProvideraddress = npiProvideraddress.replace("?", "");   //'Disallowed Key Characters.'
            npiProvideraddress = npiProvideraddress.replace("#", "");

            if(npiProvideraddress.isEmpty() || npiProvideraddress.equals(null) || npiProvideraddress == null)
                npiProvideraddress="addressnotfound";
               // Log.d("working till here","yes");
            getLatLongFromAddressURL = latlonURL + npiProvideraddress;
          //  Log.d("working till here",getLatLongFromAddressURL+"");
            jsonStrServiceCall = shProvideraddress.makeServiceCall(getLatLongFromAddressURL, ServiceHandler.GET);

            jsObject = new JSONObject(jsonStrServiceCall);
            latlngArry = jsObject.getJSONArray("result");

            jsonObject = latlngArry.getJSONObject(0);
           // Log.d("working till here","2");
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
          //  Log.d("working till here","3");
           // Log.d("npiID",npiID+"");
            latLongHashMap.put("npi_id" + k, npiID);
            latLongHashMap.put("name" + k, providerName);
            latLongHashMap.put("address" + k, npiProvideraddress.replace("+", " "));
            k++;
            //Log.d("working till here","4");
//            if(!HealthcareProviderTaxonomyCode_1.isEmpty())
//                displayname = db.getTaxonomy(HealthcareProviderTaxonomyCode_1);
//            else
                displayname ="Speciality not found";

            HashMap<String, String> providerData = new HashMap<String, String>();

            providerData.put("speciality"+(number-1), displayname);
            providerData.put("name"+(number-1), providerName + " " );
            providerData.put("address"+(number-1), npiProvideraddress );
            providerData.put("npiid"+(number-1), npiID );
            mArrayList.add(providerData);
            number += 1;
           // Log.d("working till here","5");
        }
        catch (JSONException e)
        {
            // e.printStackTrace();
        }
        return provider;
    }
/* End ### */

    public void markProviderLocationOnMapUsingMapBox()
    {
        Log.d("markProviderLocationOnMapUsingMapBox","markProviderLocationOnMapUsingMapBox");
        //BitmapDescriptor IconMarkerplot = BitmapDescriptorFactory.fromResource(R.drawable.markerblack);
        //BitmapDescriptor IconMarkerplotgreen = BitmapDescriptorFactory.fromResource(R.drawable.markerplotgreen);
        Drawable blackMarkerIcon = getResources().getDrawable( R.drawable.markerblack );
        Drawable greenMarkerIcon = getResources().getDrawable( R.drawable.markerplotgreen );
        for (int loop = 0; loop < latLongHashMap.size() / 5; loop++)
        {
            Log.d("loop",loop+1+"");
            providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + loop));
            providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + loop));
            providerName = latLongHashMap.get("name" + loop);
            providerAddress = latLongHashMap.get("address" + loop);


//            Marker markerGreen = map.addMarker(new MarkerOptions()
//                    .position(new LatLng(providerLatitude, providerLongitude))
//                    .title(providerName)
//                    .snippet(" " + providerAddress)
//                    .icon(IconMarkerplotgreen));

            Marker mBlack = new Marker(mv, "hi", "hello", new LatLng(providerLatitude,providerLongitude));
            mBlack.setMarker(blackMarkerIcon);
            mBlack.setTitle(providerName);
            mBlack.setDescription(providerAddress);
            mv.addMarker(mBlack);

            Marker mGreen = new Marker(mv, "hi", "hello", new LatLng(providerLatitude,providerLongitude));
            mGreen.setMarker(greenMarkerIcon);
            mGreen.setTitle(providerName);
            mGreen.setDescription(providerAddress);

            greenmarkers.put(loop, mGreen);
            blackmarkers.put(loop,mBlack);
        }
        mv.setZoom(10);
        mc.animateTo(new LatLng(providerLatitude, providerLongitude),true);

    }

    public class TransparentProgressDialog extends Dialog
    {
        private ImageView iv;
        public TransparentProgressDialog(Context context, int resourceIdOfImage)
        {
            super(context, R.style.TransparentProgressDialog);
            WindowManager.LayoutParams wlmp = getWindow().getAttributes();
            wlmp.gravity = Gravity.CENTER_HORIZONTAL;
            getWindow().setAttributes(wlmp);
            setTitle(null);
            setCancelable(false);
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
