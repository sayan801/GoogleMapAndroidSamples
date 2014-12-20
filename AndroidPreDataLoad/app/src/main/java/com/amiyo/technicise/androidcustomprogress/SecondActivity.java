package com.amiyo.technicise.androidcustomprogress;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
    public String[] PostalCode;
    public String[] lat;
    public String[] lang;
    public String[] providerNpiID;

    public ImageView view;

    Double providerLatitude, providerLongitude;
    public String providerName,providerAddress,ProviderPostalCode,CountListRowNo, npiId;
    public TextView CountListItem;
    public int alfaValue=0, listItemPotion = 0;
    Map<Integer, Integer> markerDetailsBlack = new HashMap<Integer, Integer>();
    Map<Integer, Integer> markerDetailsGreen = new HashMap<Integer, Integer>();

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
    Button btnLoadMore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        CountListItem=(TextView)findViewById(R.id.TextNoOfResults);

        // LoadMore button
        btnLoadMore = new Button(this);
        btnLoadMore.setBackgroundColor(Color.parseColor("#54D66A"));
        btnLoadMore.setTextColor(Color.parseColor("#FFFFFF"));
        btnLoadMore.setText("Load more data");
        /** * Listening to Load More button click event* */
        btnLoadMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // Toast.makeText(getApplicationContext()," click load more ", Toast.LENGTH_SHORT).show();
                loadMoreData();
            }
        });
        listView.addFooterView(btnLoadMore);
        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray jsonArray = jsonObject.getJSONArray("npidata");

            firstName = new String[5];
            lastName = new String[5];
            countryName = new String[5];
            businessAddress = new String[5];
            PostalCode = new String[5];
            providerNpiID = new String[5];

            JSONObject jsonObject1;
            for( int i = 0; i < 5; i++ )
            {
                jsonObject1 = jsonArray.getJSONObject(i);

                firstName[i] = jsonObject1.getString("Provider First Name");
                lastName[i] = jsonObject1.getString("Provider Last Name");
                countryName[i] = jsonObject1.getString("Provider Business Mailing Address Country Code");
                businessAddress[i] = jsonObject1.getString("Provider First Line Business Practice Location Address");
                PostalCode[i] = jsonObject1.getString("Provider Business Mailing Address Postal Code");
                providerNpiID[i] = jsonObject1.getString("NPI");

            }
        } catch (Exception error)
        {
            Log.e(TAG, error.toString());
            error.printStackTrace();
        }

        //data holder.
        DataHolder dataHolder = new DataHolder();

        dataHolder.firstName        = firstName;
        dataHolder.lastName         = lastName;
        dataHolder.businessAddress  = businessAddress;
        dataHolder.countryName      = countryName;
        dataHolder.lat              = lat;
        dataHolder.lang             = lang;
        dataHolder.PostalCode       = PostalCode;
        dataHolder.providerNpiId    = providerNpiID;
/*
        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("npidata");
            map.clear();
            BitmapDescriptor IconMarkerplot = BitmapDescriptorFactory.fromResource(R.drawable.markerblack);
            BitmapDescriptor IconMarkerplotgreen = BitmapDescriptorFactory.fromResource(R.drawable.markerplotgreen);

            JSONObject jsonObject2;

            for (int i = 0; i < jsonArray.length(); i++)
            {
                providerLatitude = Double.valueOf(sharedPrefClassObj.getLat()[i]);
                providerLongitude = Double.valueOf(sharedPrefClassObj.getLang()[i]);

                jsonObject2 = jsonArray.getJSONObject(i);

                firstName[i] = jsonObject2.getString("Provider First Name");
                lastName[i] = jsonObject2.getString("Provider Last Name");
                businessAddress[i] = jsonObject2.getString("Provider First Line Business Practice Location Address");
                countryName[i] = jsonObject2.getString("Provider Business Mailing Address Country Code");
                PostalCode[i] = jsonObject2.getString("Provider Business Mailing Address Postal Code");

                providerNpiID[i] = jsonObject2.getString("NPI");


                providerName=String.valueOf(firstName[i] + " " + lastName[i]);
                providerAddress=String.valueOf(businessAddress[i]+" "+countryName[i]);
                ProviderPostalCode=String.valueOf(PostalCode[i]);
                npiId = String.valueOf(providerNpiID[i]);;

                Marker markerBlack = map.addMarker(new MarkerOptions()
                        .position(new LatLng(providerLatitude, providerLongitude))
                        .title(providerName+" "+npiId)
                        .snippet(" " + providerAddress)
                        .icon(IconMarkerplot));
                visibleMarkers.put(i, markerBlack);
                String mBlack = markerBlack.getId();
                mBlack = mBlack.replace("m","");
                final int blackMarker =  Integer.valueOf(mBlack);
                markerDetailsBlack.put(i, blackMarker);

                Marker markerGreen = map.addMarker(new MarkerOptions()
                        .position(new LatLng(providerLatitude, providerLongitude))
                        .title(providerName+" "+npiId)
                        .snippet(" " + providerAddress)
                        .icon(IconMarkerplotgreen));
                String mGreen = markerGreen.getId();
                mGreen = mGreen.replace("m","");
                final int greenMarker =  Integer.valueOf(mGreen);
                markerDetailsGreen.put(i, greenMarker);
                visibleMarkersGreen.put(i, markerGreen);
                visibleMarkersGreen.get(i).setVisible(false);

            }
        }
        catch (JSONException error)
        {
            Log.e(TAG, error.toString());
            error.printStackTrace();
        }

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 12));
*/
        listView.setAdapter(new MyAdapter(this, dataHolder));
