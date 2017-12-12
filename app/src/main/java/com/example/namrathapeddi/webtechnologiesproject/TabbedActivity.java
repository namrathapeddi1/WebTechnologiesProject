package com.example.namrathapeddi.webtechnologiesproject;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TabbedActivity extends AppCompatActivity {
    public String text;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        Intent intent = getIntent();
        //String symbol1 = intent.getStringExtra(MainActivity.key)
        Bundle bundle2= getIntent().getExtras();


        if(bundle2!=null) {
            //final String text;
            //if(bundle2.containsKey("key1")){
            final String keep = bundle2.getString("key");
            text = keep;
            if(keep.contains("-")){
                text = keep.substring( 0, keep.indexOf("-"));}
        }

        toolbar.setTitle(text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        //Intent i = getIntent();
        //TabLayout.Tab tab = i.getStringExtra( "SelectedIndex");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //CurrentStock tab1 = new CurrentStock();
        //final Bundle bundle1 = getIntent().getExtras();

        //tab1.setArguments(bundle1);
        //frag.setArguments(bundle);
        //tab1.setArguments(bundle);
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_content,tab1).commit();
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("key1","hey");
//        //if (bundle != null) {
//            //bundle1.putString("key",bundle.toString());
//            CurrentStock frag = new CurrentStock();
        //CurrentStock tab1 = new CurrentStock();
        //tab1.setArguments(getIntent().getExtras());
        //frag.setArguments(bundle);
        //tab1.setArguments(bundle);
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_content,tab1).commit();
//
//            frag.setArguments(bundle1);
        //Bundle bundle = new Bundle();
        //bundle.putString("key1", bundle1.toString());
        //CurrentStock frag = new CurrentStock();


        //History frag1 = new History();
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_content,frag1).commit();
        //
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment fragment = new Fragment();
//        fragmentTransaction.replace(R.id.tabItem, fragment);
//        fragmentTransaction.commit();
        //FragmentTransaction transaction;
        //Fragmentclass frag=new Fragmentclass();

        //fragment.setArguments(bundle);



//        transaction.replace(R.layout.current_stock, frag);
//        transaction.commit();


        //public String myString = "hello";


       // }


        //TabLayout.Tab tab = tabLayout.getTabAt(getIntent().getIntExtra("SelectedIndex"));
        //TabLayout.Tab tab = tabLayout.getTabAt(getIntent());


        //assert tab != null;
        //tab.select();
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();
        private ArrayList<String> mTabHeader;



        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    CurrentStock tab1 = new CurrentStock();
                    tab1.setArguments(getIntent().getExtras());
                    //frag.setArguments(bundle);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.main_content,tab1).commit();

                    return tab1;
                case 1:
                    History tab2 = new History();
                    tab2.setArguments(getIntent().getExtras());
                    return tab2;
                case 2:
                    News tab3 = new News();
                    tab3.setArguments(getIntent().getExtras());
                    return tab3;
                default:
                    return null;


            }

        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){

                case 0:
                    return "CURRENT";
                case 1:
                    return "HISTORY";
                case 3:
                    return "NEWS";
            }
            return null;
        }
    }
}