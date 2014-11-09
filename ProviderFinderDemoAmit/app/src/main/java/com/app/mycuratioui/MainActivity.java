package com.app.mycuratioui;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import android.view.View.OnClickListener;

public class MainActivity extends FragmentActivity implements OnClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener
{
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
    SharedPreferenceClass sharedPrefClassObj;
    // flag for Internet connection status
    Boolean isInternetPresent = false, isloading = false, showAllProvider = false;
    // Connection detector class
    ConnectionDetector cd;
    public Dialog custom_connection_dialog, custom_zip_error, custom_show_all_providers;
    //// public Button TurnAgain;
    GoogleMap map;
    protected Context context;
    public  ListView list;
    ApiUrls myApiUrls;
    int listviewNoOfRow , markerPositionBlack = 0,markerPositionGreen =0, totalNoOfResult = 0, k=0, alfaValue = 0, listItemPotion = 0,  number = 1, loadMoreData = 0;
    // 'k' is just increase latLongHashMap position value 'n' times, j is increment value to 0-4 and after 4 its become again 0 and will continue
    Double providerLatitude = null, providerLongitude = null;
//    DatabaseHandler db;

    private TransparentProgressDialog pd;
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

    private ListView mListView;
    private MyAdapter adapter;
    private MyTask task;
    JSONObject jObject;
    String providerSearchLocation,  providerNameString, fullTextSearchLocation; //provider text as search type of provider

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());
        searchDescription = (TextView)findViewById(R.id.searchDescription);
        searchDescription.setOnClickListener(this);

        searchDescriptionAll = (TextView)findViewById(R.id.searchDescriptionALL);
        searchDescriptionAll.setOnClickListener(this);

        tvNoOfResults = (TextView)findViewById(R.id.txtNoOfResults);

        String providerSearchDistance = sharedPrefClassObj.getProviderSearchDistance();

/*

        if (sharedPrefClassObj.getProviderSearchType().toString().equals("name"))
        {
            if (sharedPrefClassObj.getProviderNameSearchTerm().toString().equals("-1"))
            {
                providerNameString = "all Providers";
            }
            else
            {
                providerNameString = sharedPrefClassObj.getProviderNameSearchTerm();
            }
        }
        else if (sharedPrefClassObj.getProviderSearchType().toString().equals("hospital"))
        {
            providerNameString = "hospitals";
        }
        else if (sharedPrefClassObj.getProviderSearchType().toString().equals("pharmacy"))
        {
            providerNameString = "Pharmacy";
        }
        else if (sharedPrefClassObj.getProviderSearchType().toString().equals("emergency") || sharedPrefClassObj.getProviderSearchType().toString().equals("urgentcare"))
        {
            if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("emergency"))
            {
                providerNameString = "Emergency Rooms";
            }
            else if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("urgentcare"))
            {
                providerNameString = "Urgent Care";
            }
            else
            {
                //NoFilter
                providerNameString = "Emergency and Urgent Care";
            }
        } else if (sharedPrefClassObj.getProviderSearchType().toString().equals("speciality"))
        {
            if (sharedPrefClassObj.getProviderNameSearchTerm().toString().equals("-1"))
            {
                providerNameString = "all Speciality";
            }
            else
            {
                providerNameString =  sharedPrefClassObj.getProviderNameSearchTerm();
            }
        }
        else
        {
            //Some thing is went to Wrong...However then it
            providerNameString = "Provider";
        }

        if(sharedPrefClassObj.getProviderLocationSearchType() != null)
        {
            if(sharedPrefClassObj.getProviderLocationSearchType().equals("cityName"))
            {
                providerSearchLocation = sharedPrefClassObj.getProviderChosenCityName();
            }
            else if(sharedPrefClassObj.getProviderSearchLocation() != null)
            {
                providerSearchLocation = sharedPrefClassObj.getProviderSearchLocation();
            }
            else
            {
                providerSearchLocation= " ";
            }

            fullTextSearchLocation = providerSearchLocation;

            String[] DescriptionLocationText = providerSearchLocation.split(" ");
            if(DescriptionLocationText.length > 0)
            {
                providerSearchLocation = DescriptionLocationText[0];
            }
            if(DescriptionLocationText.length > 1)
            {
                providerSearchLocation = DescriptionLocationText[0] + " " + DescriptionLocationText[1];
            }
            if(DescriptionLocationText.length > 2)
            {
                providerSearchLocation = DescriptionLocationText[0] + " " + DescriptionLocationText[1] + " " + DescriptionLocationText[2] + "...";
            }
            if(DescriptionLocationText.length > 3)
            {
                searchDescription.setEnabled(true);
                searchDescriptionAll.setText(Html.fromHtml("Searching for <font color='#54D66A'>"+providerNameString+"</font> Within <font color='#54D66A'>"+
                        providerSearchDistance+ "</font> of <font color='#54D66A'>"+fullTextSearchLocation+"</font>?"));
            }

        }

        String fullTextProviderName = providerNameString;
        String[] DescriptionName = providerNameString.split(" ");
        if(DescriptionName.length > 0)
        {
            providerNameString = DescriptionName[0];
        }
        if(DescriptionName.length > 1)
        {
            providerNameString = DescriptionName[0] + " " + DescriptionName[1];
        }
        if(DescriptionName.length > 2)
        {
            providerNameString = DescriptionName[0] + " " + DescriptionName[1] + " " + DescriptionName[2] + "...";
        }
        if(DescriptionName.length > 3)
        {
            searchDescription.setEnabled(true);
            searchDescriptionAll.setText(Html.fromHtml("Searching for <font color='#54D66A'>"+fullTextProviderName+"</font> Within <font color='#54D66A'>"+
                    providerSearchDistance+ "</font> of <font color='#54D66A'>"+fullTextSearchLocation+"</font>?"));
        }

        searchDescription.setText(Html.fromHtml("Searching for <font color='#54D66A'>"+providerNameString+"</font> Within <font color='#54D66A'>"+
                providerSearchDistance+ "</font> of <font color='#54D66A'>"+providerSearchLocation+"</font>?"));

        //searchDescription.setText(Html.fromHtml(sharedPrefClassObj.getProviderFinderCompleteSearchString()+":"));
        db = new DatabaseHandler(this); */
        getJsonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderByPharmacyZipDistance/72401/5";
        latlonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/geocoding/getLatLongFromAddress/";
        searchDescription = (TextView)findViewById(R.id.searchDescription);
        tvNoOfResults = (TextView)findViewById(R.id.txtNoOfResults);

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
                map.setOnInfoWindowClickListener(this);
                map.setPadding(0, 0, 0, 50); //Zoom in-Out Button will be Visible properly
                latLongHashMap.clear();
                visibleMarkers.clear();
                visibleMarkersGreen.clear();
