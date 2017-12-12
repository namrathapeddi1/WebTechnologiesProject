package com.example.namrathapeddi.webtechnologiesproject;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StockInfo1 extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private DiskBasedCache mCache;
    private com.android.volley.Network mNetwork;
    private static final String TAG = StockInfo1.class.getName();
    //TextView results = (TextView) findViewById(R.id.jsonData);
    ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info1);
//        if(getIntent().hasExtra("com.example.namrathapeddi.webtechnologiesproject.SOMETHING")){
//            TextView tv = (TextView) findViewById(R.id.textView);
//            String text = getIntent().getExtras().getString("com.example.namrathapeddi.webtechnologiesproject.SOMETHING");
//            tv.setText(text);
        //EditText input2 = (EditText) findViewById(R.id.StockType);
        final Resources res = getResources();

        Intent i = getIntent();
        final String text = i.getStringExtra ( "TextBox");
// Now set this value to EditText
        //input2.setText ( text );
        //final TextView results = (TextView) findViewById(R.id.jsonData);
        String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?table1="+text+"&func=func1";
        //String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?table1=AAPL&func=func1";
        mRequestQueue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue(this.getApplicationContext());
                //mRequestQueue = Volley.newRequestQueue(this);
//                mCache = new DiskBasedCache(getCacheDir(),4*1024*1024);
//                mNetwork = new BasicNetwork(new HurlStack());
//                mRequestQueue = new RequestQueue(mCache,mNetwork);
//                mRequestQueue.start();
               stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                List<String> list = new ArrayList<String>();
                                JSONObject mainObject = new JSONObject(response);
                                String uniObject = mainObject.getString("Time Series (Daily)");
                                JSONObject main2 = new JSONObject(uniObject);
                                Iterator<String> keys = main2.keys();
                                String str_Name = keys.next();
                                int j = 0;
                                String second;
                                String  uniName = main2.getString(str_Name);
                                JSONObject main3 = new JSONObject(uniName);
                                String open = main3.getString("1. open");
                                String close = main3.getString("4. close").trim();
                                String volume = main3.getString("5. volume");
                                String low = main3.getString("3. low");
                                String high = main3.getString("2. high");

                                list.add(text);
                                list.add(open);
                                list.add(close);
                                list.add(close);
                                list.add(volume);
                                list.add(low);
                                list.add(high);
                                while(keys.hasNext()) {

                                    j=j+1;
                                    second = keys.next();

                                    if(j==1){
                                        String uniName1 = main2.getString(second);
                                        JSONObject main4 = new JSONObject(uniName1);
                                        String second1 = main4.getString("4. close");
                                        float second2;

                                            float third = Float.parseFloat(close);
                                            second2 = third - Float.parseFloat(second1);
                                            Float.toString(second2);
                                            list.add(Float.toString(second2));


                                        break;
                                    }

                                }
                                //JSONObject main3 = new JSONObject(str_Name);
                                //String str_Name2 = main3.getString("1. open");








                                //String[] stockArr = new String[list.size()];
                                //stockArr = list.toArray(stockArr);
                                String[] arr = list.toArray(new String[list.size()]);
                                String[] items = res.getStringArray(R.array.items);
                                //ArrayAdapter adapter = new ArrayAdapter<String>(StockInfo1.this,R.layout.my_listview,arr);
                                myListView = (ListView) findViewById(R.id.myListView);
                                //myListView.setAdapter(adapter);
                                ItemAdapter ItemAdapter = new ItemAdapter(StockInfo1.this,items,arr);
                                myListView.setAdapter(ItemAdapter);
                                //JSONObject jsonObject= new JSONObject(response);
                                //String obj = jsonObject.getJSONObject("Meta Data");
                                //JSONObject obj = jsonObject.getJSONObject("Meta Data");
                                //String color = obj.getString("1. Information");

                                Log.i(TAG, response);
                                //results.setText(open);

                            }

                        catch (JSONException e) {
                            Log.e(TAG, "Invalid JSON string: ");

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG,error.toString());
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                mRequestQueue.add(stringRequest);
        }


    }

