package com.zhenzhen.datestorage.sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by zhenzhen on 2017/1/10.
 */

public class SpTest {

    /**
     * 存储的都是 Map 对象
     * @param context
     * @param content
     * @param activity
     */

    public void addToSP(Context context, String content, Activity activity){

        /**
         * 第一种方法 可以指定名字的那种
         */
        context.getSharedPreferences("", Context.MODE_APPEND);

        /**
         * 第二种方法  不可以指定名字  只得到一个默认的
         */
        PreferenceManager.getDefaultSharedPreferences(context);

        /**
         * 貌似和第一种方法类似，查看源码可以知道  以  className为 sharedPreference的名字
         *
         * public SharedPreferences getPreferences(int mode) {
         *return getSharedPreferences(getLocalClassName(), mode);
         *}
         */
        activity.getPreferences(Context.MODE_APPEND);


        /**
         * 需要修改的时候
         */
        SharedPreferences.Editor editor = context.getSharedPreferences("", Context.MODE_APPEND).edit();
        editor.putBoolean("", true);

        editor.commit();

    }
}
