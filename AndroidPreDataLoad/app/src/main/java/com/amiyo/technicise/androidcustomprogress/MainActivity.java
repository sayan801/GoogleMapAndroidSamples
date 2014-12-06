package com.amiyo.technicise.androidcustomprogress;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    Button Btngetdata;
    public ProgressBar MyProgressBar;

    JSONArray news = null;

    //URL to get JSON Array
    static String url ="http://curatehealth.net:81/webservice/sayan801/code/index.php?/provider/getProviderInfoByPartialNameZipDistance/davis/66213/1";

    static String address="http://curatehealth.net:81/webservice/sayan801/code/index.php?/geocoding/getLatLongFromAddress/";


    public String  strJson,StrJsonTest,CallAddress;
    int k=0,  number = 1,  reviews=0;

    String npiProvideraddress, npiProviderPhNo, gender, entity_type, ProviderNamePrefixText, ProviderFirstName, ProviderLastName,
            ProviderOrganizationName, ProviderFirstLineBusinessMailingAddress, ProviderBusinessMailingAddressCityName, ProviderBusinessMailingAddressStateName, ProviderBusinessMailingAddressFaxNumber,
            ProviderBusinessMailingAddressPostalCode, displayname, HealthcareProviderTaxonomyCode_1, providerName=null,
            npiID, ProviderSecondLineBusinessMailingAddress;

    public TextView mTextViewSpeciality, mTextViewName, mTextViewAddress, mTextViewNpiId;

    JSONArray jArray,jArrayTest;

    List<HashMap<String, Object>> npidata = null;

    Map<String, String> latLongHashMap = new HashMap<String, String>();

    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<HashMap<String, String>>();

    public ListView mListView; // show search provider data
    public MyAdapter adapter;
    public ImageView view;

    SharedPreferenceClass sharedPrefClassObj;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());
        MyProgressBar=(ProgressBar)findViewById(R.id.CustomProgressBar);
        Btngetdata = (Button)findViewById(R.id.getdata);

        mListView = (ListView) findViewById(R.id.searchedProviderListView);

        ActionBarDynamic actionbardynamic = new ActionBarDynamic();
        String colorCode="#54D66A";
        actionbardynamic.DynamicActionBar(getActionBar(), colorCode);
        ActionBar ab = getActionBar();
        ab.setCustomView(R.layout.custom_actionbar_layout);

        view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(20, 0, 0, 0);

        TextView TvActionbarTitle=(TextView)findViewById(R.id.TvActionBarTitle);
        TvActionbarTitle.setText("PROVIDER DETAILS");
        TvActionbarTitle.setTextColor(Color.parseColor("#FFFFFF"));

        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MyTask().execute();
                Log.d("URL",url);
                MyProgressBar.setVisibility(View.VISIBLE);

            }
        });
        adapter = new MyAdapter(this, R.layout.list_item_provideronmap);
        mListView.setAdapter(adapter);
        mListView.setVisibility(View.GONE);


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
    class MyTask extends AsyncTask<Void, Void, Void>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
              strJson = sh.makeServiceCall(url, ServiceHandler.GET);

            try
            {
                JSONObject jObject = new JSONObject(strJson);

                npidata = parse(jObject);
                sharedPrefClassObj.SetListData(strJson);
                Log.d("JsonData",strJson);
            }
            catch (Exception ex)  {   }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            adapter.notifyDataSetChanged();
           // MyProgressBar.setVisibility(View.GONE);

//            Intent a = new Intent(getApplicationContext(),SecondActivity.class);
//            sharedPrefClassObj.SetListData(strJson);
//            startActivity(a);


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

            try
            {
                //default first time load max. 10 result
                for(int i=0; i < jCountries.length(); i++)
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

                Log.d("Testing",ProviderFirstLineBusinessMailingAddress);
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



            npiProvideraddress = ProviderFirstLineBusinessMailingAddress + ", " + ProviderSecondLineBusinessMailingAddress + ", " +
                    ProviderBusinessMailingAddressCityName + ", " + ProviderBusinessMailingAddressStateName  +", "+ ProviderBusinessMailingAddressPostalCode;





            if(c.has("Provider Gender Code"))
            {
                gender = c.getString("Provider Gender Code");
            }
            else
            {
                gender = "not found";
            }

            if(c.has("Healthcare Provider Taxonomy Code_1"))
                HealthcareProviderTaxonomyCode_1 = c.getString("Healthcare Provider Taxonomy Code_1");
            else
                displayname = "Speciality not found";


                displayname ="Speciality not found";

            npiProvideraddress = npiProvideraddress.replace(" ", "+");
            npiProvideraddress = npiProvideraddress.replace("?", "");   //'Disallowed Key Characters.'
            npiProvideraddress = npiProvideraddress.replace("#", "");


            if(npiProvideraddress.isEmpty() || npiProvideraddress.equals(null) || npiProvideraddress == null)
                npiProvideraddress="addressnotfound"; // will return 'unknown' lat-long and prevented app crash

////////////////// do api call for getting lat, long..

            new Testing().execute();
            CallAddress=address+npiProvideraddress;

                 Log.d("CallAddress",CallAddress);
                latLongHashMap.put("latitude" + k, null);
                latLongHashMap.put("longitude" + k, null);




            latLongHashMap.put("npi_id" + k, npiID);
            latLongHashMap.put("speciality" + k, displayname);
            latLongHashMap.put("review" + k, reviews+"");
            latLongHashMap.put("name" + k, providerName);
            latLongHashMap.put("address" + k, npiProvideraddress.replace("+", " "));
            k++;
            reviews++;
            if(reviews == 6)
                reviews = 0;

            HashMap<String, String> providerData = new HashMap<String, String>();

            providerData.put("speciality"+(number-1), displayname);
            providerData.put("name"+(number-1), providerName + " " );
            providerData.put("address"+(number-1), npiProvideraddress );
            providerData.put("npiid"+(number-1), npiID );
            mArrayList.add(providerData);
            number += 1;



        }
        catch (Exception e)  {  }
        return provider;
    }

    class Testing extends AsyncTask<Void, Void, Void>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            StrJsonTest = sh.makeServiceCall(CallAddress, ServiceHandler.GET);

            try
            {
                JSONObject jObject2 = new JSONObject(StrJsonTest);

                npidata = parse2(jObject2);
               // sharedPrefClassObj.SetListData(strJson);
                Log.d("JsonData",StrJsonTest);
            }
            catch (Exception ex)  {   }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
             adapter.notifyDataSetChanged();
             MyProgressBar.setVisibility(View.GONE);


            Intent a = new Intent(getApplicationContext(),SecondActivity.class);
            sharedPrefClassObj.SetListData(strJson);
            startActivity(a);


        }
    }

    /* start #### */
    // Receives a JSONObject and returns a list
    public List<HashMap<String,Object>> parse2(JSONObject jObject2)
    {
        try
        {
            // Retrieves all the elements in the 'countries' array
            jArrayTest = jObject2.getJSONArray("npidata");
        }
        catch (JSONException e)
        {
            //e.printStackTrace();
        }
        return getCountries2(jArrayTest);
    }

    private List<HashMap<String, Object>> getCountries2(JSONArray jCountries2)
    {
        List<HashMap<String, Object>> countryList = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> provider = null;

        try
        {
            //default first time load max. 10 result
            for(int i=0; i < jCountries2.length(); i++)
            {
                // Call getCountry with country JSON object to parse the country
                provider = getCountry((JSONObject)jCountries2.get(i));
                countryList.add(provider);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return countryList;
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
