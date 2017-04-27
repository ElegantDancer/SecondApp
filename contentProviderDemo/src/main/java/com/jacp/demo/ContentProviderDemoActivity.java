package com.jacp.demo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jacp.demo.provider.Provider;
import com.jacp.pojos.Person;

public class ContentProviderDemoActivity extends Activity {
	
	private static final String TAG = "ProviderActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        test();
    }
    
    private void test() {
    	Person p = new Person();
        p.name = "jacp";
        p.age = 99;
        int id = insert(p);
        query(id);
    }
    
    private int insert(Person person) {
    	ContentValues values = new ContentValues();
    	values.put(Provider.PersonColumns.NAME, person.name);
    	values.put(Provider.PersonColumns.AGE, person.age);
    	Uri uri = getContentResolver().insert(Provider.PersonColumns.CONTENT_URI, values);
    	Log.i(TAG, "insert uri="+uri);
    	String lastPath = uri.getLastPathSegment();
    	if (TextUtils.isEmpty(lastPath)) {
    		Log.i(TAG, "insert failure!");
    	} else {
    		Log.i(TAG, "insert success! the id is " + lastPath);
    	}
    	
    	return Integer.parseInt(lastPath);
    }
    
    private void query(int id) {
    	Cursor c = getContentResolver().query(Provider.PersonColumns.CONTENT_URI, new String[] { Provider.PersonColumns.NAME, Provider.PersonColumns.AGE }, Provider.PersonColumns._ID + "=?", new String[] { id + "" }, null);
    	if (c != null && c.moveToFirst()) {
    		Person p = new Person();
    		p.name = c.getString(c.getColumnIndexOrThrow(Provider.PersonColumns.NAME));
    		p.age = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns.AGE));
    		Log.i(TAG, "person.name="+p.name+"---person.age="+p.age);
    	} else {
    		Log.i(TAG, "query failure!");
    	}
    }
}