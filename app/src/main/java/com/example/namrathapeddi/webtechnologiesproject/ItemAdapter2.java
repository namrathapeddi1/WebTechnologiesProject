package com.example.namrathapeddi.webtechnologiesproject;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Namratha Peddi on 11/27/2017.
 */

public class ItemAdapter2 extends ArrayAdapter<String> {
    String[] symbols;
    String[] prices;
    String[] changes;
    String[] percents;


    public ItemAdapter2(@NonNull Context context, String[] symbol, String[] price, String[] change, String[] percent) {
        super(context, R.layout.my_listview3, symbol);
        this.symbols = symbol;
        this.prices = price;
        this.changes = change;
        this.percents = percent;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.my_listview3,parent,false);
        TextView forsymbol = (TextView) customView.findViewById(R.id.textSymbol);
        TextView forprice = (TextView) customView.findViewById(R.id.textPrice);
        TextView forchange = (TextView) customView.findViewById(R.id.textChange);
        TextView forpercent = (TextView) customView.findViewById(R.id.textPercent);
        String symbol1 = symbols[position];
        String price1 = prices[position];
        String change1 = changes[position];
        String percent1 = percents[position];
        forsymbol.setText(symbol1);
        forprice.setText(price1);
        forchange.setText(change1);
        forpercent.setText(percent1);
        if(Float.parseFloat(change1) <0){
            forchange.setTextColor(Color.RED);
            forpercent.setTextColor(Color.RED);

        }
        if(Float.parseFloat(change1) >0){
            forchange.setTextColor(Color.GREEN);
            forpercent.setTextColor(Color.GREEN);

        }
        //String


        return customView;

    }

}
