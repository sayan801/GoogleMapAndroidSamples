package com.amiyo.technicise.androidcustomprogress;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SecondActivity extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener
{
    SharedPreferenceClass sharedPrefClassObj;
    private final String TAG = "SecondActivity ";

    private App app;
    private ListView listView;
    public String jsonData;
    public String[] firstName;
    public String[] lastName;
    public String[] countryName;
    public String[] businessAddress;
    public String[] lat;
    public String[] lang;
    public ImageView view;

    /** * @param context * @param dipValue * @return  */
    public static int dip2px(Context context, float dipValue)
    {
        if (context == null) {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    GoogleMap map;

    HashMap<Integer, Marker> visibleMarkersGreen = new HashMap<Integer, Marker>();
    HashMap<Integer, Marker> visibleMarkers = new HashMap<Integer, Marker>();
    Map<String, String> latLongHashMap = new HashMap<String, String>();
    Map<Integer, Integer> markerDetailsBlack = new HashMap<Integer, Integer>();
    Map<Integer, Integer> markerDetailsGreen = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());

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

        app = (App) getApplication();
        sharedPrefClassObj = app.getDataLoadOnListView();

        jsonData = sharedPrefClassObj.getJSONData();
        lat = sharedPrefClassObj.getLat();
        lang = sharedPrefClassObj.getLang();

       listView = (ListView) findViewById(R.id.listView1);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapProviderSearchResult)).getMap();
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setPadding(0, 0, 0, 50); //Zoom in-Out Button will be Visible properly

        visibleMarkers.clear();
        visibleMarkersGreen.clear();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray jsonArray = jsonObject.getJSONArray("npidata");

            firstName = new String[jsonArray.length()];
            lastName = new String[jsonArray.length()];
            countryName = new String[jsonArray.length()];
            businessAddress = new String[jsonArray.length()];

            JSONObject jsonObject1;
            for( int i = 0; i < jsonArray.length(); i++ ) {
                jsonObject1 = jsonArray.getJSONObject(i);


                firstName[i] = jsonObject1.getString("Provider First Name");
                lastName[i] = jsonObject1.getString("Provider Last Name");
                countryName[i] = jsonObject1.getString("Provider Business Mailing Address Country Code");
                businessAddress[i] = jsonObject1.getString("Provider First Line Business Practice Location Address");
            }
        } catch (Exception error) {
            Log.e(TAG, error.toString());
            error.printStackTrace();
        }

        //data holder.
        DataHolder dataHolder = new DataHolder();
        dataHolder.firstName = firstName;
        dataHolder.lastName = lastName;
        dataHolder.businessAddress = businessAddress;
        dataHolder.countryName = countryName;
        dataHolder.lat = lat;
        dataHolder.lang = lang;

        listView.setAdapter(new MyAdapter(this, dataHolder));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), firstName[position]+lastName[position], Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

                default:

                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
