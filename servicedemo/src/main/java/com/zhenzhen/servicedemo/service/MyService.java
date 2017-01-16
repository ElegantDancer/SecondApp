package com.zhenzhen.servicedemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhenzhen on 2017/1/12.
 */

public class MyService extends Service {

    private final String TAG = "MyService-----> ";

    private DownLoadBinder downLoadBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "service  onBind");

        return downLoadBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i(TAG, "service  onUnBind");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service  onCreate");
        downLoadBinder = new DownLoadBinder();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "startCommand");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "service  onDestory");

        super.onDestroy();
    }
}
