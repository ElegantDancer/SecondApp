package com.iqiyi.share.ap;

import org.qiyi.android.corejar.deliver.share.ShareBean;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APTextObject;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.iqiyi.share.util.ShareConstans;

public class ShareAP {

	public static String ALPAY_SHARE_APP_ID;
	private Activity mActivity;
	private IAPApi api;

	public ShareAP(Activity mActivity) {
		this.mActivity = mActivity;
		if (mActivity.getPackageName().equals("tv.pps.mobile")) {
			ALPAY_SHARE_APP_ID = ShareConstans.AP_SHARE_APP_ID_PPS;
		} else {
			ALPAY_SHARE_APP_ID = ShareConstans.AP_SHARE_APP_ID_IQY;
		}
		api = APAPIFactory.createZFBApi(mActivity.getApplicationContext(), ALPAY_SHARE_APP_ID, false);
	}

	public void share(ShareBean shareBean) {
		switch (shareBean.getShareType()) {
		case 0:
		case 1:
			shareWebpage(shareBean);
			break;
		case 2:
			shareText(shareBean.getTitle());
			break;
		}
		mActivity.finish();
	}

	private void shareWebpage(ShareBean shareBean) {
		APWebPageObject webPageObject = new APWebPageObject();
		webPageObject.webpageUrl = shareBean.getUrl();
		APMediaMessage webMessage = new APMediaMessage();
		webMessage.mediaObject = webPageObject;
		if (!TextUtils.isEmpty(shareBean.getAliTitle())) {
			webMessage.title = shareBean.getAliTitle();
		} else {
			webMessage.title = shareBean.getTitle();
		}
		if (!TextUtils.isEmpty(shareBean.getAliText())) {
			webMessage.description = shareBean.getAliText();
		} else {
			webMessage.description = shareBean.getDes();
		}
		webMessage.thumbUrl = shareBean.getBitmapUrl();
		SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
		webReq.message = webMessage;
		webReq.transaction = buildTransaction("webpage");
		api.sendReq(webReq);
	}

	private void shareText(String title) {
		APTextObject textObject = new APTextObject();
		textObject.text = title;
		APMediaMessage mediaMessage = new APMediaMessage();
		mediaMessage.mediaObject = textObject;
		SendMessageToZFB.Req req = new SendMessageToZFB.Req();
		req.message = mediaMessage;
		api.sendReq(req);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

}
