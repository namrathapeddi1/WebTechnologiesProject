package com.example.namrathapeddi.webtechnologiesproject;

/**
 * Created by Namratha Peddi on 11/23/2017.
 */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import static android.content.Context.MODE_PRIVATE;


public class CurrentStock extends Fragment {


    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private DiskBasedCache mCache;
    public static ArrayList<String> temp4 = new ArrayList<>();
    private com.android.volley.Network mNetwork;
    private static final String TAG = CurrentStock.class.getName();
    ProgressBar bar;
    public String text;



    //TextView results = (TextView) findViewById(R.id.jsonData);
    ListView myListView;
    TextView error;
    //Activity context;
    Spinner sp;
    TextView tv;
    String names[] = {"Price","SMA","EMA","MACD","RSI","ADX","CCI","BBANDS"};
    ArrayAdapter <String> adapter;
    String record= "";







    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_stock_info1, container, false);

        final Resources res = getResources();
        bar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        //error = (TextView) rootView.findViewById(R.id.)
        bar.setVisibility(View.VISIBLE);
        //bar.setProgress(10000);
        //new ProgressTask().execute();

        //setSupportActionBar(mActionBarToolbar);
        TextView trial = (TextView)rootView.findViewById(R.id.trailText);
        sp = (Spinner)rootView.findViewById(R.id.spinner2);
        //tv = (TextView)rootView.findViewById(R.id.simply);
        //adapter = new ArrayAdapter<String>(getActivity(),andr)
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,names);
        sp.setAdapter(adapter);


    //set display button click to show result



    Bundle bundle2= getArguments();


        if(bundle2!=null) {
            //final String text;
            //if(bundle2.containsKey("key1")){
            final String keep = bundle2.getString("key");
            text = keep;
            if(keep.contains("-")){
            text = keep.substring( 0, keep.indexOf("-"));}

            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final SharedPreferences.Editor editor1 = pref.edit();
//            Toolbar mActionBarToolbar;
//            mActionBarToolbar = rootView.findViewById(R.id.toolbar_actionbar);
//            //mActionBarToolbar.setTitle("");
//            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(text);
//            //mActionBarToolbar.setNavigationIcon(R.drawable.abc_ic_arrow_drop_right_black_24dp);
//            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getActivity().onBackPressed();
//
//
//                }
//            });
            //trial.setText(text);

            // }
            final ImageButton button5 = (ImageButton)rootView.findViewById(R.id.favImage);

            //SharedPreferences sharedPrefs = getSharedPreferences("sp_name", MODE_PRIVATE);

            if(pref.contains(text)){
                button5.setImageResource(R.mipmap.filled);

            }

            String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?table1=" + text + "&func=func1";
            //String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?table1=AAPL&func=func1";
            mRequestQueue = Singleton.getInstance(getActivity().getApplicationContext()).getRequestQueue(getActivity().getApplicationContext());
            //mRequestQueue = Volley.newRequestQueue(this);
//                mCache = new DiskBasedCache(getCacheDir(),4*1024*1024);
//                mNetwork = new BasicNetwork(new HurlStack());
//                mRequestQueue = new RequestQueue(mCache,mNetwork);
//                mRequestQueue.start();
            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        final List<String> listfav = new ArrayList<String>();
                        List<String> list = new ArrayList<String>();
                        JSONObject mainObject = new JSONObject(response);
                        String uniObject = mainObject.getString("Time Series (Daily)");
                        JSONObject main2 = new JSONObject(uniObject);
                        Iterator<String> keys = main2.keys();
                        String str_Name = keys.next();
                        int j = 0;
                        String second;
                        String uniName = main2.getString(str_Name);
                        JSONObject main3 = new JSONObject(uniName);
                        String open = main3.getString("1. open");
                        final String close = main3.getString("4. close").trim();
                        String volume = main3.getString("5. volume");
                        String low = main3.getString("3. low");
                        String high = main3.getString("2. high");

                        list.add(text);
                        list.add(open);
                        list.add(close);
                        list.add(close);
                        list.add(volume);
                        list.add(str_Name);
//                        list.add(low);
//                        list.add(high);
                        list.add(low + "-" + high);
                        final float second2;
                        while (keys.hasNext()) {

                            j = j + 1;
                            second = keys.next();

                            if (j == 1) {
                                String uniName1 = main2.getString(second);
                                JSONObject main4 = new JSONObject(uniName1);
                                String second1 = main4.getString("4. close");


                                float third = Float.parseFloat(close);
                                float second3 = third - Float.parseFloat(second1);
                                second2 = Math.round(second3*100)/100;
                                Float.toString(second2);
                                BigDecimal bd = new BigDecimal(Float.toString(second2));
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                //list.add(Float.toString(second2));
                                listfav.add(bd.toString());
                                float five = (second2/third)*100;
                                //float four = Math.round(five*100)/100;
                                BigDecimal bd1 = new BigDecimal(Float.toString(five));
                                bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);

                                //list.add(Float.toString(bd) + "(" + Float.toString(four) + "%)");
                                list.add(bd + "(" + bd1 + "%)");
                                listfav.add(bd1.toString());



                                break;
                            }

                        }
                        //Button button5 = (Button)rootView.findViewById(R.id.favImage);


                        button5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(pref.contains(text)){
                                    button5.setImageResource(R.mipmap.empty);
                                    editor1.remove(text);
                                    editor1.apply();

                                }
                                else {

                                    button5.setImageResource(R.mipmap.filled);

                                    Gson gson = new Gson();
                                    listfav.add(text);
                                    listfav.add(close);
                                    //listfav.add(second2);
                                    String[] arrfav = listfav.toArray(new String[listfav.size()]);
                                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor1 = pref.edit();

                                    String jsonText = gson.toJson(arrfav);
                                    editor1.putString(text, jsonText);
                                    editor1.commit();
                                    //TextView temp5 = (TextView) rootView.findViewById(R.id.letsseelol);



                                }
                            }
                        });


