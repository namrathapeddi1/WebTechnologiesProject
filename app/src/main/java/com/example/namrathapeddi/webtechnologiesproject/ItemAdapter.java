package com.example.namrathapeddi.webtechnologiesproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Namratha Peddi on 11/22/2017.
 */

public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    String[] arr;
    String[] items;
    ProgressBar bar;

    public ItemAdapter(Context c, String[] i, String[] p){
        items = i;
        arr = p;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.my_listview,null);
        //bar = (ProgressBar) v.findViewById(R.id.progressBar);
        TextView textViewName = (TextView) v.findViewById(R.id.textViewName);
        TextView textViewValue = (TextView) v.findViewById(R.id.textViewValue);
        ImageView img = (ImageView) v.findViewById(R.id.imageView2);
        //if(items[i]=="Change"){
           // img.setImageResource(R.mipmap.up);
        //}
        String name = items[i];
        String arr1 = arr[i];
        if(name.equals("Change")){

                String text = arr1.substring( 0, arr1.indexOf("("));
                if(Float.parseFloat(text) > 0){
            img.setImageResource(R.mipmap.up);}

        }
        else{
            img.setImageResource(R.mipmap.down);

        }
        if(!name.equals("Change")){
            img.setImageDrawable(null);


        }


        textViewName.setText(name);
        textViewValue.setText(arr1);
        return v;
    }
}
