package com.app.mycuratioui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.*;
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
import java.util.Map;

public class MainActivity extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMarkerClickListener, AbsListView.OnScrollListener  {
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

    Map<String, String> latLongHashMap = new HashMap<String, String>();
    ServiceHandler shProvideraddress = new ServiceHandler();
    ////SharedPreferenceClass sharedPrefClassObj;

    // flag for Internet connection status
    Boolean isInternetPresent = false, choosenMarker =false;

    // Connection detector class
    ConnectionDetector cd;
    public Dialog custom_connection_dialog;
    //// public Button TurnAgain;
    GoogleMap map;
    protected Context context;
    public  ListView list;
    ////ApiUrls myApiUrls;

    int listviewNoOfRow , totalNoOfResult = 0, j=0, k=0, totalRow, choosePositon = -1, t, listItemPotion,  number = 1, loadMoreData = 0, lastChooseMarker, countProviderName = 0;
    // 'k' is just increase latLongHashMap position value 'n' times, j is increment value to 0-4 and after 4 its become again 0 and will continue
    Double providerLatitude = null, providerLongitude = null;
    ////DatabaseHandler db;

    private TransparentProgressDialog pd;
    private android.os.Handler h;
    private Runnable r;
    String latlonURL,EmergencyURL, emergencyTaxcode, specialityTaxcode, specialityURL, npiProvideraddress, npiProviderPhNo, gender, URL3, entity_type, ProviderNamePrefixText, ProviderFirstName, ProviderLastName,
            ProviderOrganizationName, ProviderFirstLineBusinessMailingAddress, ProviderBusinessMailingAddressCityName,
            ProviderBusinessMailingAddressStateName, ProviderBusinessMailingAddressFaxNumber,ProviderBusinessMailingAddressPostalCode,
            displayname, HealthcareProviderTaxonomyCode_1, txonomyUrl, distance, userInputtedZip, jsonStrServiceCall,
            providerAddress = null, providerName=null, npiIDOfProvider, nameOfProvider, addressOfProvider, phoneOfProvider,faxOfProvider, genderOfProvider,
            specialityOfProvider, urlStatus, zoomProviderAddress, zoomProviderName, getLatLongFromAddressURL, npiID, provider2ndaddress, getJsonURL, ProviderSecondLineBusinessMailingAddress;

    JSONObject jsObject, jsonObject;
    JSONArray jArray;
    JSONArray latlngArry;
    public Dialog custom_zip_error;
    public Button TryAgain, TurnAgain, BtnTryAgain;
    public TextView searchDescription, tvNoOfResults, mTextViewSpeciality, mTextViewName, mTextViewAddress, mTextViewNpiId;
    public ImageView listImageIcon;


    // All variables
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<HashMap<String, String>>();
    HashMap<Integer, Marker> visibleMarkers = new HashMap<Integer, Marker>();
    private ListView mListView;
    private boolean isloading = false;
    private MyAdapter adapter;
    private MyTask task;
    public TextView footer;

    private TextView header;
    JSONObject jObject;
    List<HashMap<String, Object>> npidata = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());

        ActionBarDynamic actionbardynamic = new ActionBarDynamic();
        String colorCode="#54D66A";
        actionbardynamic.DynamicActionBar(getActionBar(), colorCode);
        ActionBar ab = getActionBar();
        ab.setCustomView(R.layout.custom_actionbar_layout);

        TextView TvActionbarTitle=(TextView)findViewById(R.id.TvActionBarTitle);
        TvActionbarTitle.setText("PROVIDERS");


        myApiUrls = new ApiUrls();
        latlonURL = myApiUrls.getLatLongFromAddress.toString();
        distance = sharedPrefClassObj.getDistance().toString();
        userInputtedZip = sharedPrefClassObj.getProviderSearchLocation().toString();
        txonomyUrl = myApiUrls.getTaxonomyDetails.toString();
*/
        getJsonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderByPharmacyZipDistance/72401/5";
        latlonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/geocoding/getLatLongFromAddress/";
        searchDescription = (TextView)findViewById(R.id.searchDescription);
        tvNoOfResults = (TextView)findViewById(R.id.txtNoOfResults);
