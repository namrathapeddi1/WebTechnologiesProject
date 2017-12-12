package com.example.namrathapeddi.webtechnologiesproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


/**
 * Created by Namratha Peddi on 11/23/2017.
 */

public class History extends Fragment {
    public String text;
    public String symbol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history, container, false);
        Bundle bundle2= getArguments();
        if(bundle2!=null) {
            final String keep = bundle2.getString("key");
            symbol = keep;
            if(keep.contains("-")){
                symbol = keep.substring( 0, keep.indexOf("-"));}
            final WebView web1 = (WebView) rootView.findViewById(R.id.webView1);
            WebSettings webset = web1.getSettings();
            webset.setJavaScriptEnabled(true);
            web1.loadUrl("file:///android_asset/history.html");
            web1.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView v1, String url) {


                    web1.loadUrl("javascript:makevar('" + symbol + "')");

                }
            });
            //TextView trial1 = (TextView)rootView.findViewById(R.id.historytrial);
            //trial1.setText("NO");
        }
            return rootView;

    }
}
