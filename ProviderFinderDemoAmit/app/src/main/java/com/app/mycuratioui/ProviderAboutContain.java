package com.app.mycuratioui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
/*
import com.app.mychartui.ConfirmDemographics;
import com.app.mychartui.DBHandler;
import com.app.mychartui.mychartmedical.MedicalAllergyMentionedMoreInfo;
import com.app.mychartui.mychartmedical.MedicalGrowthChartStart;
import com.app.mychartui.mychartmedical.MedicalImmunizationSurgicalFurther;
import com.app.mychartui.mychartmedical.MedicalIssueSubSectionComplete;
import com.app.mychartui.mychartmedical.MedicalMoreInfoPharmacy;
import com.app.mychartui.mychartmedical.MedicalSignedUpPatentPortals;
import com.app.mychartui.mychartmedical.MedicalSurgerySubSectionComplete;
import com.app.mychartui.mychartmedical.MedidcalSurgeryAddSurgeryLocation;
import com.app.mychartui.mychartmedical.mychartmedicaldbhandlers.MyChartAllergiesDatabaseHandler;
import com.app.mychartui.mychartmedical.mychartmedicaldbhandlers.MyChartImmunizationsDatabaseHandler;
import com.app.mychartui.mychartmedical.mychartmedicaldbhandlers.MyChartIssuesDatabaseHandler;
import com.app.mychartui.mychartmedical.mychartmedicaldbhandlers.MyChartMedicationsDatabaseHandler;
import com.app.mychartui.mychartmedical.mychartmedicaldbhandlers.MyChartSurgeriesDatabaseHandler;
import com.app.mycuratioui.ApiUrls;
import com.app.mycuratioui.LoginActivity;
import com.app.mycuratioui.SharedPreferenceClass;
import com.app.mycuratioui.UserHome;
*/
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProviderAboutContain extends Activity implements OnClickListener{


    public String   chartFlag = null, LoginType = null,current_chart_id,ProviderPhoneNo,
                    Med_sch, Grd_yr,Pri_spec, Sec_spec_1, Sec_spec_2, Sec_spec_3, Sec_spec_4, Sec_spec_all;
    SharedPreferenceClass sharedPrefClassObj;
    ApiUrls myApiUrls;
    public Context context;
    public Dialog dialog, custom_error_common_dialog;
    public Button TryAgain;
    public int int_current_chart_id;
    public TextView SpecialityDetails,EducationDetails;
    JSONArray jArray;
    boolean AboutContain=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_about_contain);

        context=this;

        sharedPrefClassObj = new SharedPreferenceClass(context);
        myApiUrls = new ApiUrls();
        if(sharedPrefClassObj.getProviderFromChartFlagId() != null)
            chartFlag = sharedPrefClassObj.getProviderFromChartFlagId();

        if(sharedPrefClassObj.getLoginType() != null)
            LoginType = sharedPrefClassObj.getLoginType();
        else
            LoginType = "notLoggedUser";

        if(sharedPrefClassObj.getProviderPhone()!= null)
            ProviderPhoneNo =sharedPrefClassObj.getProviderPhone();
        else
            ProviderPhoneNo = "0000000000";

        String url = myApiUrls.getHospitalOfficeDataByNpi;
        url = url + sharedPrefClassObj.getProviderNPIid();

        SpecialityDetails = (TextView)findViewById(R.id.specialityDetails);
        EducationDetails = (TextView)findViewById(R.id.educationDetails);

        new GetProviderInfoByNpiId().execute(url);



    }

    public void AddProvider(View i)
    {
/*
        try
        {
            if (chartFlag != null)
            {

                chartAddMyProvider();
            }
            else if (sharedPrefClassObj.getMedicalProviderAddProvider() != null)
            {
                Log.d("getMedicalProviderAddProvider", "1");
                sharedPrefClassObj.setMedicalProviderAddProvider(null);
                Intent intent = new Intent(context, MedicalGrowthChartStart.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalSurgeryAddProvider() != null)
            {
                //come from 'MedicalSurgeryProvider'
                MyChartSurgeriesDatabaseHandler surgerydb= new MyChartSurgeriesDatabaseHandler(context);
                String Id= sharedPrefClassObj.getMedicalSurgeriesId();
                surgerydb.updateSurgeryDetails(Id, sharedPrefClassObj.getProviderName(), "surgery provider");
                surgerydb.updateSurgeryDetails(Id, sharedPrefClassObj.getProviderNPIid(), "surgery provider npi");
                sharedPrefClassObj.setMedicalSurgeryAddProvider(null);
                Intent intent = new Intent(context, MedidcalSurgeryAddSurgeryLocation.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalPharmacyAddProvider() != null )
            {
                Log.d("getMedicalPharmacyAddProvider","3");
                sharedPrefClassObj.setMedicalPharmacyAddProvider(null);
                Intent intent = new Intent(context, MedicalSignedUpPatentPortals.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalSurgeryAddProviderLocation() != null )
            {
                //come from 'MedidcalSurgeryAddSurgeryLocation'
                Log.d("setMedicalSurgeryAddProviderLocation","3.1");
                sharedPrefClassObj.setMedicalSurgeryAddProviderLocation(null);
                Intent intent = new Intent(context, MedicalSurgerySubSectionComplete.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalHospitalAddProvider() != null )
            {
                Log.d("getMedicalHospitalAddProvider","4");
                sharedPrefClassObj.setMedicalHospitalAddProvider(null);
                Intent intent = new Intent(context, MedicalSignedUpPatentPortals.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalAllergyAddMyProvider() != null )
            {
                sharedPrefClassObj.setMedicalAllergyAddMyProvider(null);
                String MedicalAllergyId= sharedPrefClassObj.getMedicalAllergyId();
                MyChartAllergiesDatabaseHandler allergyDb= new MyChartAllergiesDatabaseHandler(context);
                allergyDb.updateAllergyDetails(MedicalAllergyId,sharedPrefClassObj.getProviderName(),"allergy provider");
                allergyDb.updateAllergyDetails(MedicalAllergyId,sharedPrefClassObj.getProviderNPIid(),"allergy provider npi");
                Intent intent = new Intent(context, MedicalAllergyMentionedMoreInfo.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalIssueAddMyProvider() != null )
            {
                String MedicalIssuesId= sharedPrefClassObj.getMedicalIssuesId();
                MyChartIssuesDatabaseHandler issuedb = new MyChartIssuesDatabaseHandler(context);
                issuedb.updateIssueDetails(MedicalIssuesId,sharedPrefClassObj.getProviderName(),"issue provider");
                issuedb.updateIssueDetails(MedicalIssuesId,sharedPrefClassObj.getProviderNPIid(),"issue provider npi");
                sharedPrefClassObj.setMedicalIssueAddMyProvider(null);
                Intent intent = new Intent(context, MedicalIssueSubSectionComplete.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalImmunizationAddMyProvider() != null )
            {
                // come from 'MedicalImmunizationProviderFurther' Activity
                String MedicalImmunizationsId= sharedPrefClassObj.getMedicalImmunizationsId();
                MyChartImmunizationsDatabaseHandler immunizationdb = new MyChartImmunizationsDatabaseHandler(context);
                immunizationdb.updateImmunizationDetails(MedicalImmunizationsId, sharedPrefClassObj.getProviderName(), "immunization provider");
                immunizationdb.updateImmunizationDetails(MedicalImmunizationsId, sharedPrefClassObj.getProviderNPIid(), "immunization provider npi");
                sharedPrefClassObj.setMedicalImmunizationAddMyProvider(null);
                Intent intent = new Intent(context, MedicalImmunizationSurgicalFurther.class);
                startActivity(intent);
            }
            else if (sharedPrefClassObj.getMedicalSurgeryAddProviderFurther() != null )
            {
                String Id= sharedPrefClassObj.getMedicalMedicationsId();
                MyChartMedicationsDatabaseHandler medicationdb=new MyChartMedicationsDatabaseHandler(context);
                medicationdb.updateMedicationDetails(Id,sharedPrefClassObj.getProviderName(), "medication provider");
                medicationdb.updateMedicationDetails(Id,sharedPrefClassObj.getProviderNPIid(), "medication provider npi");
                sharedPrefClassObj.setMedicalSurgeryAddProviderFurther(null);
                Intent intent = new Intent(context, MedicalMoreInfoPharmacy.class);
                startActivity(intent);
            }
            else
            {

                if (LoginType.equals("LoggedUser"))
                {
                    Intent intentUserHome = new Intent(context, UserHome.class);
                    startActivity(intentUserHome);
                }
                else
                {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }
        catch (Exception exception)
        {
            ShowCustomDialogError();
        }*/
    }

    public void chartAddMyProvider()
    {/*
        try
        {
            Log.d("chartFlag", chartFlag + " ");
            if (chartFlag.equals("PrimaryCareProviderToProviderSearch"))
            {
                DBHandler dbh = new DBHandler(context);
                if (sharedPrefClassObj.getChartBasicInfoEditFlag() != null)
                {
                    if (sharedPrefClassObj.getChartBasicInfoEditFlag().equals("EditingBasicInfo"))
                    {
                        String provider_name = sharedPrefClassObj.getProviderName();
                        current_chart_id = sharedPrefClassObj.getCurrentChartId();
                        int_current_chart_id = Integer.parseInt(current_chart_id);
                        dbh.updatePrimaryCareProviderChartDetails(provider_name, int_current_chart_id);
                        String provider_npi = sharedPrefClassObj.getProviderNPIid();
                        dbh.updatePrimaryCareProviderNPIChartDetails(provider_npi, int_current_chart_id);

                        Intent intentConfirmDemographics = new Intent(context, ConfirmDemographics.class);
                        sharedPrefClassObj.setProviderFromChartFlagId(null);
                        startActivity(intentConfirmDemographics);
                    }
                }
                else
                {

                    String chart_row_id = sharedPrefClassObj.getLastInsertedRowId();
                    int chart_id = Integer.parseInt(chart_row_id);
                    String provider_name = sharedPrefClassObj.getProviderName();
                    dbh.updatePrimaryCareProviderChartDetails(provider_name, chart_id);

                    String provider_npi = sharedPrefClassObj.getProviderNPIid();
                    dbh.updatePrimaryCareProviderNPIChartDetails(provider_npi, chart_id);


                    Intent intentConfirmDemographics = new Intent(context, ConfirmDemographics.class);
                    sharedPrefClassObj.setProviderFromChartFlagId(null);
                    startActivity(intentConfirmDemographics);
                }
            }
            else
            {
                Intent intentUserHome = new Intent(context, UserHome.class);
                startActivity(intentUserHome);
            }
        }
        catch (Exception earhart)
        {
            ShowCustomDialogError();
        }*/
    }

    public void ShowCustomDialogError()
    {
        custom_error_common_dialog = new Dialog(context,android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        custom_error_common_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.style.TransparentProgressDialog));
        custom_error_common_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_error_common_dialog.setCancelable(true);
        custom_error_common_dialog.setContentView(R.layout.custom_zip_error_dialog);
        custom_error_common_dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, Color.parseColor("#FFFFFF"));

        TextView errorTV = (TextView) custom_error_common_dialog.getWindow().findViewById(R.id.textviewError);
        errorTV.setText("Please try after some time");
        TryAgain = (Button) custom_error_common_dialog.findViewById(R.id.BtnTryAgain);
        TryAgain.setOnClickListener(this);
        custom_error_common_dialog.show();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

                case R.id.BtnTryAgain:
                custom_error_common_dialog.dismiss();
                break;
        }
    }

    private class GetProviderInfoByNpiId extends AsyncTask<String, Void, Void>
    {
        JSONObject jObject;
        List<HashMap<String, Object>> npidata = null;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... arg0)
        {
            try
            {
                String getJsonURL= arg0[0];
                //Log.d("getJsonURL",getJsonURL+" ");
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();
                // Making a request to url and getting response
                String  strJson = sh.makeServiceCall(getJsonURL, ServiceHandler.GET);
                //Log.d("strJson",strJson+" ");
                jObject = new JSONObject(strJson);
                // Getting the parsed data as a List construct
                npidata = parse(jObject);
            }

            catch (Exception e)
            {
                //Log.d("Exception",e.toString());
            }


            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if(AboutContain == false)
            {
                SpecialityDetails.setText("Information not found");
                EducationDetails.setText("Information not found");
            }
            else
            {
                EducationDetails.setText(Grd_yr + ", " + Med_sch);
                String speciallityDetails = Pri_spec + ", " + Sec_spec_1 + ", " + Sec_spec_2 + ", " + Sec_spec_3 + ", " + Sec_spec_4 + ", " + Sec_spec_all;
                speciallityDetails = speciallityDetails.replace("  ", "");
                speciallityDetails = speciallityDetails.replace(",  ", "");
                speciallityDetails = speciallityDetails.replace(",,", "");
                speciallityDetails = speciallityDetails.replace(",,,", "");
                speciallityDetails = speciallityDetails.replace(",,,,", "");
                SpecialityDetails.setText(speciallityDetails);

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
            jArray = jObject.getJSONArray("provider_bio");
        }
        catch (Exception e)
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
            //if data found then 'aboutData' will be TRUE
            if(jCountries.length() > 0)
                AboutContain = true;

            for(int i=0; i < jCountries.length() ; i++)
            {
                // Call getCountry with country JSON object to parse the country
                provider = getCountry((JSONObject)jCountries.get(i));
                countryList.add(provider);
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }

        return countryList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCountry(JSONObject c)
    {
        HashMap<String, Object> provider = new HashMap<String, Object>();
        try
        {

            if (c.has("Med_sch"))
            {
                Med_sch = c.getString("Med_sch");
            }
            else
            {
                Med_sch = " ";
            }

            if (c.has("Grd_yr"))
            {
                Grd_yr = c.getString("Grd_yr");
            }
            else
            {
                Grd_yr = " ";
            }


            if (c.has("Pri_spec"))
            {
                Pri_spec = c.getString("Pri_spec");
            }
            else
            {
                Pri_spec = " ";
            }

            if (c.has("Sec_spec_1"))
            {
                Sec_spec_1 = c.getString("Sec_spec_1");
            }
            else
            {
                Sec_spec_1 = " ";
            }

            if (c.has("Sec_spec_2"))
            {
                Sec_spec_2 = c.getString("Sec_spec_2");
            }
            else
            {
                Sec_spec_2 = " ";
            }

            if (c.has("Sec_spec_3"))
            {
                Sec_spec_3 = c.getString("Sec_spec_3");
            }
            else
            {
                Sec_spec_3 = " ";
            }

            if(c.has("Sec_spec_4"))
            {
                Sec_spec_4 = c.getString("Sec_spec_4");
            }
            else
            {
                Sec_spec_4 = " ";
            }

            if(c.has("Sec_spec_all"))
            {
                Sec_spec_all = c.getString("Sec_spec_all");
            }
            else
            {
                Sec_spec_all = " ";
            }
        }
        catch (Exception e)
        {
            // e.printStackTrace();
        }
        return provider;
    }


}
