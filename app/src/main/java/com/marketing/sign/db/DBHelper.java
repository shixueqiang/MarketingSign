package com.marketing.sign.db;

import android.content.Context;

import com.marketing.sign.config.DBConstant;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * Created by shixq on 2017/6/15.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String RECORD_CREATE = "create table if not exists record("
            + "_id"
            + " integer primary key autoincrement,"
            + "stratpoint STRING,"
            + "endpoint STRING,"
            + "pathline STRING,"
            + "distance STRING,"
            + "duration STRING,"
            + "averagespeed STRING,"
            + "date STRING" + ");";

    public DBHelper(Context context) {
        this(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RECORD_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
