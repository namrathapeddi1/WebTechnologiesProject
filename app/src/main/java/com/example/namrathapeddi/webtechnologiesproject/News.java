package com.example.namrathapeddi.webtechnologiesproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Namratha Peddi on 11/23/2017.
 */

public class News extends Fragment {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private DiskBasedCache mCache;
    private com.android.volley.Network mNetwork;
    ListView myListView1;
    public String text;
    private static final String TAG = News.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.news, container, false);
        Bundle bundle2= getArguments();
        if(bundle2!=null) {
            //if(bundle2.containsKey("key1")){
            final String keep = bundle2.getString("key");
            text = keep;
            if(keep.contains("-")){
                text = keep.substring( 0, keep.indexOf("-"));}
            //TextView text1 = (TextView)rootView.findViewById(R.id.newsText);
            //text1.setText(text);
            String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?searchText3="+text+"&func=func5";
            mRequestQueue = Singleton.getInstance(getActivity().getApplicationContext()).getRequestQueue(getActivity().getApplicationContext());
            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        List<String> list1 = new ArrayList<String>();
                        List<String> list2 = new ArrayList<String>();
                        List<String> list3 = new ArrayList<String>();
                        List<String> list4 = new ArrayList<String>();
                        JSONArray newArray = new JSONArray(response);
                        JSONObject mainObj = newArray.getJSONObject(0);
                        JSONObject mainObj2 = newArray.getJSONObject(1);
                        JSONObject mainObj3 = newArray.getJSONObject(2);
                        JSONObject mainObj4 = newArray.getJSONObject(3);
                        JSONObject mainObj5 = newArray.getJSONObject(4);
                        JSONObject mainObj11 = mainObj.getJSONObject("first");
                        JSONObject mainObj12 = mainObj2.getJSONObject("second");
                        JSONObject mainObj13 = mainObj3.getJSONObject("third");
                        JSONObject mainObj14 = mainObj4.getJSONObject("four");
                        JSONObject mainObj15 = mainObj5.getJSONObject("five");
                        String title1 = mainObj11.getString("Title");
                        String title2 = mainObj12.getString("Title");
                        String title3 = mainObj13.getString("Title");
                        String title4 = mainObj14.getString("Title");
                        String title5 = mainObj15.getString("Title");
                        String author1 = mainObj11.getString("Author");
                        String author2 = mainObj12.getString("Author");
                        String author3 = mainObj13.getString("Author");
                        String author4 = mainObj14.getString("Author");
                        String author5 = mainObj15.getString("Author");
                        String date1 = mainObj11.getString("Date");
                        String date2 = mainObj12.getString("Date");
                        String date3 = mainObj13.getString("Date");
                        String date4 = mainObj14.getString("Date");
                        String date5 = mainObj15.getString("Date");
                        String link1 = mainObj11.getString("Link");
                        String link2 = mainObj12.getString("Link");
                        String link3 = mainObj13.getString("Link");
                        String link4 = mainObj14.getString("Link");
                        String link5 = mainObj15.getString("Link");
                        list1.add(title1);
                        list1.add(title2);
                        list1.add(title3);
                        list1.add(title4);
                        list1.add(title5);
                        list2.add(author1);
                        list2.add(author2);
                        list2.add(author3);
                        list2.add(author4);
                        list2.add(author5);
                        list3.add(date1);
                        list3.add(date2);
                        list3.add(date3);
                        list3.add(date4);
                        list3.add(date5);
                        list4.add(link1);
                        list4.add(link2);
                        list4.add(link3);
                        list4.add(link4);
                        list4.add(link5);
                        String[] title = list1.toArray(new String[list1.size()]);
                        String[] author = list2.toArray(new String[list2.size()]);
                        String[] date = list3.toArray(new String[list3.size()]);
                        final String[] link = list4.toArray(new String[list4.size()]);

                        myListView1 = (ListView) rootView.findViewById(R.id.myListView1);
                        //myListView.setAdapter(adapter);
                        //ItemAdapter1 ItemAdapter1 = new ItemAdapter1(getActivity(), title, author, date);
                        ItemAdapter1 hello = new ItemAdapter1(getActivity(), title, author, date);
                        myListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Uri uri = Uri.parse(link[position]);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);

                            }
                        });
                        myListView1.setAdapter(hello);

                       // JSONObject mainObject = new JSONObject(response);
                        //JSONArray newArray = mainObject.getJSONArray[0];
                        //JSONArray newArray = new JSONArray(response);
                        //String uniObject = newArray[0];
                        //Log.i(TAG, response);
                        //Log.i(TAG, title);

                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Invalid JSON string: ");

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i(TAG, error.toString());
                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            mRequestQueue.add(stringRequest);
                }


        return rootView;
    }
}
