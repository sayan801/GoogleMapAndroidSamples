package com.amiyo.technicise.androidcustomprogress;

import android.widget.ListAdapter;

/**
 * Created by technicise on 6/12/14.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    String[] First_Name;
    String[] Last_Name;
    Context context;

    public MyAdapter(Context context, String[] FName, String[] LName) {
        this.context=context;
        this.First_Name=FName;
        this.Last_Name=LName;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return First_Name.length;
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

        TextView FirstName=(TextView) rowView.findViewById(R.id.tv_headLn);
        TextView LastName=(TextView) rowView.findViewById(R.id.tv_dateLn);

        FirstName.setText(First_Name[position]);
        LastName.setText(Last_Name[position]);
       // String str1=nameID[position];

        return rowView;
    }

}

