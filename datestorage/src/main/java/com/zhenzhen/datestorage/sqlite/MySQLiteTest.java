package com.zhenzhen.datestorage.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

/**
 * Created by zhenzhen on 2017/1/10.
 */

public class MySQLiteTest extends Activity{

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }


    private void createDB(){

        /**
         * 第一次 new的时候  如果 这个 数据库不存在 就去调用 MySQLite 的 onCreate方法，否则不调用
         *
         * 数据库名字  和   数据库下面的表  分清楚
         */
        MySQLite sqLite = new MySQLite(mContext, "firstdb", null, 1);
        SQLiteDatabase database = sqLite.getWritableDatabase();
    }
}
