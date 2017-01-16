package com.iqiyi.share.sina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.iqiyi.share.util.ShareConstans;
import com.iqiyi.share.R;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

import org.qiyi.android.plugin.share.SharePluginCenter;


/**
 * Created by zhaozhenzhen on 2016/8/25.
 */
public class SinaEntryActivity extends Activity implements IWeiboHandler.Response{

    private IWeiboShareAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String SINA_SHARE_APP_KEY;
        if (this.getPackageName().equals("tv.pps.mobile")) {
        	SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_PPS;
		} else {
			SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_IQY;
		}
        api = WeiboShareSDK.createWeiboAPI(this, SINA_SHARE_APP_KEY);
        
        try {
        	Bundle shareQQBundle = getIntent().getParcelableExtra("shareQQBundle");
     		Intent intent = getIntent();
     		intent.putExtras(shareQQBundle);
     		onNewIntent(intent);
		} catch (Exception e) {
			finish();
		}
    }

    @Override
    protected void onNewIntent(Intent intent) {
    	super.onNewIntent(intent);
        try{
            api.handleWeiboResponse(intent, this);
        }catch (Exception ex){
            finish();
        }
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
    			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_success));
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
    			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_cancel));
                break;
            default:
    			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_fail));
                break;
        }
        finish();
    }
}
