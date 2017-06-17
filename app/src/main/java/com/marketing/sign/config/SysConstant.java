package com.marketing.sign.config;

/**
 * 系统的常量类
 */
public interface SysConstant {

    /**头像尺寸大小定义*/
    public static final String AVATAR_APPEND_32 ="_32x32.jpg";
    public static final String AVATAR_APPEND_100 ="_100x100.jpg";
    public static final String AVATAR_APPEND_120 ="_100x100.jpg";//头像120*120的pic 没有 所以统一100
    public static final String AVATAR_APPEND_200="_200x200.jpg";

    /**协议头相关 start*/
	public static final int PROTOCOL_HEADER_LENGTH = 16;// 默认消息头的长度
	public static final int PROTOCOL_VERSION = 6;
	public static final int PROTOCOL_FLAG = 0;
	public static final char PROTOCOL_ERROR = '0';
	public static final char PROTOCOL_RESERVED = '0';


    // 读取磁盘上文件， 分支判断其类型
	public static final int FILE_SAVE_TYPE_IMAGE = 0X00013;
	public static final int FILE_SAVE_TYPE_AUDIO = 0X00014;

	public static final float MAX_SOUND_RECORD_TIME = 60.0f;// 单位秒
	public static final int MAX_SELECT_IMAGE_COUNT = 6;

}
