package com.app.mycuratioui;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;


public class DoctorDetails extends CustomTabActivity
{

    public static int dip2px(Context context, float dipValue)
    {
        if (context == null) {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    SharedPreferenceClass sharedPrefClassObj;
    ApiUrls myApiUrls;
    public Dialog custom_connection_dialog;
    public String Name;

    private TransparentProgressDialog pd;
    private android.os.Handler h;
    private Runnable r;

    public Dialog dialog;
    public Button TurnAgain;
    public TextView TxtDocName;

    private static final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,50, 0.5f);


    private static TabHost tabHost;
    private static TabHost.TabSpec spec;
    public static LayoutInflater inflater;

    private View tab;
    private TextView label,label2,label3,divider;

    public  String npiProvideraddress, npiProviderPhNo, gender= "not define", entity_type, ProviderNamePrefixText, ProviderFirstName, ProviderLastName,
            ProviderOrganizationName, ProviderFirstLineBusinessMailingAddress, ProviderBusinessMailingAddressCityName,
            ProviderBusinessMailingAddressStateName, ProviderBusinessMailingAddressFaxNumber,ProviderBusinessMailingAddressPostalCode,
            providerAddress = null, providerName=null,ProviderSecondLineBusinessMailingAddress;
    ImageView  providerIcon;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_details);
/*
        ActionBarDynamic actionbardynamic = new ActionBarDynamic();
        String colorCode = "#54D66A";
        actionbardynamic.DynamicActionBar(getActionBar(), colorCode);
        ActionBar ab = getActionBar();
        ab.setCustomView(R.layout.custom_actionbar_layout);

        TextView TvActionbarTitle = (TextView) findViewById(R.id.TvActionBarTitle);
        TvActionbarTitle.setText("PROVIDERS");
*/
        providerIcon = (ImageView) findViewById(R.id.providerGenderIcon);
        pd = new TransparentProgressDialog(this, R.drawable.spinner);
        r = new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        };

        inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
        tabHost = getTabHost();
        tabHost.getTabWidget().setDividerDrawable(null);


            // This converts the custom tab view we created and injects it into the tab widget
            tab = inflater.inflate(R.layout.custom_tab_layout_provider_details, getTabWidget(), false);
            // Mainly used to set the weight on the tab so each is equally wide
            tab.setLayoutParams(params);
            // Add some text to the tab
            label = (TextView) tab.findViewById(R.id.tabLabel);
            label.setText("About");
            label.setTextColor(Color.parseColor("#54D66A"));
            label.setTypeface(Typeface.DEFAULT_BOLD);
            // Show a thick line under the selected tab (there are many ways to show
            // which tab is selected, I chose this)
            divider = (TextView) tab.findViewById(R.id.tabSelectedDivider);
            divider.setVisibility(View.VISIBLE);

            Intent AboutContain = new Intent(this, ProviderAboutContain.class);
            spec = tabHost.newTabSpec("About").setIndicator(tab).setContent(AboutContain);
            tabHost.addTab(spec);


                // This converts the custom tab view we created and injects it into the tab widget
                tab = inflater.inflate(R.layout.custom_tab_layout_provider_details, getTabWidget(), false);
                // Mainly used to set the weight on the tab so each is equally wide
                tab.setLayoutParams(params);
                // Add some text to the tab
                label2 = (TextView) tab.findViewById(R.id.tabLabel2);
                label2.setText("Location");
                label2.setTextColor(Color.parseColor("#B2B2B2"));


                Intent LocationTab = new Intent(this, ProviderLocationTab.class);
                spec = tabHost.newTabSpec("Location").setIndicator(tab).setContent(LocationTab);
                tabHost.addTab(spec);


                        // This converts the custom tab view we created and injects it into the tab widget
                        tab = inflater.inflate(R.layout.custom_tab_layout_provider_details, getTabWidget(), false);
                        // Mainly used to set the weight on the tab so each is equally wide
                        tab.setLayoutParams(params);
                        // Add some text to the tab
                        label3 = (TextView) tab.findViewById(R.id.tabLabel3);
                        label3.setText("Review");
                        label3.setTextColor(Color.parseColor("#B2B2B2"));

                        Intent ReviewTab = new Intent(this, ProviderReviewTab.class);
                        spec = tabHost.newTabSpec("Review").setIndicator(tab).setContent(ReviewTab);
                        tabHost.addTab(spec);


        // Listener to detect when a tab has changed. I added this just to show
        // how you can change UI to emphasize the selected tab
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tag) {
                // reset some styles
                clearTabStyles();
                View tabView = null;
                // Use the "tag" for the tab spec to determine which tab is selected
                if (tag.equals("About")) {
                    tabView = getTabWidget().getChildAt(0);

                    label.setTypeface(Typeface.DEFAULT_BOLD);
                    label.setTextColor(Color.parseColor("#54D66A"));
                    label2.setTextColor(Color.parseColor("#B2B2B2"));
                    label3.setTextColor(Color.parseColor("#B2B2B2"));
                    label2.setTypeface(Typeface.DEFAULT);
                    label3.setTypeface(Typeface.DEFAULT);

                }if (tag.equals("Location")) {
                    tabView = getTabWidget().getChildAt(1);

                    label2.setTextColor(Color.parseColor("#54D66A"));
                    label.setTextColor(Color.parseColor("#B2B2B2"));
                    label3.setTextColor(Color.parseColor("#B2B2B2"));
                    label.setTypeface(Typeface.DEFAULT);
                    label3.setTypeface(Typeface.DEFAULT);
                    label2.setTypeface(Typeface.DEFAULT_BOLD);

                }
                else if (tag.equals("Review")) {
                    tabView = getTabWidget().getChildAt(2);

                    label3.setTextColor(Color.parseColor("#54D66A"));
                    label.setTextColor(Color.parseColor("#B2B2B2"));
                    label2.setTextColor(Color.parseColor("#B2B2B2"));
                    label.setTypeface(Typeface.DEFAULT);
                    label2.setTypeface(Typeface.DEFAULT);
                    label3.setTypeface(Typeface.DEFAULT_BOLD);

                }

                tabView.findViewById(R.id.tabSelectedDivider).setVisibility(View.VISIBLE);
            }
        });


        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());
        myApiUrls = new ApiUrls();

        TxtDocName = (TextView) findViewById(R.id.TxtDocName);

        try {
            new GetProviderInfoByNpiId().execute();

        } catch (Exception ex) {

        }

           }


    private void clearTabStyles() {
        for (int i = 0; i < getTabWidget().getChildCount(); i++) {
            tab = getTabWidget().getChildAt(i);
            tab.findViewById(R.id.tabSelectedDivider).setVisibility(View.GONE);
        }
    }



    private class GetProviderInfoByNpiId extends AsyncTask<Void, Void, Void>
    {
        JSONObject jObject;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String  providerDetails = myApiUrls.getProviderInfoByNPI.toString() + sharedPrefClassObj.getProviderNPIid();
            Log.d("providerDetails",providerDetails+ " ");
            String jsonStr = sh.makeServiceCall(providerDetails, ServiceHandler.GET);
            if (jsonStr != null)
            {
                try
                {
                    jObject = new JSONObject(jsonStr);
                    JSONArray providerdata  = jObject.getJSONArray("npidata");

                    JSONObject c = providerdata.getJSONObject(0);

                    String urlStatus = c.getString("rowCount");
                    if(Integer.parseInt(urlStatus) > 0)
                    {

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
                        if(c.has("Provider Gender Code"))
                        {
                            gender = c.getString("Provider Gender Code");
                        }
                        else
                        {
                            gender = "";
                        }
                        npiProvideraddress =
                                ProviderFirstLineBusinessMailingAddress + ", " +
                                        ProviderSecondLineBusinessMailingAddress  + ", " +
                                        ProviderBusinessMailingAddressCityName + ", " +
                                        ProviderBusinessMailingAddressStateName +", "+
                                        ProviderBusinessMailingAddressPostalCode;

                    }
                     else
                    {
                        Log.d("There is no provider with this NPI id", "null result");
                    }

                }
                catch (Exception e)
                {
              }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            sharedPrefClassObj.setProviderName(providerName);
            sharedPrefClassObj.setProviderPhone(npiProviderPhNo);
            sharedPrefClassObj.setProviderFax(ProviderBusinessMailingAddressFaxNumber);
            sharedPrefClassObj.setProviderSpeciality("not found");

            TxtDocName.setText(providerName);
            providerIcon = (ImageView) findViewById(R.id.providerGenderIcon);

            Log.d("gender 0", gender + " ");
/*
            if (gender.equals("M"))
            {
                Log.d("gender 1", gender + " ");
                providerIcon.setImageResource(R.drawable.male_provider_icon);
            }
                else if(gender.equals("F"))
            {
                Log.d("gender 1", gender + " ");
                providerIcon.setImageResource(R.drawable.female_prvider_icon);
            }
                else
            {
                Log.d("gender else", gender + " ");
                providerIcon.setImageResource(R.drawable.providericongreenround);
            }
*/
            if(pd.isShowing())
                pd.dismiss();
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
    public void navigateMe(View view)
    {
        npiProvideraddress = npiProvideraddress.replace(" ","+");
        String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", npiProvideraddress);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
