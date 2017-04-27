package com.jacp.demo.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.jacp.database.DatabaseHelper;

/**
 * 对programmer表进行操作的ContentProvider
 * @author jacp
 *
 */
public class ProgrammerProvider extends ContentProvider {

    private static HashMap<String, String> sprogrammersProjectionMap;

    private static final int PROGRAMMERS = 1;
    private static final int PROGRAMMERS_ID = 2;

    private static final UriMatcher sUriMatcher;

    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Provider.ProgrammerColumns.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        case PROGRAMMERS:
            qb.setProjectionMap(sprogrammersProjectionMap);
            break;

        case PROGRAMMERS_ID:
            qb.setProjectionMap(sprogrammersProjectionMap);
            qb.appendWhere(Provider.ProgrammerColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.ProgrammerColumns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case PROGRAMMERS:
            return Provider.CONTENT_TYPE;
        case PROGRAMMERS_ID:
            return Provider.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != PROGRAMMERS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        // Make sure that the fields are all set
        if (values.containsKey(Provider.ProgrammerColumns.NAME) == false) {
            values.put(Provider.ProgrammerColumns.NAME, "");
        }

        if (values.containsKey(Provider.ProgrammerColumns.AGE) == false) {
            values.put(Provider.ProgrammerColumns.AGE, 0);
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Provider.ProgrammerColumns.TABLE_NAME, Provider.ProgrammerColumns.NAME, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(Provider.ProgrammerColumns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case PROGRAMMERS:
            count = db.delete(Provider.ProgrammerColumns.TABLE_NAME, where, whereArgs);
            break;

        case PROGRAMMERS_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(Provider.ProgrammerColumns.TABLE_NAME, Provider.ProgrammerColumns._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case PROGRAMMERS:
            count = db.update(Provider.ProgrammerColumns.TABLE_NAME, values, where, whereArgs);
            break;

        case PROGRAMMERS_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(Provider.ProgrammerColumns.TABLE_NAME, values, Provider.ProgrammerColumns._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Provider.ProgrammerColumns.AUTHORITY, "programmers", PROGRAMMERS);
        sUriMatcher.addURI(Provider.ProgrammerColumns.AUTHORITY, "programmers/#", PROGRAMMERS_ID);

        sprogrammersProjectionMap = new HashMap<String, String>();
        sprogrammersProjectionMap.put(Provider.ProgrammerColumns._ID, Provider.ProgrammerColumns._ID);
        sprogrammersProjectionMap.put(Provider.ProgrammerColumns.NAME, Provider.ProgrammerColumns.NAME);
        sprogrammersProjectionMap.put(Provider.ProgrammerColumns.AGE, Provider.ProgrammerColumns.AGE);
    }
}
