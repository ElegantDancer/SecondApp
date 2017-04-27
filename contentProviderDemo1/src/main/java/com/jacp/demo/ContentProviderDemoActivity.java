package com.jacp.demo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jacp.demo.provider.Provider;
import com.jacp.pojos.Leader;
import com.jacp.pojos.Programmer;

public class ContentProviderDemoActivity extends Activity {
	
	private static final String TAG = "ProviderActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        testLeader();
        testProgrammer();
    }
    
    private void testProgrammer() {
    	Programmer p = new Programmer();
        p.name = "jacp";
        p.age = 99;
        int id = insertProgrammer(p);
        queryProgrammer(id);
    }
    
    private int insertProgrammer(Programmer programmer) {
    	ContentValues values = new ContentValues();
    	values.put(Provider.ProgrammerColumns.NAME, programmer.name);
    	values.put(Provider.ProgrammerColumns.AGE, programmer.age);
    	Uri uri = getContentResolver().insert(Provider.ProgrammerColumns.CONTENT_URI, values);
    	Log.i(TAG, "insert uri="+uri);
    	String lastPath = uri.getLastPathSegment();
    	if (TextUtils.isEmpty(lastPath)) {
    		Log.i(TAG, "insert failure!");
    	} else {
    		Log.i(TAG, "insert success! the id is " + lastPath);
    	}
    	
    	return Integer.parseInt(lastPath);
    }
    
    private void queryProgrammer(int id) {
    	Cursor c = getContentResolver().query(Provider.ProgrammerColumns.CONTENT_URI, new String[] { Provider.ProgrammerColumns.NAME, Provider.ProgrammerColumns.AGE }, Provider.ProgrammerColumns._ID + "=?", new String[] { id + "" }, null);
    	if (c != null && c.moveToFirst()) {
    		Programmer p = new Programmer();
    		p.name = c.getString(c.getColumnIndexOrThrow(Provider.ProgrammerColumns.NAME));
    		p.age = c.getInt(c.getColumnIndexOrThrow(Provider.ProgrammerColumns.AGE));
    		Log.i(TAG, "programmer.name="+p.name+"---programmer.age="+p.age);
    	} else {
    		Log.i(TAG, "query failure!");
    	}
    }
    
    private void testLeader() {
    	Leader leader = new Leader();
    	leader.name = "jacky";
    	leader.title = "CTO";
    	leader.level = 30;
    	int id = insertLeader(leader);
    	queryLeader(id);
    }
    
    private int insertLeader(Leader leader) {
    	ContentValues values = new ContentValues();
    	values.put(Provider.LeaderColumns.NAME, leader.name);
    	values.put(Provider.LeaderColumns.TITLE, leader.title);
    	values.put(Provider.LeaderColumns.LEVEL, leader.level);
    	Uri uri = getContentResolver().insert(Provider.LeaderColumns.CONTENT_URI, values);
    	Log.i(TAG, "insert uri="+uri);
    	String lastPath = uri.getLastPathSegment();
    	if (TextUtils.isEmpty(lastPath)) {
    		Log.i(TAG, "insert failure!");
    	} else {
    		Log.i(TAG, "insert success! the id is " + lastPath);
    	}
    	
    	return Integer.parseInt(lastPath);
    }
    
    private void queryLeader(int id) {
    	Cursor c = getContentResolver().query(Provider.LeaderColumns.CONTENT_URI, new String[] { Provider.LeaderColumns.NAME, Provider.LeaderColumns.TITLE, Provider.LeaderColumns.LEVEL }, Provider.LeaderColumns._ID + "=?", new String[] { id + "" }, null);
    	if (c != null && c.moveToFirst()) {
    		Leader leader = new Leader();
    		leader.name = c.getString(c.getColumnIndexOrThrow(Provider.LeaderColumns.NAME));
    		leader.title = c.getString(c.getColumnIndexOrThrow(Provider.LeaderColumns.TITLE));
    		leader.level = c.getInt(c.getColumnIndexOrThrow(Provider.LeaderColumns.LEVEL));
    		Log.i(TAG, "leader.name="+leader.name+"---leader.title="+leader.title+"---leader.level="+leader.level);
    	} else {
    		Log.i(TAG, "query failure!");
    	}
    }
}