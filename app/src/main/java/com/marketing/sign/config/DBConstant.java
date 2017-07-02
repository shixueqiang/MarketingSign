package com.marketing.sign.config;

import com.marketing.sign.model.PathRecordModel;
import com.marketing.sign.model.UserModel;

public interface DBConstant {
    public static String DB_NAME = "marketingsign-%s.db";
    /** 用来存储登录用户信息*/
    public static String DB_NAME_USER = "user.db";
    public static int DB_VERSION = 1;
    public static int DB_VERSION_USER = 1;
    public static final Class<?>[] DB_CLASSES = new Class[]{
            PathRecordModel.class,
            UserModel.class
    };
}
