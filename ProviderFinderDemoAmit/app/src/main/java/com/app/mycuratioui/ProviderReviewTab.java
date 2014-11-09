package com.app.mycuratioui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.mycuratioui.ApiUrls;
import com.app.mycuratioui.SharedPreferenceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProviderReviewTab extends FragmentActivity {

    public Context context;

    ListView listReview;
    JSONArray jArray;
    SharedPreferenceClass sharedPrefClassObj;

    ApiUrls myApiUrls = new ApiUrls();
    String comments, userComment;
    int listviewNoOfRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_review_tab);
        context=this;

        listReview = (ListView) findViewById(R.id.reviewContent);

        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
        sharedPrefClassObj = new SharedPreferenceClass(context);
        comments = myApiUrls.getAllComments;
        comments = comments + sharedPrefClassObj.getProviderNPIid();
        listViewLoaderTask.execute(comments);

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

        }
        // Doing the parsing of xml data in a non-ui thread
        @Override
        protected SimpleAdapter doInBackground(String... url)
        {
            try
            {
                String getJsonURL= url[0];
                ServiceHandler sh = new ServiceHandler();
                String  strJson = sh.makeServiceCall(getJsonURL, ServiceHandler.GET);
                jObject = new JSONObject(strJson);
                npidata = parse(jObject);
            }
            catch (Exception e)
            {
                //Log.d("Exception",e.toString());
            }
            String[] from = {"name", "address",};
            int[] to = {R.id.textViewTitle, R.id.textViewShowhide,};
            SimpleAdapter adapter = new SimpleAdapter(ProviderReviewTab.this, npidata, R.layout.list_item_reviewcontent, from, to);
            return adapter;

        }

        /**
         * Invoked by the Android on "doInBackground" is executed
         */
        @Override
        protected void onPostExecute(SimpleAdapter adapter)
        {

            if(adapter.getCount() > 0)
            {
                // Setting adapter for the listview
                listReview.setAdapter(adapter);
                for (int i = 0; i < adapter.getCount(); i++)
                {
                    HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
                    hm.put("position", i);

                }
                listReview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> argo, View view, int i, long l)
                    {
                        String nameOfProvider = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
                        String addressOfProvider = ((TextView) view.findViewById(R.id.textViewShowhide)).getText().toString();
                        //Log.d("Click","Clickk"+nameOfProvider+ " and "+addressOfProvider);
                        //addAnim= new AddAnimation(context);
                        final TextView tvShowHide = (TextView) view.findViewById(R.id.textViewShowhide);
                        final TextView tvViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
                        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear_layoutReview);

                        if(tvShowHide.isShown())
                        {
                            layout.setVisibility(View.GONE);
                            //addAnim.slide_up(layout);
                        }
                        else
                        {
                            layout.setVisibility(View.VISIBLE);
                            //addAnim.slide_down(layout);
                        }
                    }
                });

            }

        }
    }


    /* start #### */
    // Receives a JSONObject and returns a list
    public List<HashMap<String,Object>> parse(JSONObject jObject)
    {
        try
        {
            // Retrieves all the elements in the 'countries' array
            jArray = jObject.getJSONArray("comments");
            // Log.d("ProviderJSONParser-2","ProviderJSONParser-2");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return getCountries(jArray);
    }


    private List<HashMap<String, Object>> getCountries(JSONArray jCountries)
    {
        List<HashMap<String, Object>> countryList = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> provider = null;

        if(jCountries.length() > 10)
        {
            listviewNoOfRow = 10;
        }
        else
        {
            listviewNoOfRow = jCountries.length();
        }
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

        return countryList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCountry(JSONObject c)
    {

        HashMap<String, Object> provider = new HashMap<String, Object>();
        try
        {
            if (c.has("comment"))
            {
                userComment = c.getString("comment");
            }
            else
            {
                userComment = "comment not found";
            }
        }
        catch (Exception e)
        {

        }
        String [] userCommentSubject = userComment.split(" ");
        if(userCommentSubject.length > 3)
        {
            provider.put("name", userCommentSubject[0]+" "+userCommentSubject[1]+" "+userCommentSubject[2]+"...");
        }
        else
        {
            provider.put("name", userComment+"...");
        }


        provider.put("address", userComment);

        return provider;
    }



    public void MoveToReview(View i)
    {
        Intent MoveToUserReview = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(MoveToUserReview);

    }
}
