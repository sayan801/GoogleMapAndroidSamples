package com.amiyo.technicise.androidcustomprogress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by technicise on 8/12/14.
 */
public class AddressAdapter extends BaseAdapter {



    String[] ProviderLat;
    Context context;

        public AddressAdapter(String[] Provider_Lat) {
           this.context=context;
            this.ProviderLat=Provider_Lat;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ProviderLat.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rowView=inflater.inflate(R.layout.listview_item, parent,false);
            TextView Provider_Lat=(TextView) rowView.findViewById(R.id.Txt_Lat);


            Provider_Lat.setText(ProviderLat[position]);
            // String str1=nameID[position];

            return rowView;
        }

    }



