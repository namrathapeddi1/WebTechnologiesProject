package com.example.namrathapeddi.webtechnologiesproject;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Namratha Peddi on 11/29/2017.
 */

public class AutoCompleteAdapter extends ArrayAdapter implements Filterable {
    // private ArrayList<Notification.Style> mData;
    private ArrayList<String> data;

    public AutoCompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if(data.size()>=5){
        return 5;
        }
        else{

            return data.size();
        }
    }

    @Override
    public String getItem(int index) {
        return data.get(index);


    }

    @NonNull
    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering (CharSequence constraint)
            {
                FilterResults results = new FilterResults();
                if (constraint != null)
                {
                    HttpURLConnection conn = null;
                    InputStream input = null;
                    try
                    {
                        URL url = new URL ("http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?searchText="+constraint.toString()+"&func=func3");
                        conn = (HttpURLConnection) url.openConnection();
                        input = conn.getInputStream();
                        InputStreamReader reader = new InputStreamReader (input, "UTF-8");
                        BufferedReader buffer = new BufferedReader (reader, 8192);
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = buffer.readLine()) != null)
                        {
                            builder.append (line);
                        }

                        JSONArray terms = new JSONArray (builder.toString());

                        ArrayList<String> suggestions = new ArrayList<>();
                        for (int ind = 0; ind < terms.length(); ind++)
                        {
                            JSONObject mainObj = terms.getJSONObject(ind);

                            String term = mainObj.getString ("Symbol");
                            String term1 = mainObj.getString ("Name");
                            String term2 = mainObj.getString ("Exchange");

                            //String term = terms
                            suggestions.add (term +"-"  + term1 +"("+ term2 + ")");
                            //suggestions.add (term);
//                            suggestions.add(term1);
//                            suggestions.add(term2);
                        }
                        results.values = suggestions;
                        //results.count = suggestions.size();
                        if(suggestions.size()>=5){
                        results.count = 5;
                        }
                        else{
                            results.count = suggestions.size();
                        }
                        data = suggestions;
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        if (input != null)
                        {
                            try
                            {
                                input.close();
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        if (conn != null) conn.disconnect();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults (CharSequence constraint, FilterResults results)
            {
                if (results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                }
                else notifyDataSetInvalidated();
            }
        };
}
}
