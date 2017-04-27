package com.iqiyi.mutiprocess;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by zhenzhen on 2017/2/9.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        TextView textView = (TextView) findViewById(R.id.sec);
        String str = (String) getIntent().getExtras().get("value");
        if(!TextUtils.isEmpty(str)){
            textView.setText(str + String.valueOf(Utils.NUMBER) + MainActivity.TAG);
        }
    }
}
