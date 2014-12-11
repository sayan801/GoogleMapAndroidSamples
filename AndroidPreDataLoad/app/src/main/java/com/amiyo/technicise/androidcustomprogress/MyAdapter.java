package com.amiyo.technicise.androidcustomprogress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter is used for showing the json array data to the list.
 */
public class MyAdapter extends BaseAdapter {

    private String[] firstName;
    private String[] lastName;
    private Context context;
    private String[] countryName;
    private String[] businessAddress;
    private String[] lat;
    private String[] lang;

    public MyAdapter(Context context, DataHolder holder) {

        this.context = context;
        this.firstName = holder.firstName;
        this.lastName = holder.lastName;
        this.countryName = holder.countryName;
        this.businessAddress = holder.businessAddress;
        this.lat = holder.lat;
        this.lang = holder.lang;
    }

    @Override
    public int getCount() {
        return firstName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = null;

        if (convertView == null ) {
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.FirstName = (TextView) convertView.findViewById(R.id.tv_headLn);
            holder.LastName = (TextView) convertView.findViewById(R.id.tv_dateLn);
            holder.CountryName = (TextView) convertView.findViewById(R.id.tv_country);
            holder.BusinessAddress = (TextView) convertView.findViewById(R.id.tv_business);
            holder.Lat = (TextView) convertView.findViewById(R.id.Txt_Lat);
            holder.Lang = (TextView) convertView.findViewById(R.id.Txt_Long);
            //tag the holder object to the convert view so that the object can recycle every time.
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.FirstName.setText(firstName[position]);
        holder.LastName.setText(lastName[position]);
        holder.CountryName.setText(countryName[position]);
        holder.BusinessAddress.setText(businessAddress[position]);
        holder.Lat.setText(lat[position]);
        holder.Lang.setText(lang[position]);
       return convertView;
    }


    @SuppressWarnings("UnusedDeclaration")
    public static  class ViewHolder {
        TextView FirstName;
        TextView LastName;
        TextView CountryName;
        TextView BusinessAddress;
        TextView Lat;
        TextView Lang;

    }

}

