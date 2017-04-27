package com.alipay.share.demo.apshare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.alipay.share.demo.*;
import com.alipay.share.sdk.openapi.*;

public class ShareEntryActivity extends Activity implements View.OnClickListener, IAPAPIEventHandler {
    private IAPApi api;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.go).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
        findViewById(R.id.start_zfb).setOnClickListener(this);
        findViewById(R.id.check_version).setOnClickListener(this);
        initAppId();
        api = APAPIFactory.createZFBApi(getApplicationContext(), Constants.APP_ID, false);
        Intent intent = getIntent();
        api.handleIntent(intent, this);
    }

    private void initAppId() {
        SharedPreferences preferences = getSharedPreferences("share_config",MODE_PRIVATE);
        Constants.APP_ID = preferences.getString("appId",Constants.APP_ID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go:
                Intent intent = new Intent(this, SendToZFBActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.start_zfb:
                api.openZFBApp();
                break;
            case R.id.check_version:
                Toast.makeText(this, "当前支付宝的版本号为" + api.getZFBVersionCode()
                        + "\n是否支持分享 " + api.isZFBSupportAPI(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                startActivity(new Intent(this, SettingActivity.class));
                finish();
                break;
            default:
                break;

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result;

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                result = R.string.errcode_send_fail;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
