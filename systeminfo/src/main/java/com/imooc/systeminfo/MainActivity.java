package com.imooc.systeminfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 这个只是把系统信息打印出来而已
         */
        TextView textView = (TextView) findViewById(R.id.text);
        String systemInfoStr = SystemInfoTools.getBuildInfo();
        systemInfoStr += "-------------------------------------\r\n";
        systemInfoStr += SystemInfoTools.getSystemPropertyInfo();
        textView.setText(systemInfoStr);
    }
}
