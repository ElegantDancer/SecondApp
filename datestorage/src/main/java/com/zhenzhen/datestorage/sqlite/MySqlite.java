package com.zhenzhen.datestorage.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhenzhen on 2017/1/10.
 */

public class MySQLite extends SQLiteOpenHelper {

    /**
     * integer  表示整数类型
     * real     表示浮点数类型
     * text     表示字符串类型
     * blob     二进制类型
     */

    private final String CREATE_TABLE = "create table book (" +
            "id integer primary key autoincrement" +
            "name text" +
            "price real)";

    private String QUERY = "select * from book where name = haha";

    private String UPDATE = "update (id, name , price) values (?, ?, ?)";

    private final String INSERT = "insert into Book(name, price) values(?, ?)";

    private final String ALTER = "alter table Book rename to BookList";

    private final String ALTER_ADD = "alter table Book add columns sex text default girl";


    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("insert into Book(name, price) values(?, ?)", new String[]{"The big Talk", "120.99"});
        db.execSQL("update Book set price = ? where name = ?", new String[]{"50.99", "The big Talk"});
        db.execSQL("delete from Book where price > ?", new String[]{"50.0"});
        db.rawQuery("select * from Book", null);
    }
}
