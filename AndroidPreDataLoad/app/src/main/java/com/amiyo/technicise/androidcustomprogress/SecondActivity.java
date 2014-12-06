package com.amiyo.technicise.androidcustomprogress;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;


public class SecondActivity extends Activity {

    SharedPreferenceClass sharedPrefClassObj;
    String strJson;
    public ImageView view;
    ListView lv;
    String[] First_Name;
    String[] Last_Name;

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



        strJson = sharedPrefClassObj.GetListData();

        lv=(ListView) findViewById(R.id.listView1);

        try {
            JSONObject jobj=new JSONObject(strJson);

            JSONArray jarray=jobj.getJSONArray("npidata");

            First_Name=new String[jarray.length()];
            Last_Name=new String[jarray.length()];
            JSONObject jobject=null;
            for(int i=0;i<=jarray.length();i++){
                jobject=jarray.getJSONObject(i);

                First_Name[i]=jobject.getString("Provider First Name");
                Last_Name[i]=jobject.getString("Provider Last Name");
            }


        } catch (Exception e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        lv.setAdapter(new MyAdapter(this,First_Name,Last_Name));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),First_Name[position], Toast.LENGTH_SHORT).show();
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

}