/*
        String providerSearchLocation = sharedPrefClassObj.getProviderSearchLocation();
        String providerSearchDistance = sharedPrefClassObj.getProviderSearchDistance();
        String providerTV; //provider text as search type of provider

        if (sharedPrefClassObj.getProviderSearchType().toString().equals("name"))
        {
            if (sharedPrefClassObj.getProviderNameSearchTerm().toString().equals("-1"))
            {
                providerTV = "all Providers";
            }
            else
            {
                providerTV = sharedPrefClassObj.getProviderNameSearchTerm();
            }
        }
        else if (sharedPrefClassObj.getProviderSearchType().toString().equals("hospital"))
        {

            providerTV = "hospitals";

        }
        else if (sharedPrefClassObj.getProviderSearchType().toString().equals("pharmacy"))
        {

            providerTV = "Pharmacy";

        }
        else if (sharedPrefClassObj.getProviderSearchType().toString().equals("emergency") || sharedPrefClassObj.getProviderSearchType().toString().equals("urgentcare"))
        {
            if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("emergency"))
            {
                providerTV = "Emergency Rooms";

            }
            else if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("urgentcare"))
            {

                providerTV = "Urgent Care";
            }
            else
            {
                //NoFilter
                providerTV = "Emergency and Urgent Care";
            }
        } else if (sharedPrefClassObj.getProviderSearchType().toString().equals("speciality"))
        {
            if (sharedPrefClassObj.getProviderNameSearchTerm().toString().equals("-1"))
            {
                providerTV = "all Speciality";
            }
            else
            {
                providerTV =  sharedPrefClassObj.getProviderNameSearchTerm();
            }

        }
        else
        {
            //Some thing is went to Wrong...However then it
            providerTV = "Provider";
        }

        searchDescription.setText(Html.fromHtml("Searching for <b>"+providerTV+"</b> Within <b>"+providerSearchDistance+ "</b> of <b>"+providerSearchLocation+"</b> :"));


        db = new DatabaseHandler(this);
        // creating connection detector class instance
        */
        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent)
        {

            h = new android.os.Handler();
            pd = new TransparentProgressDialog(this, R.drawable.spinner);
            r = new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            };

            try
            {
                map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                map.setOnMarkerClickListener(this);
                map.setPadding(0, 0, 0, 50); //Zoom in-Out Button will be Visisble properly
                map.clear();

/*                //ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();

                if (sharedPrefClassObj.getProviderSearchType().toString().equals("name"))
                {
                    String providerName = sharedPrefClassObj.getProviderNameSearchTerm().toString();
                    providerName = providerName.replace(" ", "+");
                    String urlValue = myApiUrls.getProviderInfoByPartialNameZipDistance.toString() + providerName + "/" + userInputtedZip + "/" + distance;
                    //listViewLoaderTask.execute(nameURL);
                    getJsonURL = urlValue;
                }
                else if (sharedPrefClassObj.getProviderSearchType().toString().equals("hospital"))
                {

                    String hopitalURL = myApiUrls.getProviderHospitalByZipDistance.toString();
                    String urlValue = hopitalURL + userInputtedZip + "/" + distance;
                    //listViewLoaderTask.execute(strUrlhospital);
                    getJsonURL = urlValue;

                }
                else if (sharedPrefClassObj.getProviderSearchType().toString().equals("pharmacy"))
                {
                    String pharmacyURL = myApiUrls.getProviderPharmacyByZipDistance.toString();
                    String urlValue = pharmacyURL + userInputtedZip + "/" + distance;
                    //listViewLoaderTask.execute(strUrlpharmacy);
                    getJsonURL = urlValue;

                }
                else if (sharedPrefClassObj.getProviderSearchType().toString().equals("emergency") || sharedPrefClassObj.getProviderSearchType().toString().equals("urgentcare"))
                {
                    if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("emergency"))
                    {
                        emergencyTaxcode = "261QE0002X";
                        EmergencyURL = myApiUrls.getProviderInfoByEmergencyUrgentCareZip.toString();
                        String urlValue = EmergencyURL + "/" + emergencyTaxcode + "/" + userInputtedZip + "/" + distance;
                        getJsonURL = urlValue;
                        //listViewLoaderTask.execute(urlValue);
                    }
                    else if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("urgentcare"))
                    {
                        EmergencyURL = myApiUrls.getProviderInfoByEmergencyUrgentCareZip.toString();
                        emergencyTaxcode = "261QU0200X";
                        String urlValue = EmergencyURL + "/" + emergencyTaxcode + "/" + userInputtedZip + "/" + distance;
                        //listViewLoaderTask.execute(urlValue);
                        getJsonURL = urlValue;
                    }
                    else
                    {
                        //NoFilter
                        EmergencyURL = myApiUrls.getProviderInfoByEmergencyUrgentCareZip.toString();
                        emergencyTaxcode = "-1";
                        String urlValue = EmergencyURL + "/" + emergencyTaxcode + "/" + userInputtedZip + "/" + distance;
                        //listViewLoaderTask.execute(urlValue);
                        getJsonURL = urlValue;

                    }
                } else //(sharedPrefClassObj.getProviderSearchType().toString().equals("speciality"))
                {
                    specialityTaxcode = sharedPrefClassObj.getProviderType().toString();
                    specialityURL = myApiUrls.getProviderInfoBySpecialityZipDistance.toString();
                    String urlValue = specialityURL + "/" + specialityTaxcode + "/" + userInputtedZip + "/" + distance;
                    //listViewLoaderTask.execute(urlValue);
                    getJsonURL = urlValue;
                }
*/
                //listViewLoaderTask.execute();
            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }

        }
        else
        {
            ShowConnectionStatus();
        }
        //header = (TextView) findViewById(R.id.header);
        mListView = (ListView) findViewById(R.id.drawer_content);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        footer = (TextView) inflater.inflate(R.layout.footer, null);
