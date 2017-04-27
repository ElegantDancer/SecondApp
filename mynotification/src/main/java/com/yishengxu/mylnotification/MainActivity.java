package com.yishengxu.mylnotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;


public class MainActivity extends Activity {

    private int NOTIFICATION_ID_BASIC = 1;
    private int NOTIFICATION_ID_COLLAPSE = 2;
    private int NOTIFICATION_ID_HEADSUP = 3;
    private int NOTIFICATION_ID_VISIBILITY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String s = "{\n" +
                "  \"desc\": \"OK\",\n" +
                "  \"status\": 1000,\n" +
                "  \"data\": {\n" +
                "    \"wendu\": \"22\",\n" +
                "    \"ganmao\": \"风较大，较易发生感冒，注意防护。\",\n" +
                "    \"forecast\": [\n" +
                "      {\n" +
                "        \"fengxiang\": \"北风\",\n" +
                "        \"fengli\": \"5-6级\",\n" +
                "        \"high\": \"高温 24℃\",\n" +
                "        \"type\": \"晴\",\n" +
                "        \"low\": \"低温 11℃\",\n" +
                "        \"date\": \"3日星期六\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"fengxiang\": \"北风\",\n" +
                "        \"fengli\": \"4-5级\",\n" +
                "        \"high\": \"高温 19℃\",\n" +
                "        \"type\": \"晴\",\n" +
                "        \"low\": \"低温 8℃\",\n" +
                "        \"date\": \"4日星期日\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"fengxiang\": \"无持续风向\",\n" +
                "        \"fengli\": \"微风\",\n" +
                "        \"high\": \"高温 21℃\",\n" +
                "        \"type\": \"晴\",\n" +
                "        \"low\": \"低温 9℃\",\n" +
                "        \"date\": \"5日星期一\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"fengxiang\": \"无持续风向\",\n" +
                "        \"fengli\": \"微风\",\n" +
                "        \"high\": \"高温 21℃\",\n" +
                "        \"type\": \"多云\",\n" +
                "        \"low\": \"低温 10℃\",\n" +
                "        \"date\": \"6日星期二\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"fengxiang\": \"无持续风向\",\n" +
                "        \"fengli\": \"微风\",\n" +
                "        \"high\": \"高温 24℃\",\n" +
                "        \"type\": \"晴\",\n" +
                "        \"low\": \"低温 12℃\",\n" +
                "        \"date\": \"7日星期三\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"fengxiang\": \"无持续风向\",\n" +
                "        \"fengli\": \"微风\",\n" +
                "        \"high\": \"高温 23℃\",\n" +
                "        \"type\": \"晴\",\n" +
                "        \"low\": \"低温 11℃\",\n" +
                "        \"date\": \"8日星期四\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"yesterday\": {\n" +
                "      \"fl\": \"微风\",\n" +
                "      \"fx\": \"无持续风向\",\n" +
                "      \"high\": \"高温 23℃\",\n" +
                "      \"type\": \"晴\",\n" +
                "      \"low\": \"低温 12℃\",\n" +
                "      \"date\": \"2日星期五\"\n" +
                "    },\n" +
                "    \"aqi\": \"59\",\n" +
                "    \"city\": \"北京\"\n" +
                "  }\n" +
                "}";
    }


    /**
     * 基本的notification
     *
     * @param view
     */
    public void basicNotify(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.baidu.com"));
        // 构造PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        // 创建Notification对象
        Notification.Builder builder = new Notification.Builder(this);
        // 设置Notification的各种属性
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Basic Notifications");
        builder.setContentText("I am a basic notification");
        builder.setSubText("it is really basic");
        // 通过NotificationManager来发出Notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID_BASIC,
                builder.build());
    }


    /**
     * 主要是 利用 RemoteViews 创建 notification.contentView  和 notification.bigContentView
     *
     * @param view
     */
    public void collapsedNotify(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.sina.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_launcher));
        // 通过RemoteViews来创建自定义的Notification视图
        RemoteViews contentView =
                new RemoteViews(getPackageName(),
                        R.layout.notification);
        contentView.setTextViewText(R.id.textView,
                "show me when collapsed");

        Notification notification = builder.build();
        notification.contentView = contentView;
        // 通过RemoteViews来创建自定义的Notification视图
        RemoteViews expandedView =
                new RemoteViews(getPackageName(),
                        R.layout.notification_expanded);
        notification.bigContentView = expandedView;

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_COLLAPSE, notification);
    }


    /**
     * 悬挂式 notification  类似于  微信的聊天消息
     *
     * @param view
     */

    public void headsupNotify(View view) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Headsup Notification")
                .setContentText("I am a Headsup notification.");

        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentText("Heads-Up Notification on Android 5.0")
                .setFullScreenIntent(pendingIntent, true);

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_HEADSUP, builder.build());
    }


    /**
     * 带有等级的 notification
     * 1. Notification.VISIBILITY_PUBLIC  任何情况下都显示
     * 2. Notification.VISIBILITY_PRIVATE  只有在没有锁屏的时候 才显示
     * 3. Notification.VISIBILITY_SECRET 在没有 pin passport 和安全锁的情况下 才会显示
     *
     * @param view
     */
    public void visibilityNotify(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(
                R.id.visibility_radio_group);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Notification for Visibility Test");
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_public:
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setContentText("Public");
                builder.setSmallIcon(R.drawable.ic_public);
                break;
            case R.id.radio_button_private:
                builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                builder.setContentText("Private");
                builder.setSmallIcon(R.drawable.ic_private);
                break;
            case R.id.radio_button_secret:
                builder.setVisibility(Notification.VISIBILITY_SECRET);
                builder.setContentText("Secret");
                builder.setSmallIcon(R.drawable.ic_secret);
                break;
        }
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_VISIBILITY, builder.build());
    }
}
