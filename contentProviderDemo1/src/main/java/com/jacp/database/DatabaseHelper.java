package com.jacp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jacp.demo.provider.Provider;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "jacp_demo.db";
	private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	// 创建programmer表
        db.execSQL("CREATE TABLE " + Provider.ProgrammerColumns.TABLE_NAME + " ("
                + Provider.ProgrammerColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.ProgrammerColumns.NAME + " TEXT,"
                + Provider.ProgrammerColumns.AGE + " INTEGER"
                + ");");
        
        // 创建leader表
        db.execSQL("CREATE TABLE " + Provider.LeaderColumns.TABLE_NAME + " ("
        		+ Provider.LeaderColumns._ID + " INTEGER PRIMARY KEY,"
        		+ Provider.LeaderColumns.NAME + " TEXT,"
        		+ Provider.LeaderColumns.TITLE + " TEXT,"
        		+ Provider.LeaderColumns.LEVEL + " INTEGER"
        		+ ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Provider.ProgrammerColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Provider.LeaderColumns.TABLE_NAME);
        onCreate(db);
    }
}