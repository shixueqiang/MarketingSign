package com.marketing.sign.db;

import android.content.ContentValues;
import android.content.Context;

import com.marketing.sign.model.PathRecordModel;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.util.List;

/**
 * Created by shixq on 2017/6/17.
 */

public class PathDao extends DBHelper {
    private static volatile PathDao mPathDao;

    private PathDao(Context context) {
        super(context);
    }

    public static PathDao getInstance(Context context) {
        if (mPathDao == null) {
            synchronized (PathDao.class) {
                if (mPathDao == null) {
                    mPathDao = new PathDao(context);
                }
            }
        }
        return mPathDao;
    }

    public void insertPathRecord(Context context,PathRecordModel path) {
        ContentValues values = new ContentValues();
        context.getContentResolver().insert(PathProvider.Path.CONTENT_URI,values);
    }

    public List<PathRecordModel> queryAllPath() {
        return null;
    }
}
