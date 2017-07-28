package com.good.gd.example.skeleton;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.good.gd.GDAndroid;

/**
 * Created by developer01 on 12/06/2017.
 */
public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GDAndroid.getInstance().activityInit(this);
        setContentView(R.layout.activity_third);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://mail.google.com/mail/u/0/#inbox");
    }
}
