package com.zhenzhen.bitmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
//        int outWidth = options.outWidth;
//        int outHeight = options.outHeight;
//        String imageType = options.outMimeType;
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

//        ByteArrayOutputStream ous = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, ous);
//        byte[] bytes1 = ous.toByteArray();
//        Log.i("------------>", "byte1 ===" + String.valueOf(bytes1.length));

        ByteArrayOutputStream ous2 = new ByteArrayOutputStream();
        bmp2.compress(Bitmap.CompressFormat.PNG, 100, ous2);
        byte[] bytes2 = ous2.toByteArray();

        Log.i("------------>", "byte1 ===" + String.valueOf(bytes2.length));
    }
}
