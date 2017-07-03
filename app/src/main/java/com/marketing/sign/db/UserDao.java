package com.marketing.sign.db;

import android.content.ContentValues;
import android.content.Context;

import com.elvishew.xlog.XLog;
import com.marketing.sign.model.UserModel;
import com.marketing.sign.utils.TimeUtil;
import com.tencent.wcdb.Cursor;

/**
 * Created by shixq on 2017/7/2.
 */

public class UserDao extends UserDBHelper {
    private static volatile UserDao mUserDao;

    private UserDao(Context context) {
        super(context);
    }

    public static UserDao getInstance(Context context) {
        if (mUserDao == null) {
            synchronized (UserDao.class) {
                if (mUserDao == null) {
                    mUserDao = new UserDao(context);
                }
            }
        }
        return mUserDao;
    }

    public void insert(UserModel user) {
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().query(UserModel.TABLE_NAME, null, UserModel.ID + " = ?", new String[]{String.valueOf(user.getId())}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                ContentValues values = new ContentValues();
                values.put(UserModel.LOGIN_TIME, TimeUtil.getNowTime());
                values.put(UserModel.STATUS, 0);
                values.put(UserModel.START_SIGN_TIME, "");
                values.put(UserModel.END_SIGN_TIME, "");
                getWritableDatabase().update(UserModel.TABLE_NAME, values, UserModel.ID + " = ?", new String[]{String.valueOf(user.getId())});
            } else {
                ContentValues args = new ContentValues();
                args.put(UserModel.ID, user.getId());
                args.put(UserModel.NAME, user.getName());
                args.put(UserModel.PHONE, user.getPhone());
                args.put(UserModel.ACCOUNT,user.getAccount());
                getWritableDatabase().insert(UserModel.TABLE_NAME, null, args);
            }
        }catch (Exception e) {
            XLog.e(e.getMessage());
        }finally {
            if(cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    /**
     * 从本地对象读取当前登录用户
     *
     * @return
     */
    public UserModel queryCurrentUser() {
        return null;
    }
}