//        mListView.addFooterView(footer);

        // LoadMore button
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Show More Providers");
        btnLoadMore.setBackgroundColor(Color.parseColor("#54D66A"));
        btnLoadMore.setTextColor(Color.parseColor("#FFFFFF"));


        /**
         * Listening to Load More button click event
         * */

//        task = new MyTask();
//        task.execute();

        btnLoadMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(totalNoOfResult > adapter.getCount() )
                {
                    pd.show();

                    loadMoreData = adapter.getCount();
                    task = new MyTask();
                    task.execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No more Provider",Toast.LENGTH_SHORT).show();
                }
            }
        });


        mListView.addFooterView(btnLoadMore);

        adapter = new MyAdapter(this, R.layout.list_item_provideronmap);
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(this);

//        task = new MyTask();
//        task.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> argo, View view, int i, long l)
            {


                try
                {
                    listItemPotion = i;
                    addressOfProvider = ((TextView) view.findViewById(R.id.TxtProviderAddress)).getText().toString();
                    nameOfProvider = ((TextView) view.findViewById(R.id.TxtProviderName)).getText().toString();
                    zoomProviderAddress = addressOfProvider;
                    zoomProviderName = nameOfProvider;
                    new getProviderInfoByNPIid().execute(addressOfProvider);
                }catch (Exception excp)
                {
                    //
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                try
                {/*
                    npiIDOfProvider = ((TextView) view.findViewById(R.id.TxtProviderNPIid)).getText().toString();
                    nameOfProvider = ((TextView) view.findViewById(R.id.TxtProviderName)).getText().toString();
                    Intent intentDoctorDetails = new Intent(getApplicationContext(), DoctorDetails.class);
                    sharedPrefClassObj.setProviderName(nameOfProvider);
                    sharedPrefClassObj.setProviderNPIid(npiIDOfProvider);
                    startActivity(intentDoctorDetails);*/
                }
                catch (Exception e)
                {
                    //
                }
                return false;
            }
        });
    }


    @Override
    public boolean onMarkerClick(Marker marker)
    {
        // Show the info window
        marker.showInfoWindow();
        Log.d("marker.getTitle()",marker.getTitle()+" ");
        String[] separated =  marker.getTitle().split(" .");
        String data = separated[0];
//        data = data.replace("(","");
//        data = data.replace(")","");
        Log.d("data",data+ " ");
        final String position = data;

        mListView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mListView.smoothScrollToPositionFromTop(Integer.parseInt(position)-1,Integer.parseInt(position)-1);
            }
        });

        // Event was handled by our code do not launch default behaviour.
        return true;
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
        public void show() {
            super.show();
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(1000);
            iv.setAnimation(anim);
            iv.startAnimation(anim);
        }
    }


    public void ShowConnectionStatus()

    {
        custom_connection_dialog = new Dialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        custom_connection_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.style.TransparentProgressDialog));
        custom_connection_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_connection_dialog.setCancelable(false);
        custom_connection_dialog.setContentView(R.layout.custom_error_connection);
        custom_connection_dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, Color.parseColor("#FFFFFF"));

        TurnAgain = (Button) custom_connection_dialog.findViewById(R.id.ButtonTryAgain);
        TurnAgain.setOnClickListener(this);
        custom_connection_dialog.show();

    }

    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.ButtonTryAgain:
                custom_connection_dialog.dismiss();
                break;

            case R.id.ButtonEmailTryAgain:
                Intent doctorActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(doctorActivityIntent);
                custom_zip_error.dismiss();
                break;
        }
    }

    private class markProviderLocationOnMap extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... arg0)
        {
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.d("markProviderLocationOnMap latLongHashMap.size()", String.valueOf(latLongHashMap.size()));
            //execute Map Plotting
            if (latLongHashMap.size() > 0)
            {
                MarkerOptions mp = new MarkerOptions();
                mp.flat(true);
                boolean markerPlot =false;
                for (int loop = 0; loop < latLongHashMap.size() / 4; loop++)
                {
                    if (providerLatitude.equals(null) || providerLongitude.equals(null))
                    {
                        //lat long not Found...
                    }
                    else
                    {
                        providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + loop));
                        providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + loop));

                        if(markerPlot == false)
                        {
                            pd.dismiss();
                            //(Math.abs(marker.getPosition().latitude - latLng.latitude)
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 12));
                            markerPlot = true;
                        }
                        providerName = latLongHashMap.get("name" + loop);
                        providerAddress = latLongHashMap.get("address" + loop);

                        mp.position(new LatLng(providerLatitude, providerLongitude));
                        //if (tmp == 5) tmp = 0;
                        //mp.icon(BitmapDescriptorFactory.fromResource(map_icon[0]));
                        mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.markerplot));
                        mp.title(" "+providerName);
                        mp.snippet(providerAddress+" ");
                        //visibleMarkers.put(loop, mp);
                        map.addMarker(mp);
                    }
                }
            }
            else
            {
                ////errorDialogShow();
            }
        }

    }

    void errorDialogShow()
    {
        custom_zip_error = new Dialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        custom_zip_error.getWindow().setBackgroundDrawable(new ColorDrawable(R.style.TransparentProgressDialog));
        custom_zip_error.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_zip_error.setCancelable(false);
        custom_zip_error.setContentView(R.layout.custom_error_zip_dialog);
        custom_zip_error.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, Color.parseColor("#FFFFFF"));
        BtnTryAgain = (Button) custom_zip_error.findViewById(R.id.ButtonEmailTryAgain);
        BtnTryAgain.setOnClickListener(this);
        if(pd.isShowing())
            pd.dismiss();
        custom_zip_error.show();
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

        totalNoOfResult = jCountries.length();

        if (loadMoreData >1 )
        {
            try
            {
                //Log.d("loadMoreData", String.valueOf(loadMoreData));
                //Log.d("totalNoOfResult", String.valueOf(loadMoreData));

                for(int i=loadMoreData; i < loadMoreData+5 ; i++)
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

        }
        else
        {
            try
            {
                for(int i=0; i < listviewNoOfRow; i++)
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

        }
        if(jCountries.length() > 10)
        {
            listviewNoOfRow = 10;
        }
        else
        {
            listviewNoOfRow = jCountries.length();
        }

        return countryList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCountry(JSONObject c)
    {

        HashMap<String, Object> provider = new HashMap<String, Object>();
        try {
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

//            sharedPrefClassObj.setProviderBusinessAddress(ProviderFirstLineBusinessMailingAddress);
//            sharedPrefClassObj.setProviderPracticeAddress(provider2ndaddress);

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
            npiProvideraddress = npiProvideraddress.replace("++", "+");
            npiProvideraddress = npiProvideraddress.replace("+++", "+");

            Log.d("totalNoOfResult", String.valueOf(totalNoOfResult));

            if(totalNoOfResult >0 || totalNoOfResult<5)
            {
                //Log.d("value of k ghur6e", String.valueOf(k));
                getLatLongFromAddressURL = latlonURL + npiProvideraddress;

                jsonStrServiceCall = shProvideraddress.makeServiceCall(getLatLongFromAddressURL, ServiceHandler.GET);

                jsObject = new JSONObject(jsonStrServiceCall);
                latlngArry = jsObject.getJSONArray("result");

                jsonObject = latlngArry.getJSONObject(0);
                providerLatitude = jsonObject.getDouble("latitude");
                providerLongitude = jsonObject.getDouble("longitude");

                latLongHashMap.put("latitude" + k, providerLatitude.toString());
                latLongHashMap.put("longitude" + k, providerLongitude.toString());
                latLongHashMap.put("name" + k, providerName);
                latLongHashMap.put("address" + k, npiProvideraddress.replace("+", " "));
                k++;
                Log.d("providerLatitude", String.valueOf(providerLatitude));
            }

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

        }
        catch (JSONException e)
        {
            // e.printStackTrace();
        }
        return provider;
    }
/* End ### */

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


    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        int loadedItems = firstVisibleItem + visibleItemCount;
        if((loadedItems == totalItemCount) && !isloading)
        {
            if(task != null && (task.getStatus() == AsyncTask.Status.FINISHED))
            {
//                task = new MyTask();
//                task.execute();
            }
        }
    }


    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        //
    }

    class MyTask extends AsyncTask<Void, Void, Void>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();

            if(pd.isShowing())
                pd.show();
            else
                pd.show();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String  strJson = sh.makeServiceCall(getJsonURL, ServiceHandler.GET);
            //Log.d("strJson getJsonURL >",strJson+" ");
            try
            {
                jObject = new JSONObject(strJson);
                npidata = parse(jObject);
Log.d(" npidata = parse(jObject)", "npidata = parse(jObject)");
            }
            catch (Exception ex)
            {
                //
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (totalNoOfResult > 0)
            {
                //new markProviderLocationOnMap().execute();
                markProviderLocationOnMap1();
            }
            else
            {
                if (pd.isShowing())
                    pd.dismiss();
                errorDialogShow();
            }

            tvNoOfResults.setText("     TOTAL RESULTS "+totalNoOfResult);
            adapter.notifyDataSetChanged();
            isloading = false;


        }
    }
    //execute a Asyntask plot Particular provider on Map
    private class getProviderInfoByNPIid extends AsyncTask<String, Void, Void>
    {
        JSONObject jObject;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            if(pd.isShowing())
                pd.show();
            else
                pd.show();
        }

        @Override
        protected Void doInBackground(String... arg0)
        {
            npiProvideraddress = arg0[0];
            try
            {
                getLatLongFromAddressURL = latlonURL + npiProvideraddress;
                jsonStrServiceCall = shProvideraddress.makeServiceCall(getLatLongFromAddressURL, ServiceHandler.GET);
                jsObject = new JSONObject(jsonStrServiceCall);
                latlngArry = jsObject.getJSONArray("result");


                jsonObject = latlngArry.getJSONObject(0);
                urlStatus = jsonObject.getString("status");

                if(urlStatus.equals("OK"))
                {
                    providerLatitude = jsonObject.getDouble("latitude");
                    providerLongitude = jsonObject.getDouble("longitude");
                }
                else
                {
                    providerLatitude = null ;
                    providerLongitude = null;
                }

            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            try
            {
                MarkerOptions mp = new MarkerOptions();

                if(pd.isShowing())
                    pd.dismiss();



            }
            catch (Exception excep)
            {
                //
            }
            plotAprovier();
        }
    }
    public void markProviderLocationOnMap1()
    {
        if (latLongHashMap.size() > 0)
        {
            Marker mp = null;// = new MarkerOptions();
            //mp.flat(true);
            boolean markerPlot =false;
            for (int loop = 0; loop < latLongHashMap.size() / 4; loop++)
            {
                if (providerLatitude.equals(null) || providerLongitude.equals(null))
                {
                    //lat long not Found...
                }
                else
                {
                    providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + loop));
                    providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + loop));

                    if(markerPlot == false)
                    {
                        pd.dismiss();
                        //(Math.abs(marker.getPosition().latitude - latLng.latitude)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 12));
                        markerPlot = true;
                    }
                    providerName = latLongHashMap.get("name" + loop);
                    providerAddress = latLongHashMap.get("address" + loop);