//
//                            }
//                        });



                        //String[] stockArr = new String[list.size()];
                        //stockArr = list.toArray(stockArr);
                        String[] arr = list.toArray(new String[list.size()]);
                        String[] items = res.getStringArray(R.array.items);
                        //ArrayAdapter adapter = new ArrayAdapter<String>(StockInfo1.this,R.layout.my_listview,arr);
                        myListView = (ListView) rootView.findViewById(R.id.myListView);
                        //myListView.setAdapter(adapter);
                        ItemAdapter ItemAdapter = new ItemAdapter(getActivity(), items, arr);
                        myListView.setAdapter(ItemAdapter);
                        bar.setVisibility(View.GONE);
                        //JSONObject jsonObject= new JSONObject(response);
                        //String obj = jsonObject.getJSONObject("Meta Data");
                        //JSONObject obj = jsonObject.getJSONObject("Meta Data");
                        //String color = obj.getString("1. Information");
                        //
                        Log.i(TAG, response);
                        //results.setText(open);

                    } catch (JSONException e) {
                        Log.e(TAG, "Invalid JSON string: ");

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Log.i(TAG, error.toString());
                    if(error instanceof TimeoutError || error instanceof NoConnectionError){
                        TextView error1 = (TextView)rootView.findViewById(R.id.lastbet);
                        error1.setText("Failed to load data");
                        bar.setVisibility(View.GONE);
                        error1.setVisibility(View.VISIBLE);

                    }
                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            mRequestQueue.add(stringRequest);


            final WebView web1 = (WebView) rootView.findViewById(R.id.webViewPrice);
            WebSettings webset = web1.getSettings();
            webset.setJavaScriptEnabled(true);
            web1.loadUrl("file:///android_asset/price.html");
            web1.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView v1, String url) {


                    web1.loadUrl("javascript:makevar('" + text + "')");

                }
            });

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override

                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //use postion value

                    switch (position)

                    {

                        case 0:

                            record = "Price";

                            break;

                        case 1:

                            record = "SMA";

                            break;

                        case 2:

                            record = "EMA";

                            break;

                        case 3:
                            record = "MACD";
                            break;
                        case 4:
                            record = "RSI";
                            break;
                        case  5:
                            record = "ADX";
                            break;
                        case 6:
                            record = "CCI";
                            break;
                        case 7:
                            record = "BBANDS";
                            break;



                    }

                }

                @Override

                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            Button button = (Button) rootView.findViewById(R.id.spinnerButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(record == "Price"){
                    web1.loadUrl("file:///android_asset/price.html");
                    WebSettings webset = web1.getSettings();
                    webset.setJavaScriptEnabled(true);
                    //web1.this.mWebView.loadUrl("file:///android_asset/indicator.html");
                    web1.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView v1, String url) {


                                web1.loadUrl("javascript:makevar('" + text + "')");

                            }



                    });
                }
                    else {
                        web1.loadUrl("file:///android_asset/indicator.html");
                        WebSettings webset = web1.getSettings();
                        webset.setJavaScriptEnabled(true);
                        //web1.this.mWebView.loadUrl("file:///android_asset/indicator.html");
                        web1.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView v1, String url) {


                                web1.loadUrl("javascript:first('" + record + "','" + text + "')");

                            }



                        });
                        //web1.loadUrl("javascript:makevar('" + text + "')");


                    }


                    //tv.setTextSize(18);

                    //tv.setText(record);
                    // Move your green(View v) method logic here instead of calling  green(v)
                }
            });





        }


        return rootView;
    }




//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//        // use rootView or getView()
//
//        Button button = (Button) getView().findViewById(R.id.spinnerButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                tv.setTextSize(18);
//
//                tv.setText(record);
//                // Move your green(View v) method logic here instead of calling  green(v)
//            }
//        });
//    }





}
