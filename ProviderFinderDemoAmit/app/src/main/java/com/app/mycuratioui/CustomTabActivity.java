package com.app.mycuratioui;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by technicise on 25/8/14.
 */
public class CustomTabActivity extends TabActivity {

    private Menu menu;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    //Bugreporthelper bugreporthelper;

    SharedPreferenceClass sharedPrefClassObj;
    public   ImageView  view ;
    public   String     LoginType;
    public   String     Logged;
    CustomSessionManager session;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());
        LoginType=sharedPrefClassObj.getLoginType();
        Logged ="LoggedUser";

        view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(15, 0, 0, 0);

        this.menu = menu;
/*        if(LoginType.equals(Logged))
        {
            getMenuInflater().inflate(R.menu.user_home, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.common_menu_not_logged, menu);
        }*/
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //  menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu));

        switch (item.getItemId()) {
/*

            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_home:

                session = new CustomSessionManager(getApplicationContext());
                sharedPrefClassObj = new SharedPreferenceClass(getApplicationContext());
                LoginType=sharedPrefClassObj.getLoginType();

                if(LoginType.equals(Logged)){

                    Intent UserDashBoard = new Intent(getApplicationContext(), UserHome.class);
                    startActivity(UserDashBoard);
                } else {

                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);

                }
                return true;

            case R.id.menu_add:

                Toast.makeText(getApplicationContext(), "You Click Add Option", Toast.LENGTH_SHORT).show();
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu));
                return true;

            case R.id.menu_chart:

                Intent intent_load_my_chart = new Intent(getApplicationContext(), ShowMyChartList.class);
                startActivity(intent_load_my_chart);
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu));
                return true;
            case R.id.menu_community:

                Toast.makeText(getApplicationContext(), "You Click Community Option", Toast.LENGTH_SHORT).show();
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu));
                return true;
            case R.id.menu_devices:

                Toast.makeText(getApplicationContext(), "You Click Devices Option", Toast.LENGTH_SHORT).show();
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu));
                return true;

            case R.id.menu_providers:

                Intent i = new Intent(getApplicationContext(), ProviderSearchByTypeActivity.class);
                startActivity(i);

                return true;

            case R.id.menu_settings:

                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu));
                Intent intent = new Intent(getApplicationContext(), AccountSettings.class);
                startActivity(intent);
                return true;
            case R.id.developer_settings:

                cd = new ConnectionDetector(getApplicationContext());
                isInternetPresent = cd.isConnectingToInternet();
                bugreporthelper = new Bugreporthelper(getApplicationContext());
                if(isInternetPresent)
                {
                    bugreporthelper.sendBugReportFromLocalToServer();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "NO Internet Connection", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().addOnMenuVisibilityListener(new android.app.ActionBar.OnMenuVisibilityListener() {
            @Override
            public void onMenuVisibilityChanged(boolean isVisible) {
                //  Toast.makeText(getApplicationContext(), "on OnMenuVisibilityListener from Custom Action Bar Activity", Toast.LENGTH_LONG).show();
                if(isVisible)
                {
                   ////// menu.getItem(0).setIcon(R.drawable.white_x);
                }
                else
                    menu.getItem(0).setIcon(R.drawable.menu);
            }
        });
    }

}