//                    mp.position(new LatLng(providerLatitude, providerLongitude));
//                    //if (tmp == 5) tmp = 0;
//                    //mp.icon(BitmapDescriptorFactory.fromResource(map_icon[0]));
//                    mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.markerplot));
//                    mp.title(" "+providerName);
//                    mp.snippet(providerAddress+" ");
//
//                    Log.d("visibleMarkers object key", String.valueOf(visibleMarkers.get(loop)));
//                    map.addMarker(mp);

                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(providerLatitude, providerLongitude))
                            .title(countProviderName+" . " + providerName)
                            .snippet(" " + providerAddress)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.markerplot)));
                    countProviderName++;
                    visibleMarkers.put(loop, marker);
                    Log.d("visibleMarkers object key", String.valueOf(visibleMarkers.get(loop)));
                }
            }
        }
        else
        {
            ////errorDialogShow();
        }
    }
    public  void  plotAprovier()
    {
        if(providerLatitude != null && providerLongitude != null)
        {

            visibleMarkers.get(listItemPotion).setVisible(false);
            lastChooseMarker = listItemPotion;

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(providerLatitude - 0.004, providerLongitude + 0.001), 15));
            //delete the existing marker with this Lat. Long
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(providerLatitude, providerLongitude))
                    .title(" "+nameOfProvider)
                    .snippet(" "+zoomProviderAddress)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.markerplotgreen)));

            marker.showInfoWindow();
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "This Address Not Available", Toast.LENGTH_SHORT).show();
        }
    }
    public void showProvider(View view)
    {
        getJsonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderByPharmacyZipDistance/72401/5";
        task = new MyTask();
        task.execute();

        task = new MyTask();
        task.execute();

        //I can't understand why execute Asynctask Two times..although its working properly
    }
}
