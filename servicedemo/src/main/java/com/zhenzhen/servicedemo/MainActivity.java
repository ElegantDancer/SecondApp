package com.zhenzhen.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhenzhen.servicedemo.service.DownLoadBinder;
import com.zhenzhen.servicedemo.service.MyService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity---> ";


    @Bind(R.id.start_service)
    Button startService;
    @Bind(R.id.stop_service)
    Button stopService;
    @Bind(R.id.bind_service)
    Button bindService;
    @Bind(R.id.unbind_service)
    Button unbindService;

    private ServiceConnection serviceConnection;

    private DownLoadBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (DownLoadBinder)service;
                binder.startDownLoad();
                Log.i(TAG, "onServiceConnected");

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected");
            }
        };
    }

    @OnClick({R.id.start_service, R.id.stop_service, R.id.bind_service, R.id.unbind_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service:

                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
                break;
            case R.id.stop_service:

                Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:

                Intent bindService = new Intent(MainActivity.this, MyService.class);
                bindService(bindService, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(serviceConnection);
                break;
        }
    }
}
