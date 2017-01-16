package com.iqiyi.share;

import org.qiyi.android.corejar.deliver.share.ShareBean;
import org.qiyi.android.plugin.share.SharePluginCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.iqiyi.share.ap.ShareAP;
import com.iqiyi.share.qq.ShareQQ;
import com.iqiyi.share.sina.ShareSina;

public class ShareTransferActivity extends Activity {

	private ShareBean shareBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			shareBean = getIntent().getParcelableExtra("shareBean");
		} catch (Exception e) {
			finish();
			return;
		}

		switch (shareBean.getChannelType()) {
		case 2:
			ShareQQ shareQQ = new ShareQQ(this);
			shareQQ.shareToQQ(shareBean);
			break;
		case 3:
			ShareQQ shareQZone = new ShareQQ(this);
			shareQZone.shareToQZone(shareBean);
			break;
		case 4:
			ShareAP shareAp = new ShareAP(this);
			shareAp.share(shareBean);
			break;
		case 7:
			ShareSina shareSina = new ShareSina(this);
			shareSina.share(shareBean);
			break;
		default:
			finish();
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_fail) + "_"
					+ shareBean.getChannelType());
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// QQ分享 QQ未登录的情况
		if (requestCode == 10103 || requestCode == 10104) {
			finish();
			SharePluginCenter.onShareQQorQZoneCallBack(getResources().getString(R.string.sns_share_cancel));
		}
	}

}
