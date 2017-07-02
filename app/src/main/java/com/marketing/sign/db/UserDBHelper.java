package com.marketing.sign.db;

import android.content.Context;

import com.marketing.sign.config.DBConstant;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * Created by shixq on 2017/7/2.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(Context context) {
        this(context, DBConstant.DB_NAME_USER, null, DBConstant.DB_VERSION_USER);
    }
    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
