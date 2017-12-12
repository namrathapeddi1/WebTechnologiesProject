package com.example.namrathapeddi.webtechnologiesproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getName();

    //TextView tv10 = (TextView) findViewById(R.id.tr)
    public RequestQueue mRequestQueue;
    public String[] checkSymbol;
    public String[] checkPrice;
    public String[] checkChange;
    public String[] checkPercent;
    public int i12;
    private StringRequest stringRequest;
    private final Handler handler = new Handler();
    String item1;
    String item2;
    public ListView myListViewGod;
    public ListView myListViewGod1;
    public List<String> listSymbol = new ArrayList<String>();
    public List<String> listPrice = new ArrayList<String>();
    public List<String> listChange = new ArrayList<String>();
    private AutoCompleteTextView input1;

    //private String url ="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=AAPL&apikey=BVFQG1A5Y8XQTOQG";
    //private String url = "http://10.155.93.27/index.php?table1=AAPL&func=func1";

    private DiskBasedCache mCache;
    private com.android.volley.Network mNetwork;
    String cat[] = {"Sort By", "Symbol", "Price", "Change"};
    String order[] = {"Order By", "Ascending", "Descending"};

    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button getQuote = (Button) findViewById(R.id.getQuote);
        final Button clear = (Button) findViewById(R.id.clear);

        //final EditText input1 = (EditText) findViewById(R.id.editText1);
        input1 = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        //AutoCompleteAdapter adapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line);
        //TextView tv13 = (TextView) findViewById(R.id.);
        input1.setThreshold(1);
        input1.setAdapter(adapter);


        getQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input1.getText().toString().trim();
                if (input1.length() == 0) {

                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a Stock name or symbol";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    //toast.setBackgroundResource(R.drawable.toast_9_patch);
                    toast.show();

                }
                if (name.matches("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a Stock name or symbol";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    //toast.setBackgroundResource(R.drawable.toast_9_patch);
                    toast.show();


                } else {


                    Bundle bundle = new Bundle();
                    bundle.putString("key", input1.getText().toString());

                    Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                    startIntent.putExtras(bundle);
                    startActivity(startIntent);

                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input1.setText("");

            }
        });


    }

    private List<Float> getIntegerArray(List<String> stringArray) {
        List<Float> result = new ArrayList<Float>();
        for (String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Float.parseFloat(stringValue));
            } catch (NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }

    private List<String> getStringArray(List<Float> stringArray) {
        List<String> result = new ArrayList<String>();
        for (Float stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Float.toString(stringValue));
            } catch (NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }


    @Override
    protected void onResume() {
        super.onResume();
        int i;
        //for(i=0; i<temp4.size(); i++){
        final Gson gson = new Gson();
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor1 = pref.edit();
//        editor1.clear();
//        editor1.commit();


//                    if(temp4.size()!=0){
//                        tv12.setText(temp4.get(0));
//                    }
        // }
        //for(i=0;i<SharedPreferences.)
        Map<String, ?> keys = pref.getAll();
        //String jsonText="here";
        final List<String> listSymbol = new ArrayList<String>();
        final List<String> listPrice = new ArrayList<String>();
        final List<String> listChange = new ArrayList<String>();
        final List<String> listPercent = new ArrayList<>();
        final List<String> listSymbolNew = new ArrayList<String>();
        final List<String> listPriceNew = new ArrayList<String>();
        final List<String> listChangeNew = new ArrayList<String>();
        final List<String> listPercentNew = new ArrayList<>();


        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String ab = entry.getKey();
            String jsonText = pref.getString(ab, null);
            String[] text = gson.fromJson(jsonText, String[].class);
            listSymbolNew.add(text[2]);
            listPriceNew.add(text[3]);
            listChangeNew.add(text[0]);
            listPercentNew.add("(" + text[1] + "%)");

            //tv12.setText(text[2]);
        }
        Map<String, ?> allEntries1 = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries1.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String ab = entry.getKey();
            String jsonText = pref.getString(ab, null);
            String[] text = gson.fromJson(jsonText, String[].class);
            listSymbol.add(text[2]);
            listPrice.add(text[3]);
            listChange.add(text[0]);
            listPercent.add(text[1]);

            //tv12.setText(text[2]);
        }

        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

        myListViewGod = (ListView) findViewById(R.id.listviewparent);
        registerForContextMenu(myListViewGod);
        ItemAdapter2 ItemAdapterGod = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String[] links = getResources().getStringArray(R.array.link);
                Bundle bundle = new Bundle();
                String k = listSymbol.get(position);
                bundle.putString("key", k);

                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                startIntent.putExtras(bundle);
                startActivity(startIntent);

            }
        });


        myListViewGod.setAdapter(ItemAdapterGod);
        final Spinner sp1 = (Spinner) findViewById(R.id.category);
        final Spinner sp2 = (Spinner) findViewById(R.id.type);

        ArrayAdapter<String> semarrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cat);

        sp1.setAdapter(semarrayAdapter);

        ArrayAdapter<String> brancharrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, order);

        sp2.setAdapter(brancharrayAdapter);


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                final List<String> listSymbol = new ArrayList<String>();
                final List<String> listPrice = new ArrayList<String>();
                final List<String> listChange = new ArrayList<String>();
                final List<String> listPercent = new ArrayList<>();


                Map<String, ?> allEntries = pref.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                    String ab = entry.getKey();
                    String jsonText = pref.getString(ab, null);
                    String[] text = gson.fromJson(jsonText, String[].class);
                    listSymbol.add(text[2]);
                    listPrice.add(text[3]);
                    listChange.add(text[0]);
                    listPercent.add(text[1]);

                    //tv12.setText(text[2]);
                }
                item1 = sp1.getSelectedItem().toString();
                final List<String> listChangeNew = new ArrayList<String>();
                final List<String> listPriceNew = new ArrayList<String>();
                final List<String> listSymbolNew = new ArrayList<String>();
                final List<String> listPercentNew = new ArrayList<>();

                if (item1 != "Sort By" && item2 != "Order By") {

                    if (item1 == "Symbol" && item2 == "Ascending") {
                        final List<String> newList = new ArrayList<>(listSymbol);


                        Collections.sort(newList);
                        for (String temp0 : newList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = listSymbol.indexOf(temp0);
                            String temp7 = listPrice.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8 + "(");
                            listPriceNew.add(temp7);
                            listPercentNew.add(temp9 + "%)");


                        }
                        checkSymbol = newList.toArray(new String[newList.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = newList.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });

                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Symbol" && item2 == "Descending") {
                        final List<String> newList = new ArrayList<>(listSymbol);


                        Collections.sort(newList);
                        Collections.reverse(newList);
                        for (String temp0 : newList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = listSymbol.indexOf(temp0);
                            String temp7 = listPrice.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8 + "(");
                            listPriceNew.add(temp7);
                            listPercentNew.add(temp9 + "%)");


                        }
                        checkSymbol = newList.toArray(new String[newList.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);

                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = newList.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Price" && item2 == "Ascending") {
                        List<String> newList = new ArrayList<>(listPrice);
                        List<Float> PriceSee = getIntegerArray(listPrice);
                        List<Float> resultList = getIntegerArray(newList);


                        Collections.sort(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = PriceSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8 + "(");
                            listSymbolNew.add(temp7);
                            listPercentNew.add(temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);
                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = resultList1.toArray(new String[resultList1.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);

                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Price" && item2 == "Descending") {
                        List<String> newList = new ArrayList<>(listPrice);
                        List<Float> resultList = getIntegerArray(newList);
                        List<Float> PriceSee = getIntegerArray(listPrice);


                        Collections.sort(resultList);
                        Collections.reverse(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = PriceSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8 + "(");
                            listSymbolNew.add(temp7);
                            listPercentNew.add(temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);
                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = resultList1.toArray(new String[resultList1.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Change" && item2 == "Ascending") {
                        List<String> newList = new ArrayList<>(listChange);
                        List<Float> resultList = getIntegerArray(newList);
                        List<Float> ChangeSee = getIntegerArray(listChange);


                        Collections.sort(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = ChangeSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listPrice.get(l);
                            String temp9 = listPercent.get(l);
                            listPriceNew.add(temp8);
                            listSymbolNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);

                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = resultList1.toArray(new String[resultList1.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Change" && item2 == "Descending") {
                        List<String> newList = new ArrayList<>(listChange);
                        List<Float> resultList = getIntegerArray(newList);
                        List<Float> ChangeSee = getIntegerArray(listChange);


                        Collections.sort(resultList);
                        Collections.reverse(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = ChangeSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listPrice.get(l);
                            String temp9 = listPercent.get(l);
                            listPriceNew.add(temp8);
                            listSymbolNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);

                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = resultList1.toArray(new String[resultList1.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }


//

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                final List<String> listSymbol = new ArrayList<String>();
                final List<String> listPrice = new ArrayList<String>();
                final List<String> listChange = new ArrayList<String>();
                final List<String> listPercent = new ArrayList<>();


                Map<String, ?> allEntries = pref.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                    String ab = entry.getKey();
                    String jsonText = pref.getString(ab, null);
                    String[] text = gson.fromJson(jsonText, String[].class);
                    listSymbol.add(text[2]);
                    listPrice.add(text[3]);
                    listChange.add(text[0]);
                    listPercent.add(text[1]);


                    //tv12.setText(text[2]);
                }

                item2 = sp2.getSelectedItem().toString();
                final List<String> listChangeNew = new ArrayList<String>();
                final List<String> listPriceNew = new ArrayList<String>();
                final List<String> listSymbolNew = new ArrayList<String>();
                final List<String> listPercentNew = new ArrayList<>();

                if (item1 != "Sort By" && item2 != "Order By") {
                    if (item1 == "Symbol" && item2 == "Ascending") {
                        final List<String> newList = new ArrayList<>(listSymbol);


                        Collections.sort(newList);
                        for (String temp0 : newList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = listSymbol.indexOf(temp0);
                            String temp7 = listPrice.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);

                            listChangeNew.add(temp8);
                            listPriceNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        checkSymbol = newList.toArray(new String[newList.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = newList.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Symbol" && item2 == "Descending") {
                        final List<String> newList = new ArrayList<>(listSymbol);


                        Collections.sort(newList);
                        Collections.reverse(newList);
                        for (String temp0 : newList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = listSymbol.indexOf(temp0);
                            String temp7 = listPrice.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8);
                            listPriceNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        checkSymbol = newList.toArray(new String[newList.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = newList.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Price" && item2 == "Ascending") {
                        List<String> newList = new ArrayList<>(listPrice);
                        List<Float> PriceSee = getIntegerArray(listPrice);

                        List<Float> resultList = getIntegerArray(listPrice);


                        Collections.sort(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = PriceSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8);
                            listSymbolNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);
                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = resultList1.toArray(new String[resultList1.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Price" && item2 == "Descending") {
                        List<String> newList = new ArrayList<>(listPrice);
                        List<Float> resultList = getIntegerArray(newList);
                        List<Float> PriceSee = getIntegerArray(listPrice);


                        Collections.sort(resultList);
                        Collections.reverse(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = PriceSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listChange.get(l);
                            String temp9 = listPercent.get(l);
                            listChangeNew.add(temp8);
                            listSymbolNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");

                        }
                        List<String> resultList1 = getStringArray(resultList);
                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = resultList1.toArray(new String[resultList1.size()]);
                        checkChange = listChangeNew.toArray(new String[listChangeNew.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Change" && item2 == "Ascending") {
                        List<String> newList = new ArrayList<>(listChange);
                        List<Float> ChangeSee = getIntegerArray(listChange);

                        List<Float> resultList = getIntegerArray(listChange);


                        Collections.sort(resultList);
                        for (Float temp0 : resultList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = ChangeSee.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listPrice.get(l);
                            String temp9 = listPercent.get(l);
                            listPriceNew.add(temp8);
                            listSymbolNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);

                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = newList.toArray(new String[newList.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }
                    if (item1 == "Change" && item2 == "Descending") {
                        List<String> newList = new ArrayList<>(listChange);
                        List<Float> ChangeSee = getIntegerArray(listChange);

                        List<Float> resultList = getIntegerArray(listChange);


                        Collections.sort(newList);
                        Collections.reverse(newList);
                        for (String temp0 : newList) {
                            //System.out.println("fruits " + ++i + " : " + temp);
                            int l = listChange.indexOf(temp0);
                            String temp7 = listSymbol.get(l);
                            String temp8 = listPrice.get(l);
                            String temp9 = listPercent.get(l);
                            listPriceNew.add(temp8);
                            listSymbolNew.add(temp7);
                            listPercentNew.add("(" + temp9 + "%)");


                        }
                        List<String> resultList1 = getStringArray(resultList);
                        checkSymbol = listSymbolNew.toArray(new String[listSymbolNew.size()]);
                        checkPrice = listPriceNew.toArray(new String[listPriceNew.size()]);
                        checkChange = resultList1.toArray(new String[resultList1.size()]);
                        checkPercent = listPercentNew.toArray(new String[listPercentNew.size()]);

                        myListViewGod = (ListView) findViewById(R.id.listviewparent);
                        ItemAdapter2 ItemAdapterGod1 = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
                        myListViewGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //String[] links = getResources().getStringArray(R.array.link);
                                Bundle bundle = new Bundle();
                                String k = listSymbolNew.get(position);
                                bundle.putString("key", k);

                                Intent startIntent = new Intent(getApplicationContext(), TabbedActivity.class);

                                startIntent.putExtras(bundle);
                                startActivity(startIntent);

                            }
                        });
                        myListViewGod.setAdapter(ItemAdapterGod1);


                    }


//
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        //Switch s = (Switch) findViewById(R.id.switch1);
//        if (s != null) {
//            s.setOnCheckedChangeListener(this);
//        }
        //doTheAutoRefresh(listSymbol,listPrice,listChange,listPercent);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //if (v.getId()==R.id.list) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //menu.setHeaderTitle(Countries[info.position]);
        String[] menuItems = getResources().getStringArray(R.array.menu);
        for (int i = 0; i < menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
        //}
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        //
        // TextView tv12 = (TextView) findViewById(R.id.exp);
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];


        String listItemName = checkSymbol[info.position];


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor1 = pref.edit();

        if (menuItemName.equals("Yes")) {
            //tv12.setText(menuItemName);
            editor1.remove(listItemName);
            editor1.apply();
//            int p = listSymbol.indexOf(listItemName);
////            listSymbol.remove(p);
////            listPrice.remove(p);
////            listChange.remove(p);
            Map<String, ?> keys = pref.getAll();
            //String jsonText="here";
            final List<String> listChange = new ArrayList<String>();
            final List<String> listPrice = new ArrayList<String>();
            final List<String> listSymbol = new ArrayList<String>();
            final List<String> listPercent = new ArrayList<>();

            Gson gson = new Gson();

            Map<String, ?> allEntries = pref.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                String ab = entry.getKey();
                String jsonText = pref.getString(ab, null);
                String[] text = gson.fromJson(jsonText, String[].class);
                listSymbol.add(text[2]);
                listPrice.add(text[3]);
                listChange.add(text[0]);
                listPercent.add(text[1]);

                //tv12.setText(text[2]);
            }

            checkSymbol = listSymbol.toArray(new String[listSymbol.size()]);
            checkPrice = listPrice.toArray(new String[listPrice.size()]);
            checkChange = listChange.toArray(new String[listChange.size()]);
            checkPercent = listPercent.toArray(new String[listPercent.size()]);

            myListViewGod = (ListView) findViewById(R.id.listviewparent);
            registerForContextMenu(myListViewGod);
            ItemAdapter2 ItemAdapterGod = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
            myListViewGod.setAdapter(ItemAdapterGod);


        }

        return true;

    }

//    private void doTheAutoRefresh(final List<String> listSymbol,final List<String> listPrice2,final List<String> listChange2,final List<String> listPercent2) {
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final List<String> listChangeNew1 = new ArrayList<String>(listChange2);
//                final List<String> listPriceNew1 = new ArrayList<String>(listPrice2);
//                final List<String> listSymbolNew1 = new ArrayList<String>(listSymbol);
//                final List<String> listPercentNew1 = new ArrayList<>(listPercent2);
//
//
//                i12=0;
//                //for(i12=0;i12<listSymbol.size();i12++){
//                    while(i12<listSymbol.size()-1){
//                        //Log.d("First", i12);
//                        //Log.d("map values", +i12+ "this" + listSymbol.get(i12));
//
//
//                // Write code for your refresh logic
//                String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?table1=" + listSymbol.get(i12) + "&func=func1";
//                //String url = "http://namrathapeddiprojec-env.us-east-2.elasticbeanstalk.com/index.php?table1=AAPL&func=func1";
//                //mRequestQueue = Singleton.getInstance(getApplicationContext()).getRequestQueue(getApplicationContext());
//                        //mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//                mCache = new DiskBasedCache(getCacheDir(),4*1024*1024);
//                mNetwork = new BasicNetwork(new HurlStack());
//                mRequestQueue = new RequestQueue(mCache,mNetwork);
//                mRequestQueue.start();
//
//                stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//
//                        //Log.d("Second",listSymbolNew1.get(i12));
//                        try {
//                           // Log.d("Third",listSymbol.get(i12));
//                            final List<String> listfav = new ArrayList<String>();
//                            List<String> list = new ArrayList<String>();
//                            JSONObject mainObject = new JSONObject(response);
//                            String uniObject = mainObject.getString("Time Series (Daily)");
//                            JSONObject main2 = new JSONObject(uniObject);
//                            Iterator<String> keys = main2.keys();
//                            String str_Name = keys.next();
//                            int j = 0;
//                            String second;
//                            String uniName = main2.getString(str_Name);
//                            JSONObject main3 = new JSONObject(uniName);
//                            String open = main3.getString("1. open");
//                            final String close = main3.getString("4. close").trim();
//                            String volume = main3.getString("5. volume");
//                            String low = main3.getString("3. low");
//                            String high = main3.getString("2. high");
//                            //listSymbolNew1.add(listSymbol.get(i12));
//                            //listPriceNew1.add(close);
//                            //listPriceNew1.add(close);
//
//                            //Log.d("map values", listSymbol.get(i12) + ": " + close);
//
//                            listPriceNew1.set(i12,close);
//                            //Log.d("map values", listSymbol.get(i12) + ": " + i12 + listPriceNew1);
//
//
//
////
//                            final float second2;
//                            while (keys.hasNext()) {
//
//                                j = j + 1;
//                                second = keys.next();
//
//                                if (j == 1) {
//                                    String uniName1 = main2.getString(second);
//                                    JSONObject main4 = new JSONObject(uniName1);
//                                    String second1 = main4.getString("4. close");
//
//
//                                    float third = Float.parseFloat(close);
//                                    float second3 = third - Float.parseFloat(second1);
//                                    second2 = Math.round(second3 * 100) / 100;
//                                    Float.toString(second2);
//                                    //list.add(Float.toString(second2));
//                                    listfav.add(Float.toString(second2));
//                                    float five = (second2 / third) * 100;
//                                    float four = Math.round(five * 100) / 100;
////                                    list.add(Float.toString(second2) + "(" + Float.toString(four) + "%)");
////                                    listfav.add(Float.toString(four));
//                                    //listChangeNew1.add(Float.toString(second2));
//                                    //listChangeNew1.set(i12,Float.toString(second2));
//                                    listChangeNew1.set(i12,Float.toString(second2));
//                                    //listPercentNew1.add(Float.toString(four));
//                                    //listPercentNew1.set(i12,Float.toString(four));
//                                    listPercentNew1.set(i12,Float.toString(four));
//                                    //listSymbolNew1.add("AAP");
//
//                                    break;
//                                }
//
//                            }
//                            checkSymbol = listSymbol.toArray(new String[listSymbol.size()]);
//                            checkPrice = listPriceNew1.toArray(new String[listPriceNew1.size()]);
//                            checkChange = listChangeNew1.toArray(new String[listChangeNew1.size()]);
//                            checkPercent = listPercentNew1.toArray(new String[listPercentNew1.size()]);
//
//
//
//                            myListViewGod = (ListView) findViewById(R.id.listviewparent);
//                            registerForContextMenu(myListViewGod);
//                            ItemAdapter2 ItemAdapterGod = new ItemAdapter2(getApplicationContext(), checkSymbol, checkPrice, checkChange, checkPercent);
//                            myListViewGod.setAdapter(ItemAdapterGod);
//
//
//
//                        } catch (JSONException e) {
//                            Log.e(TAG, "Invalid JSON string: ");
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.i(TAG, error.toString());
//                    }
//                });
//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        //mRequestQueue.add(stringRequest);
//                        mRequestQueue.stop();
//
//
//
//                        i12 = i12+1;
//
//                }
//
//
//
//
//
//
//                doTheAutoRefresh(listSymbol,listPriceNew1,listChangeNew1,listPercentNew1);
//             }
//        }, 15000);
//    }
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
//                Toast.LENGTH_SHORT).show();
//        if(isChecked) {
//            doTheAutoRefresh();
//        } else {
//            //do stuff when Switch if OFF
//        }
//    }
}








