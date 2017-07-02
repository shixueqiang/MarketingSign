package com.marketing.sign.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.marketing.sign.model.PathRecordModel;
import com.tencent.wcdb.database.SQLiteDatabase;

/**
 * Created by shixq on 2017/7/2.
 */

public class PathProvider extends ContentProvider {
    public static final String AUTHORITY = "com.marketing.sign";
    //provider path
    private static final String PATH = "path";
    //provider type
    private static final int TYPE_DETAULT = 1;
    private static final int TYPE_PATH = TYPE_DETAULT + 1;
    private static final int TYPE_PATH_ID = TYPE_DETAULT + 2;
    //mime type 返回多行记录
    private static final String MIME_PREFIX_DIR = "vnd.android.cursor.dir";
    //mime type 返回单行记录
    private static final String MIME_PREFIX_ITEM = "vnd.android.cursor.item";

    private static final UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sMatcher.addURI(AUTHORITY, PATH, TYPE_PATH);
        sMatcher.addURI(AUTHORITY, PATH + "/#", TYPE_PATH_ID);
    }

    public static class Path {
        public static String TABLE_NAME = "path";
        public static final Uri CONTENT_URI = Uri.parse(String.format("content://%s/%s", AUTHORITY, PATH));
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = PathDao.getInstance(getContext()).getReadableDatabase();
        String id = null;
        String tableName = null;
        switch (sMatcher.match(uri)) {
            case TYPE_PATH:
                tableName = Path.TABLE_NAME;
                break;
            case TYPE_PATH_ID:
                id = "_id=" + ContentUris.parseId(uri);
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri:" + uri);
        }
        Cursor c = query(db, tableName, id, projection, selection, selectionArgs, sortOrder);
        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String retVal = null;
        switch (sMatcher.match(uri)) {
            case TYPE_PATH:
                retVal = String.format("%s/%s", MIME_PREFIX_DIR, PATH);
                break;
            case TYPE_PATH_ID:
                retVal = String.format("%s/%s", MIME_PREFIX_ITEM, PATH);
                break;
            default:
                retVal = "";
                throw new IllegalArgumentException("Unknow Uri:" + uri);
        }
        return retVal;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = PathDao.getInstance(getContext()).getWritableDatabase();
        long rowid = -1;
        switch (sMatcher.match(uri)) {
            case TYPE_PATH_ID:
            case TYPE_PATH:
                rowid = insert(db, Path.TABLE_NAME, values);
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri:" + uri);
        }
        if (rowid > 0) {
            Uri retVal = ContentUris.withAppendedId(uri, rowid);
            ContentResolver cr = this.getContext().getContentResolver();
            cr.notifyChange(uri, null);
            cr.notifyChange(Path.CONTENT_URI, null);
            return retVal;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = PathDao.getInstance(getContext()).getWritableDatabase();
        int count = 0;
        String id = null;
        switch (sMatcher.match(uri)) {
            case TYPE_PATH_ID:
                id = "_id=" + ContentUris.parseId(uri);
            case TYPE_PATH:
                count = delete(db, Path.TABLE_NAME, id, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri:" + uri);
        }
        if (count > 0) {
            ContentResolver cr = this.getContext().getContentResolver();
            cr.notifyChange(uri, null);
            cr.notifyChange(Path.CONTENT_URI, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = PathDao.getInstance(getContext()).getWritableDatabase();
        switch (sMatcher.match(uri)) {
            case TYPE_PATH_ID:
            case TYPE_PATH:
                count = update(db, Path.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri:" + uri);
        }
        if (count > 0) {
            ContentResolver cr = this.getContext().getContentResolver();
            cr.notifyChange(uri, null);
            cr.notifyChange(Path.CONTENT_URI, null);
        }
        return count;
    }

    private Cursor query(SQLiteDatabase db, String tableName, String partId, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (!TextUtils.isEmpty(partId)) {
            selection = String.format("%s AND (%s)", partId, selection);
        }
        try {
            if (!db.isOpen())
                return null;
            return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        } catch (Exception e) {
            XLog.e(e.getMessage());
            return null;
        }
    }

    private long insert(SQLiteDatabase db, String tableName, ContentValues values) {
        long ret = -1;
        try {
            if (!db.isOpen())
                return ret;
            ret = db.replace(tableName, null, values);
        } catch (Exception e) {
            XLog.e(e.getMessage());
        }
        return ret;
    }

    private int delete(SQLiteDatabase db, String tableName, String partId, String whereClause, String[] whereArgs) {
        int ret = -1;
        if (!db.isOpen())
            return ret;
        try {
            ret = db.delete(tableName, whereClause, whereArgs);
        } catch (Exception e) {
           XLog.e(e.getMessage());
        }
        return ret;
    }
    private int update(SQLiteDatabase db, String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        int ret = -1;
        if (!db.isOpen())
            return ret;
        try {
            ret = db.update(tableName, values, whereClause, whereArgs);
        } catch (Exception e) {
            XLog.e(e.getMessage());
        }
        return ret;
    }

}
