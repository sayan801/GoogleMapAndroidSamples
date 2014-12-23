package com.amiyo.technicise.androidcustomprogress;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class SecondActivity extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener
{
    SharedPreferenceClass sharedPrefClassObj;
    String TAG = "SecondActivity ";
    String latLongURL = "http://curatehealth.net:81/webservice/sayan801/code/index.php?/geocoding/getLatLongFromAddress/";
    public App app;
    public ListView listView;
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
    public String providerName, providerAddress, ProviderPostalCode, npiId;
    public TextView CountListItem;
    Map<Integer, Integer> markerDetailsBlack = new HashMap<Integer, Integer>();
    Map<Integer, Integer> markerDetailsGreen = new HashMap<Integer, Integer>();

    /** * @param context * @param dipValue * @return  */
    public static int dip2px(Context context, float dipValue)
    {
        if (context == null)
        {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    GoogleMap map;
    HashMap<Integer, Marker> visibleMarkersGreen = new HashMap<Integer, Marker>();
    HashMap<Integer, Marker> visibleMarkers = new HashMap<Integer, Marker>();
    Map<String, String> latLongHashMap = new HashMap<String, String>();
    Button btnLoadMore, btnShowAllProviders;

    int totalResultCounted = 0, showingResult = 0, numberKey = 0, alfaValue = 0;
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
        TvActionbarTitle.setText("PROVIDER");
        TvActionbarTitle.setTextColor(Color.parseColor("#FFFFFF"));

        app = (App) getApplication();
        sharedPrefClassObj = app.getDataLoadOnListView();
        jsonData = sharedPrefClassObj.getJSONData();
        lat = sharedPrefClassObj.getLat();
        lang = sharedPrefClassObj.getLang();

        listView = (ListView) findViewById(R.id.listViewProviderSearchResults);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapProviderSearchResult)).getMap();
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setPadding(0, 0, 0, 50); //Zoom in-Out Button will be Visible properly

        visibleMarkers.clear();
        visibleMarkersGreen.clear();

        CountListItem = (TextView) findViewById(R.id.TextNoOfResults);

        //btnShowAllProviders button
        btnShowAllProviders = new Button(this);
        btnShowAllProviders.setBackgroundColor(Color.parseColor("#54D66A"));
        btnShowAllProviders.setText("Load all Providers");
        btnShowAllProviders.setTextColor(Color.parseColor("#FFFFFF"));

        /** * Listening to Load All Provider click event* */
        btnShowAllProviders.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                //Toast.makeText(getApplicationContext(), " show All Provider.. ", Toast.LENGTH_SHORT).show();
                new loadMoreProviderAsyncTask().execute("showAllProviders");

            }
        });

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

                if(showingResult < totalResultCounted)
                    new loadMoreProviderAsyncTask().execute("loadMoreProvider");
                else
                    btnLoadMore.setText("End of Providers");
            }
        });
        listView.addFooterView(btnLoadMore);
        listView.addFooterView(btnShowAllProviders);
        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray jsonArray = jsonObject.getJSONArray("npidata");
            totalResultCounted = jsonArray.length();

            int showFirstValue = 10;
            if(jsonArray.length() < 10)
                showFirstValue = jsonArray.length();
            else
                showFirstValue = 10;

            firstName = new String[showFirstValue];
            lastName = new String[showFirstValue];
            countryName = new String[showFirstValue];
            businessAddress = new String[showFirstValue];
            PostalCode = new String[showFirstValue];
            providerNpiID = new String[showFirstValue];

            JSONObject jsonObject1;
            for( int i = 0; i < showFirstValue; i++ )
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

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("npidata");
            map.clear();
            latLongHashMap.clear();
            BitmapDescriptor IconMarkerplot = BitmapDescriptorFactory.fromResource(R.drawable.markerblack);
            BitmapDescriptor IconMarkerplotgreen = BitmapDescriptorFactory.fromResource(R.drawable.markerplotgreen);
            JSONObject jsonObject2;
            int firstLoadDataAndPlotMarker = 10;
            if(jsonArray.length() < 10)
            {
                firstLoadDataAndPlotMarker = jsonArray.length();
            }
            else
            {
                firstLoadDataAndPlotMarker = 10;
            }
            showingResult = firstLoadDataAndPlotMarker;
            for (int i = 0; i < firstLoadDataAndPlotMarker; i++)
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
                npiId = String.valueOf(providerNpiID[i]);

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

                latLongHashMap.put("latitude" + numberKey, providerLatitude.toString());
                latLongHashMap.put("longitude" + numberKey, providerLongitude.toString());
                latLongHashMap.put("name" + numberKey, providerName+"");
                latLongHashMap.put("address" + numberKey, providerAddress+"");
                numberKey += 1;
            }
        }
        catch (Exception error)  {  }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 8));

        listView.setAdapter(new MyAdapter(this, dataHolder));
        CountListItem.setText(" "+showingResult+" / "+totalResultCounted+" RESULTS");

        if(showingResult >= totalResultCounted)
        {
            listView.removeFooterView(btnShowAllProviders);
            btnLoadMore.setText("End of Providers");
        }
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
            case R.id.linearLayoutProviderDetails:
                int rowNo = Integer.parseInt(String.valueOf(view.getTag()));
                Toast.makeText(getApplicationContext(), "goto next screen "+rowNo, Toast.LENGTH_SHORT).show();
                break;

            case R.id.Markar_Icon:
                try
                {
                    int rowNo1 = Integer.parseInt(String.valueOf(view.getTag()));
                    zoomAddress(rowNo1);
                    listView.performItemClick(listView.getAdapter().getView(rowNo1, null, null), rowNo1, listView.getItemIdAtPosition(rowNo1));
                }
                catch (Exception exMarkerClick){ }
                break;

            case R.id.customCheckableLinearLayout:
                try
                {
                    int rowNo2 = Integer.parseInt(String.valueOf(view.getTag()));
                    zoomAddress(rowNo2);
                    listView.performItemClick(listView.getAdapter().getView(rowNo2, null, null), rowNo2, listView.getItemIdAtPosition(rowNo2));
                }
                catch (Exception exMarkerClick){ }
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
            zoomAddress(alfaValue);
        }
        catch (Exception excepMarker)  {  }
        // Event was handled by our code do not launch default behaviour.
        return true;
    }
    /* ******** Plot/View only green marker ******** */
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

            providerLatitude = Double.valueOf(latLongHashMap.get("latitude" + RowId));
            providerLongitude = Double.valueOf(latLongHashMap.get("longitude" + RowId));

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 12));
        }
        catch (Exception ex){ }
    }

    /* ***** loadMoreProviderAsyncTask ******* */
    private class loadMoreProviderAsyncTask extends AsyncTask<String , Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... url)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject.getJSONArray("npidata");
                int incrementValue, incrementWith = 10;

                if(url[0] == "showAllProviders")
                {
                    incrementWith = totalResultCounted; // will display ALL providers
                    //listView.removeFooterView(btnShowAllProviders);
                }

                if((showingResult+incrementWith) > totalResultCounted)
                {
                    int remainingResult = (showingResult+incrementWith) - totalResultCounted;
                    incrementValue = (showingResult+incrementWith) - remainingResult;
                }
                else
                {
                    incrementValue = (showingResult+incrementWith);
                }

                firstName = new String[incrementValue];
                lastName = new String[incrementValue];
                countryName = new String[incrementValue];
                businessAddress = new String[incrementValue];
                PostalCode = new String[incrementValue];
                providerNpiID = new String[incrementValue];

                JSONObject jsonObject1;
                for( int i = 0; i < incrementValue; i++ )
                {
                    jsonObject1 = jsonArray.getJSONObject(i);

                    firstName[i] = jsonObject1.getString("Provider First Name")+"positopn > "+i;
                    lastName[i] = jsonObject1.getString("Provider Last Name");
                    countryName[i] = jsonObject1.getString("Provider Business Mailing Address Country Code");
                    businessAddress[i] = jsonObject1.getString("Provider First Line Business Practice Location Address");
                    PostalCode[i] = jsonObject1.getString("Provider Business Mailing Address Postal Code");
                    providerNpiID[i] = jsonObject1.getString("NPI");

                    //take first 5 digit of provider Zip code

                    if(i >= showingResult)
                    {
                        String pin = PostalCode[i].substring(0, Math.min(PostalCode[i].length(), 5));

                        String address = countryName[i] + "+" + businessAddress[i] + "+" + pin;
                        address = address.replace(" ","+");
                        address = address.replace(" ","+");
                        Log.d("address final", address + " > "+numberKey);

                        latLongHashMap.put("name" + numberKey, firstName[i]+" "+lastName[i]);
                        latLongHashMap.put("address" + numberKey, address+"");

                        loadMoreProviderLatLong(address);
                    }
                }
                showingResult = incrementValue;
            }
            catch (Exception error)  {  }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
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

            listView.setAdapter(new MyAdapter(getApplicationContext(), dataHolder));
            CountListItem.setText(" "+showingResult+" / "+totalResultCounted+" RESULTS");

            if(showingResult >= totalResultCounted)
            {
                listView.removeFooterView(btnShowAllProviders);
                btnLoadMore.setText("End of Providers");
            }

            rePlotMarker();

        }
    }

    /* ***** rePlotMarker ******* */
    public void rePlotMarker()
    {
        try
        {
            BitmapDescriptor IconMarkerplot = BitmapDescriptorFactory.fromResource(R.drawable.markerblack);
            BitmapDescriptor IconMarkerplotgreen = BitmapDescriptorFactory.fromResource(R.drawable.markerplotgreen);
            map.clear();

            if (latLongHashMap.size() > 0)
            {
                Log.d("latLongHashMap rePlotMarker data",latLongHashMap.toString()+"");
                int markerPositionBlack = 0, markerPositionGreen = 0;
                for (int loop = 0; loop < latLongHashMap.size() / 4; loop++)
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
//                            pd.dismiss();
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(providerLatitude - 0.03, providerLongitude + 0.001), 8));
                        }

                        Marker markerBlack = map.addMarker(new MarkerOptions()
                                .position(new LatLng(providerLatitude, providerLongitude))
                                .title(String.valueOf(latLongHashMap.get("name" + loop))+" position > "+loop)
                                .snippet(" "+String.valueOf(latLongHashMap.get("address" + loop))+" position > "+loop)
                                .icon(IconMarkerplot));
                        visibleMarkers.put(loop, markerBlack);
                        String mBlack = markerBlack.getId();
                        mBlack = mBlack.replace("m","");
                        final int blackMarker =  Integer.valueOf(mBlack);
                        markerDetailsBlack.put(markerPositionBlack, blackMarker);
                        markerPositionBlack++;

                        Marker markerGreen = map.addMarker(new MarkerOptions()
                                .position(new LatLng(providerLatitude, providerLongitude))
                                .title(String.valueOf(latLongHashMap.get("name" + loop))+" position > "+loop)
                                .snippet(" "+String.valueOf(latLongHashMap.get("address" + loop))+" position > "+loop)
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
                // LayoutProgress.setVisibility(View.GONE);
            }
            else
            {
                ////errorDialogShow();
            }


        }
        catch (Exception ex){ }
    }
    /* **** loadMoreProviderLatLong **** */
   public void loadMoreProviderLatLong(String address)
    {
        // Creating service handler class instance
        ServiceHandler shCheckZipAsyncTask = new ServiceHandler();
        // Making a request to url and getting response
        String jsonStr = shCheckZipAsyncTask.makeServiceCall(latLongURL+address, ServiceHandler.GET);
        Log.d("jsonStr data ",jsonStr);
        try
        {
            JSONObject jsObject = new JSONObject(jsonStr);
            JSONArray latlngArry = jsObject.getJSONArray("result");

            JSONObject jsonObject = latlngArry.getJSONObject(0);

            if (jsonObject.getString("status").equals("OK"))
            {
                providerLatitude = jsonObject.getDouble("latitude");
                providerLongitude = jsonObject.getDouble("longitude");

                latLongHashMap.put("latitude" + numberKey, providerLatitude.toString());
                latLongHashMap.put("longitude" + numberKey, providerLongitude.toString());
            }
            else
            {
                latLongHashMap.put("latitude" + numberKey, null);
                latLongHashMap.put("longitude" + numberKey, null);
            }
            numberKey += 1;
        }
        catch (Exception ex)  {  }

    }
    /* ****** Custom BaseAdapter ****** */
    public class MyAdapter extends BaseAdapter
    {
        ViewHolder holder =  new ViewHolder(); // Global declare
        View leftPart, rightPart, leftPart2; // Global declare

        private String[] firstName;
        private String[] lastName;
        private Context context;
        private String[] countryName;
        private String[] businessAddress;

        public MyAdapter(Context context, DataHolder holder)
        {
            this.context = context;
            this.firstName = holder.firstName;
            this.lastName = holder.lastName;
            this.countryName = holder.countryName;
            this.businessAddress = holder.businessAddress;
        }

        @Override
        public int getCount()
        {
            return firstName.length;
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(context);

            convertView = inflater.inflate(R.layout.listview_item, null);
            holder.FirstName = (TextView) convertView.findViewById(R.id.tv_headLn);

            leftPart =  convertView.findViewById(R.id.Markar_Icon);
            leftPart.setTag(position);
            leftPart.setOnClickListener(SecondActivity.this);

            leftPart2 =  convertView.findViewById(R.id.customCheckableLinearLayout);
            leftPart2.setTag(position);
            leftPart2.setOnClickListener(SecondActivity.this);

            rightPart =  convertView.findViewById(R.id.linearLayoutProviderDetails);
            rightPart.setTag(position);
            rightPart.setOnClickListener(SecondActivity.this);

            holder.LastName = (TextView) convertView.findViewById(R.id.tv_dateLn);
            holder.CountryName = (TextView) convertView.findViewById(R.id.tv_country);
            holder.BusinessAddress = (TextView) convertView.findViewById(R.id.tv_business);
            holder.FirstName.setText(firstName[position]);
            holder.LastName.setText(lastName[position]);
            holder.CountryName.setText(countryName[position]);
            holder.BusinessAddress.setText(businessAddress[position]);

            return convertView;
        }
    }
    @SuppressWarnings("UnusedDeclaration")
    public static class ViewHolder
    {
        TextView FirstName;
        TextView LastName;
        TextView CountryName;
        TextView BusinessAddress;
    }
}