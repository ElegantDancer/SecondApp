package com.iqiyi.share.qq;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.common.AssistActivity;

/**
 * Created by maoxiang on 2015/7/29.
 */
public class CustomAssistActivity extends AssistActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Bundle shareQQBundle = getIntent().getParcelableExtra("shareQQBundle");
			Intent intent = getIntent();
			intent.putExtras(shareQQBundle);
			onNewIntent(intent);
		} catch (Exception e) {
			finish();
		}
	}
}
