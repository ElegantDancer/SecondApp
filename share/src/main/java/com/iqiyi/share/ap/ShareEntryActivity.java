package com.iqiyi.share.ap;

import org.qiyi.android.plugin.share.SharePluginCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.BaseReq;
import com.alipay.share.sdk.openapi.BaseResp;
import com.alipay.share.sdk.openapi.IAPAPIEventHandler;
import com.alipay.share.sdk.openapi.IAPApi;
import com.iqiyi.share.R;
import com.iqiyi.share.util.ShareConstans;

public class ShareEntryActivity extends Activity implements IAPAPIEventHandler {

	public static String ALPAY_SHARE_APP_ID;
	private IAPApi api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getPackageName().equals("tv.pps.mobile")) {
			ALPAY_SHARE_APP_ID = ShareConstans.AP_SHARE_APP_ID_PPS;
		} else {
			ALPAY_SHARE_APP_ID = ShareConstans.AP_SHARE_APP_ID_IQY;
		}
		api = APAPIFactory.createZFBApi(getApplicationContext(), ALPAY_SHARE_APP_ID, false);

		Intent shareRespIntent;
		try {
			shareRespIntent = getIntent().getParcelableExtra("shareRespIntent");
		} catch (Exception e) {
			finish();
			return;
		}
		if (shareRespIntent != null) {
			api.handleIntent(shareRespIntent, this);
		} else {
			finish();
		}
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	/**
	 * 分享结果回调信息处理
	 */
	@Override
	public void onResp(BaseResp baseResp) {
		switch (baseResp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_success));
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_cancel));
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_fail) + ":AUTH_DENIED");
			break;
		case BaseResp.ErrCode.ERR_SENT_FAILED:
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_fail));
			break;
		default:
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_fail));
			break;
		}
		finish();
	}
}