/*
                if (sharedPrefClassObj.getProviderSearchType().toString().equals("name"))
                {
                    String providerName = sharedPrefClassObj.getProviderNameSearchTerm().toString();
                    providerName = providerName.trim();

                    if (providerName.contains(" "))
                    {
                        String[] pData = providerName.split(" ");
                        String urlValue = myApiUrls.getProviderInfoByFullNameZipDistance.toString() + pData[0]+"/"+ pData[1]+ "/" + userInputtedZip + "/" + distance;
                        getJsonURL = urlValue;
                    }
                    else
                    {
                        providerName = providerName.replace(" ", "+");
                        String urlValue = myApiUrls.getProviderInfoByPartialNameZipDistance.toString() + providerName + "/" + userInputtedZip + "/" + distance;
                        getJsonURL = urlValue;
                    }
                }
                else if (sharedPrefClassObj.getProviderSearchType().toString().equals("hospital"))
                {
                    String hopitalURL = myApiUrls.getProviderHospitalByZipDistance.toString();
                    String urlValue = hopitalURL + userInputtedZip + "/" + distance;
                    getJsonURL = urlValue;
                }
                else if (sharedPrefClassObj.getProviderSearchType().toString().equals("pharmacy"))
                {
                    String pharmacyURL = myApiUrls.getProviderPharmacyByZipDistance.toString();
                    String urlValue = pharmacyURL + userInputtedZip + "/" + distance;
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
                    }
                    else if (sharedPrefClassObj.getEmergencyorUrgentcare().toString().equals("urgentcare"))
                    {
                        EmergencyURL = myApiUrls.getProviderInfoByEmergencyUrgentCareZip.toString();
                        emergencyTaxcode = "261QU0200X";
                        String urlValue = EmergencyURL + "/" + emergencyTaxcode + "/" + userInputtedZip + "/" + distance;
                        getJsonURL = urlValue;
                    }
                    else
                    {
                        //NoFilter
                        EmergencyURL = myApiUrls.getProviderInfoByEmergencyUrgentCareZip.toString();
                        emergencyTaxcode = "-1";
                        String urlValue = EmergencyURL + "/" + emergencyTaxcode + "/" + userInputtedZip + "/" + distance;
                        getJsonURL = urlValue;
                    }
                } else //(sharedPrefClassObj.getProviderSearchType().toString().equals("speciality"))
                {
                    specialityTaxcode = sharedPrefClassObj.getProviderType().toString();
                    specialityURL = myApiUrls.getProviderInfoBySpecialityZipDistance.toString();
                    String urlValue = specialityURL + "/" + specialityTaxcode + "/" + userInputtedZip + "/" + distance;
                    getJsonURL = urlValue;
                }*/
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
        mListView = (ListView) findViewById(R.id.drawer_content);
        //btnShowAllProviders button
        btnShowAllProviders = new Button(this);
        btnShowAllProviders.setBackgroundColor(Color.parseColor("#54D66A"));
        btnShowAllProviders.setText("Load all Providers");
        btnShowAllProviders.setTextColor(Color.parseColor("#FFFFFF"));

        // LoadMore button
        btnLoadMore = new Button(this);
        btnLoadMore.setBackgroundColor(Color.parseColor("#54D66A"));
        btnLoadMore.setTextColor(Color.parseColor("#FFFFFF"));

        task = new MyTask();
        task.execute();
        /** * Listening to Load More button click event* */
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
            }
        });

        /** * Listening to Load All Provider click event* */
        btnShowAllProviders.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(totalNoOfResult > adapter.getCount() )
                    showAllProviderConfirmationDialog();
            }
        });
        mListView.addFooterView(btnLoadMore);
        mListView.addFooterView(btnShowAllProviders);

        adapter = new MyAdapter(this, R.layout.list_item_provideronmap);
        mListView.setAdapter(adapter);

        task = new MyTask();
        task.execute();
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        try
        {
            marker.showInfoWindow();
            String m = marker.getId();
            m = m.replace("m","");
            final int i =  Integer.valueOf(m);
            alfaValue = i ; //assign value

            for(int k = 0; k<markerDetailsBlack.size(); k++)
            {
                if(alfaValue == markerDetailsBlack.get(k))
                {
                    alfaValue = k;
                    break;
                }

                if(alfaValue == markerDetailsGreen.get(k))
                {
                    alfaValue = k;
                    break;
                }
            }
            listItemPotion = alfaValue;
            //auto-Scroll the ListView position
            mListView.smoothScrollToPositionFromTop(alfaValue,alfaValue);
            //programatically click on ListView row Item for corresponding marker Click
            mListView.performItemClick(mListView.getAdapter().getView(alfaValue, null, null),alfaValue, mListView.getItemIdAtPosition(alfaValue));
            zoomAddress(alfaValue);
        }
        catch (Exception excepMarker)
        {
            //
        }
        // Event was handled by our code do not launch default behaviour.
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        try
        {
            String NpiId = latLongHashMap.get("npi_id" + listItemPotion);
            Intent intentDoctorDetails = new Intent(getApplicationContext(), Delete.class);
//            sharedPrefClassObj.setProviderPreviewToDetails("ProviderSearchResultToDetails");
            sharedPrefClassObj.setProviderName(latLongHashMap.get("name" + listItemPotion));
            sharedPrefClassObj.setProviderNPIid(NpiId);
            startActivity(intentDoctorDetails);
        }
        catch (Exception ex){ }
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
                custom_zip_error.dismiss();
                //////////////////////////////////////////////////////////////super.onBackPressed();
                break;

            case R.id.ButtonShowAllProviders:
                showAllProvider = true;
                pd.show();

                loadMoreData = adapter.getCount();
                task = new MyTask();
                task.execute();

                custom_show_all_providers.dismiss();
                break;

            case R.id.ButtonShowAllProviderCancel:
                custom_show_all_providers.dismiss();
                break;

            case R.id.Markar_Icon:
                try
                {
                    String rowNo = String.valueOf(view.getTag());
                    listItemPotion = Integer.parseInt(rowNo);
                    zoomAddress(listItemPotion);
                    mListView.performItemClick(mListView.getAdapter().getView(listItemPotion, null, null), listItemPotion, mListView.getItemIdAtPosition(listItemPotion));
                }
                catch (Exception exMarkerClick){ }
                break;

            case R.id.customCheckableLinearLayout:
                try
                {
                    String rowNo = String.valueOf(view.getTag());
                    listItemPotion = Integer.parseInt(rowNo);
                    zoomAddress(listItemPotion);
                    mListView.performItemClick(mListView.getAdapter().getView(listItemPotion, null, null), listItemPotion, mListView.getItemIdAtPosition(listItemPotion));
                }
                catch (Exception exMarkerClick2){ }
                break;

            case R.id.linearLayoutProviderDetails:
                try
                {
                    String rowNo3 = String.valueOf(view.getTag());
                    int itemNo3 = Integer.parseInt(rowNo3);
                    String NpiId = latLongHashMap.get("npi_id" + itemNo3);
                    Intent intentDoctorDetails = new Intent(getApplicationContext(), Delete.class);
                    //sharedPrefClassObj.setProviderPreviewToDetails("ProviderSearchResultToDetails");
                    sharedPrefClassObj.setProviderName(latLongHashMap.get("name" + itemNo3));
                    sharedPrefClassObj.setProviderNPIid(NpiId);
                    startActivity(intentDoctorDetails);
                }
                catch (Exception exDetailsClick){ }
                break;

            case R.id.searchDescription:
                searchDescription.setVisibility(View.GONE);
                searchDescriptionAll.setVisibility(View.VISIBLE);
                break;

            case R.id.searchDescriptionALL:
                searchDescription.setVisibility(View.VISIBLE);
                searchDescriptionAll.setVisibility(View.GONE);
                break;
        }
    }

    public void errorDialogShow()
    {
        custom_zip_error = new Dialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        custom_zip_error.getWindow().setBackgroundDrawable(new ColorDrawable(R.style.TransparentProgressDialog));
        custom_zip_error.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_zip_error.setCancelable(true);
        custom_zip_error.setContentView(R.layout.custom_error_zip_dialog);
        custom_zip_error.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, Color.parseColor("#FFFFFF"));
        BtnTryAgain = (Button) custom_zip_error.findViewById(R.id.ButtonEmailTryAgain);
        BtnTryAgain.setOnClickListener(this);

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
        if (showAllProvider == true)
        {
            try
            {
                //will display show All Provider
                for(int i=loadMoreData; i < totalNoOfResult ; i++)
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
        else if (loadMoreData >1 )
        {
            try
            {
                //will display next more '10' provider
                for(int i=loadMoreData; i < loadMoreData+10 ; i++)
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
                //default first time load max. 10 result
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

            sharedPrefClassObj.setProviderBusinessAddress(ProviderFirstLineBusinessMailingAddress);
            sharedPrefClassObj.setProviderPracticeAddress(provider2ndaddress);

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

            getLatLongFromAddressURL = latlonURL + npiProvideraddress;
            jsonStrServiceCall = shProvideraddress.makeServiceCall(getLatLongFromAddressURL, ServiceHandler.GET);

            jsObject = new JSONObject(jsonStrServiceCall);
            latlngArry = jsObject.getJSONArray("result");

            jsonObject = latlngArry.getJSONObject(0);

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
            latLongHashMap.put("npi_id" + k, npiID);
            latLongHashMap.put("name" + k, providerName);
            latLongHashMap.put("address" + k, npiProvideraddress.replace("+", " "));
            k++;

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

            View leftPart =  convertView.findViewById(R.id.Markar_Icon);
            leftPart.setTag(position);
            leftPart.setOnClickListener(MainActivity.this);

            View left =  convertView.findViewById(R.id.customCheckableLinearLayout);
            left.setTag(position);
            left.setOnClickListener(MainActivity.this);

            View rightPart =  convertView.findViewById(R.id.linearLayoutProviderDetails);
            rightPart.setTag(position);
            rightPart.setOnClickListener(MainActivity.this);

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

    class MyTask extends AsyncTask<Void, Void, Void>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();

//            if(pd.isShowing())
//                pd.show();
//            else
//                pd.show();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String  strJson = sh.makeServiceCall(getJsonURL, ServiceHandler.GET);
            try
            {
                jObject = new JSONObject(strJson);
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
            try
            {
                if (totalNoOfResult > 0)
                {
                    markProviderLocationOnMap1();
                }
                else
                {
                    if (pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    errorDialogShow();
                }

                tvNoOfResults.setText("  "+adapter.getCount()+" / "+totalNoOfResult+"  RESULTS ");
                adapter.notifyDataSetChanged();
                isloading = false;

                if(totalNoOfResult > adapter.getCount() )
                {
                    btnLoadMore.setText("Show More Providers");
                }
                else
                {
                    btnLoadMore.setText("End Of Results");
                    mListView.removeFooterView(btnShowAllProviders);
                }
            }
            catch (Exception ex){ }
        }
    }
    // Plot/View only green marker
    public  void  zoomAddress(int RowId)
    {
        try
        {
            for(int p=0; p<visibleMarkers.size(); p++)
                visibleMarkers.get(p).setVisible(true);

            for(int q=0; q<visibleMarkersGreen.size(); q++)
                visibleMarkersGreen.get(q).setVisible(false);

            visibleMarkers.get(RowId).setVisible(false);
            visibleMarkersGreen.get(RowId).setVisible(true);
            visibleMarkersGreen.get(RowId).showInfoWindow();

            providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + RowId));
            providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + RowId));

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 12));
        }
        catch (Exception ex){ }
    }
    public void markProviderLocationOnMap1()
    {
        BitmapDescriptor IconMarkerplot = BitmapDescriptorFactory.fromResource(R.drawable.markerplot);
        BitmapDescriptor IconMarkerplotgreen = BitmapDescriptorFactory.fromResource(R.drawable.markerplotgreen);
        map.clear();

        try
        {
            if (latLongHashMap.size() > 0)
            {
                markerPositionBlack = 0;
                markerPositionGreen = 0;
                for (int loop = 0; loop < latLongHashMap.size() / 5; loop++)
                {
                    if (latLongHashMap.get("latitude" + loop) == null
                            || Double.valueOf(latLongHashMap.get("longitude" + loop)) == null)
                    {
                        //lat long not Found...
                    }
                    else
                    {
                        providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + loop));
                        providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + loop));

                        if(loop == 0)
                        {
                            pd.dismiss();
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 10));
                        }
                        providerName = latLongHashMap.get("name" + loop);
                        providerAddress = latLongHashMap.get("address" + loop);

                        Marker markerBlack = map.addMarker(new MarkerOptions()
                                .position(new LatLng(providerLatitude, providerLongitude))
                                .title(providerName)
                                .snippet(" " + providerAddress)
                                .icon(IconMarkerplot));
                        visibleMarkers.put(loop, markerBlack);
                        String mBlack = markerBlack.getId();
                        mBlack = mBlack.replace("m","");
                        final int blackMarker =  Integer.valueOf(mBlack);
                        markerDetailsBlack.put(markerPositionBlack, blackMarker);
                        markerPositionBlack++;

                        Marker markerGreen = map.addMarker(new MarkerOptions()
                                .position(new LatLng(providerLatitude, providerLongitude))
                                .title(providerName)
                                .snippet(" " + providerAddress)
                                .icon(IconMarkerplotgreen));
                        String mGreen = markerGreen.getId();
                        mGreen = mGreen.replace("m","");
                        final int greenMarker =  Integer.valueOf(mGreen);
                        markerDetailsGreen.put(markerPositionGreen, greenMarker);
                        markerPositionGreen++;
                        visibleMarkersGreen.put(loop, markerGreen);
                        visibleMarkersGreen.get(loop).setVisible(false);
                    }
                }
            }
            else
            {
                ////errorDialogShow();
            }
        }
        catch (Exception ex){ }
    }
    void showAllProviderConfirmationDialog()
    {
        custom_show_all_providers = new Dialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        custom_show_all_providers.getWindow().setBackgroundDrawable(new ColorDrawable(R.style.TransparentProgressDialog));
        custom_show_all_providers.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_show_all_providers.setCancelable(false);
        custom_show_all_providers.setContentView(R.layout.custom_show_all_provider_confirmation_dialog);
        custom_show_all_providers.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, Color.parseColor("#FFFFFF"));

        Button BtnShowAllProviders = (Button) custom_show_all_providers.findViewById(R.id.ButtonShowAllProviders);
        BtnShowAllProviders.setOnClickListener(this);

        Button BtnShowAllProviderCancel = (Button) custom_show_all_providers.findViewById(R.id.ButtonShowAllProviderCancel);
        BtnShowAllProviderCancel.setOnClickListener(this);
        custom_show_all_providers.show();
    }
    public void showProvider(View view)
    {
        getJsonURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderByPharmacyZipDistance/72401/5";
        //getJsonURL= "http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderInfoByEmergencyUrgentCareZip/-1/16601/10";
        task = new MyTask();
        task.execute();

        task = new MyTask();
        task.execute();

        //I can't understand why execute Asynctask Two times..although its working properly
    }
}
// Bamanga6i Lat: 22.74925 long: 88.5122154
