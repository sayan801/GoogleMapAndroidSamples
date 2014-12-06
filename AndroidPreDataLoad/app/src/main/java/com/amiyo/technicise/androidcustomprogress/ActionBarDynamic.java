package com.amiyo.technicise.androidcustomprogress;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;


/**
 * Created by Technicise_mac3 on 04/09/14.
 * Trying to write a method that generates custom custom_actionbar_layout for each activity
 */
public class ActionBarDynamic
    {


        public void createActionBar(android.app.ActionBar ab,String title, String colorCode)
            {
                ab.setHomeButtonEnabled(true);
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
                ab.setDisplayShowCustomEnabled(true);
                ab.setTitle(Html.fromHtml(title));
                ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(colorCode));
                ab.setBackgroundDrawable(colorDrawable);
                ab.show();


            }


        public void DynamicActionBar(android.app.ActionBar ab,String colorCode)
        {

            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor(colorCode))); // set color or image of actionbar title
           // ab.setIcon(R.drawable.back_icon); // can set icon of action bar
            ab.setIcon(R.drawable.white_back_icon); // can set icon of action bar

            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowCustomEnabled(true);

        }



    }
