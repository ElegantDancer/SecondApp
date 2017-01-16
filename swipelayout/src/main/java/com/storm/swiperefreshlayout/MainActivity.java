/*
 * Created by Storm Zhang, Mar 31, 2014.
 */

package com.storm.swiperefreshlayout;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author:
 * @description  :stormzhang
 * @web: http://stormzhang.com/android/2014/10/27/android-swiperefreshlayout/
 * @date  :2015年1月19日
 */
public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	private ArrayList<String> list = new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
		// 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
		mSwipeLayout.setProgressBackgroundColor(R.color.red);
		mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
	}

	private ArrayList<String> getData() {
		list.add("Hello");
		list.add("This is stormzhang");
		list.add("An Android Developer");
		list.add("Love Open Source");
		list.add("My GitHub: stormzhang");
		list.add("weibo: googdev");
		return list;
	}

	/* 
	 * 监听器SwipeRefreshLayout.OnRefreshListener中的方法，当下拉刷新后触发
	 */
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 停止刷新
				mSwipeLayout.setRefreshing(false);
			}
		}, 5000); // 5秒后发送消息，停止刷新
	}
}
