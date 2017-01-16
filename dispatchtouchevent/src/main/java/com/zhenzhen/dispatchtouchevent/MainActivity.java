package com.zhenzhen.dispatchtouchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            URL url = new URL("");
//            url.getHost();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }
}
