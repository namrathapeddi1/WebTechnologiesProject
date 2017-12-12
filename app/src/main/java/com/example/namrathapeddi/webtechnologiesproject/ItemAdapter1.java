package com.example.namrathapeddi.webtechnologiesproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Namratha Peddi on 11/25/2017.
 */

public class ItemAdapter1 extends BaseAdapter {

    LayoutInflater mInflater1;
    String[] title;
    String[] author;
    String[] date;

    public ItemAdapter1(Context c, String[] i, String[] p, String[] t){
        title = i;
        author = p;
        date = t;
        mInflater1 = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return title[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater1.inflate(R.layout.my_listview2,null);
        TextView textViewName = (TextView) v.findViewById(R.id.title1);
        TextView textViewValue = (TextView) v.findViewById(R.id.author);
        TextView date1 =(TextView) v.findViewById(R.id.date);
        String name = title[i];
        String arr1 = author[i];
        String arr2 = date[i];
        textViewName.setText(name);
        textViewValue.setText(arr1);
        date1.setText(arr2);
        return v;
    }

}
