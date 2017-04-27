package com.alipay.share.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.alipay.share.demo.apshare.ShareEntryActivity;

/**
 * Created by linghan.wj on 2015/10/12.
 */
public class SettingActivity extends Activity {
    private EditText etAppId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        etAppId = (EditText) findViewById(R.id.et_appid);
        etAppId.setText(Constants.APP_ID);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppId();
                finish();
            }
        });
    }

    private void saveAppId() {
        String appId = etAppId.getText().toString();
        if (!TextUtils.isEmpty(appId))
            Constants.APP_ID = appId;

        SharedPreferences preferences = getSharedPreferences("share_config", MODE_PRIVATE);
        preferences.edit().putString("appId", Constants.APP_ID).apply();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        Intent intent = new Intent(this, ShareEntryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.finish();
    }
}
