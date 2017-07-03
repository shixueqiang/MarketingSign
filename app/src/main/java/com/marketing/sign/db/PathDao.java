package com.marketing.sign.db;

import android.content.ContentValues;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.marketing.sign.model.PathRecordModel;

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
        float distance = path.getDistance(path.getPathline());
        String pathlineSring = path.getPathLineString(path.getPathline());
        List<AMapLocation> list = path.getPathline();
        AMapLocation firstLocaiton = list.get(0);
        AMapLocation lastLocaiton = list.get(list.size() - 1);
        String stratpoint = path.amapLocationToString(firstLocaiton);
        String endpoint = path.amapLocationToString(lastLocaiton);
        values.put(PathRecordModel.DISTANCE,distance);
        values.put(PathRecordModel.PATH_LINE,pathlineSring);
        values.put(PathRecordModel.START_POINT,stratpoint);
        values.put(PathRecordModel.END_POINT,endpoint);
        context.getContentResolver().insert(PathProvider.Path.CONTENT_URI,values);
    }

    public List<PathRecordModel> queryAllPath() {
        return null;
    }
}