int data = listView.getAdapter().getCount();
        CountListItem.setText(" "+(data-1)+" RESULTS");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, final int position, long id) {
                zoomAddress(position);
                final int rowItem = position;

                LinearLayout ln = (LinearLayout) view.findViewById(R.id.linearLayoutProviderDetails);
                ln.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), String.valueOf(providerNpiID[rowItem]) + " Details row > " + rowItem, Toast.LENGTH_SHORT).show();
                    }
                });
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
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.Markar_Icon:
                Toast.makeText(getApplicationContext(), "click marker", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onInfoWindowClick(Marker marker)
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
            Toast.makeText(getApplicationContext(), "NpiID > "+String.valueOf(providerNpiID[alfaValue]), Toast.LENGTH_SHORT).show();
        }
        catch (Exception excepMarker)  {  }

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
            // listItemPotion = alfaValue;
            //auto-Scroll the ListView position
            listView.smoothScrollToPositionFromTop(alfaValue,alfaValue);
            //programatically click on ListView row Item for corresponding marker Click
            listView.performItemClick(listView.getAdapter().getView(alfaValue, null, null),alfaValue, listView.getItemIdAtPosition(alfaValue));
            //zoomAddress(alfaValue);
        }
        catch (Exception excepMarker)  {  }
        // Event was handled by our code do not launch default behaviour.
        return true;
    }
    // Plot/View only green marker
    public  void  zoomAddress(int RowId)
    {
        try
        {
            // visible All black marker
            for (int p = 0; p < visibleMarkers.size(); p++)
                visibleMarkers.get(p).setVisible(true);
            // invisible All green black marker
            for (int q = 0; q < visibleMarkersGreen.size(); q++)
                visibleMarkersGreen.get(q).setVisible(false);
            // invisible only a particular black marker
            visibleMarkers.get(RowId).setVisible(false);
            // visible only a green marker
            visibleMarkersGreen.get(RowId).setVisible(true);
            //show marker Chata
            visibleMarkersGreen.get(RowId).showInfoWindow();
        }
        catch (Exception ex){ }
    }

    public void loadMoreData()
    {
        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray jsonArray = jsonObject.getJSONArray("npidata");

            firstName = new String[jsonArray.length()];
            lastName = new String[jsonArray.length()];
            countryName = new String[jsonArray.length()];
            businessAddress = new String[jsonArray.length()];
            PostalCode = new String[jsonArray.length()];
            providerNpiID = new String[jsonArray.length()];

            JSONObject jsonObject1;
            for( int i = 0; i < jsonArray.length(); i++ )
            {
                jsonObject1 = jsonArray.getJSONObject(i);

                firstName[i] = jsonObject1.getString("Provider First Name");
                lastName[i] = jsonObject1.getString("Provider Last Name");
                countryName[i] = jsonObject1.getString("Provider Business Mailing Address Country Code");
                businessAddress[i] = jsonObject1.getString("Provider First Line Business Practice Location Address");
                PostalCode[i] = jsonObject1.getString("Provider Business Mailing Address Postal Code");
                providerNpiID[i] = jsonObject1.getString("NPI");

            }
        } catch (Exception error)
        {
            Log.e(TAG, error.toString());
            error.printStackTrace();
        }

        //data holder.
        DataHolder dataHolder = new DataHolder();

        dataHolder.firstName        = firstName;
        dataHolder.lastName         = lastName;
        dataHolder.businessAddress  = businessAddress;
        dataHolder.countryName      = countryName;
        dataHolder.lat              = lat;
        dataHolder.lang             = lang;
        dataHolder.PostalCode       = PostalCode;
        dataHolder.providerNpiId    = providerNpiID;

        listView.setAdapter(new MyAdapter(this, dataHolder));
        int data = listView.getAdapter().getCount();
        CountListItem.setText(" "+(data-1)+" RESULTS");
    }
}