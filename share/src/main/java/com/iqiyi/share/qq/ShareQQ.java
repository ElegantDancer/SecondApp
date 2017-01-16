package com.iqiyi.share.qq;

import java.io.File;
import java.util.ArrayList;

import org.qiyi.android.corejar.deliver.share.ShareBean;
import org.qiyi.android.plugin.share.SharePluginCenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.iqiyi.share.R;
import com.iqiyi.share.util.ShareConstans;
import com.iqiyi.share.util.ShareUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class ShareQQ {

	public static String QQ_SHARE_APP_ID;
	private Activity mActivity;
	private Tencent mTencent;
	private BaseUiListener listener = new BaseUiListener();
	
	private final int QQ_TITLE_LENGTH = 30;
	private final int QQ_DES_LENGTH = 40;

	private final int QQZONE_TITLE_LENGTH = 200;
	private final int QQZONE_DES_LENGTH = 600;
	
	public ShareQQ(Activity mActivity) {
		this.mActivity = mActivity;
		if (mActivity.getPackageName().equals("tv.pps.mobile")) {
			QQ_SHARE_APP_ID = ShareConstans.QQ_SHARE_APP_ID_PPS;
		} else {
			QQ_SHARE_APP_ID = ShareConstans.QQ_SHARE_APP_ID_IQY;
		}
		mTencent = Tencent.createInstance(QQ_SHARE_APP_ID, mActivity);
	}

	/**
	 * 分享到QQ
	 * @param shareBean
	 */
	public void shareToQQ(ShareBean shareBean) {
		switch (shareBean.getShareType()) {
		case 0:
		case 1:
		case 2:
			shareVideoToQQ(mActivity, shareBean);
			break;
		case 3:
		case 4:
			shareImageToQQ(mActivity, shareBean);
			break;
		default:
			break;
		}
	}

	private void shareImageToQQ(Activity activity, ShareBean shareBean){
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
		String path = "";// todo shareBean里面需要加字段
		File file = new File(path);
		if(file.exists()){
			params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
		}else{
			return;
		}
		mTencent.shareToQQ(activity, params, listener);
	}
	
	private void shareVideoToQQ(Activity activity, ShareBean shareBean){
		
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		String title = shareBean.getTitle();
		if (!TextUtils.isEmpty(shareBean.getQqTitle())) {
			title = shareBean.getQqTitle();
		}
		if(title.length() > QQ_TITLE_LENGTH){
			title = title.substring(0, QQ_TITLE_LENGTH);
		}
		
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareBean.getUrl());
		String text = shareBean.getDes();
		if (!TextUtils.isEmpty(shareBean.getQqText())) {
			text = shareBean.getQqText();
		}
		if(text.length() > QQ_DES_LENGTH){
			text = text.substring(0, QQ_DES_LENGTH);
		}
		
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareBean.getBitmapUrl());
		mTencent.shareToQQ(activity, params, listener);
	}
	
	
	/**
	 * 分享到QQ空间
	 * @param shareBean
	 */
	public void shareToQZone(ShareBean shareBean) {
		
		switch (shareBean.getShareType()) {
		case 0:
		case 1:
		case 2:
			shareVideoToQQZONE(mActivity, shareBean);
			break;
		case 3:
		case 4:
			shareImageToQQZONE(mActivity, shareBean);
			break;
		default:
			break;
		}
	}
	
	private void shareImageToQQZONE(Activity activity, ShareBean shareBean){
		
	}
	
	private void shareVideoToQQZONE(Activity activity, ShareBean shareBean){
		
		final Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		String title;
		if (!TextUtils.isEmpty(shareBean.getZoneTitle())) {
			title = shareBean.getZoneTitle();
		} else if (!ShareUtils.isVVIsZero(shareBean.getVv())) {
			//title = mActivity.getResources().getString(R.string.sns_share_vv, shareBean.getVv()) + shareBean.getTitle();
			title = mActivity.getResources().getString(R.string.sns_share_everyone_is_watching) + shareBean.getTitle();
		} else {
			title = shareBean.getTitle();
		}
		
		if(title.length() > QQZONE_TITLE_LENGTH){
			title = title.substring(0, QQZONE_TITLE_LENGTH);
		}
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareBean.getUrl());
		String text = shareBean.getDes();
		if (!TextUtils.isEmpty(shareBean.getZoneText())) {
			text = shareBean.getZoneText();
		}
		
		if(text.length() > QQZONE_DES_LENGTH){
			text = text.substring(0, QQZONE_DES_LENGTH);
		}
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, text);
		ArrayList<String> imageUrls = new ArrayList<String>();
		imageUrls.add(shareBean.getBitmapUrl());
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
		mTencent.shareToQzone(mActivity, params, listener);
	}
	

	private class BaseUiListener implements IUiListener {
		@Override
		public void onError(UiError e) {
			mActivity.finish();
			SharePluginCenter.onShareQQorQZoneCallBack(mActivity.getResources().getString(R.string.sns_share_fail) + "："
					+ e.errorMessage);
		}

		@Override
		public void onCancel() {
			mActivity.finish();
			SharePluginCenter.onShareQQorQZoneCallBack(mActivity.getResources().getString(R.string.sns_share_cancel));
		}

		@Override
		public void onComplete(Object obj) {
			mActivity.finish();
			SharePluginCenter.onShareQQorQZoneCallBack(mActivity.getResources().getString(R.string.sns_share_success));
		}
	}

}
