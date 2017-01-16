package com.zhenzhen.servicedemo.service;

import android.os.Binder;
import android.util.Log;

/**
 * Created by zhenzhen on 2017/1/12.
 */

public class DownLoadBinder extends Binder {

    private final String TAG = "DownLoadBinder---> ";

    public void startDownLoad(){
        Log.i(TAG, "startDownLoad");
    }

    public void getPorgress(){
        Log.i(TAG, "progress is on");
    }
}
