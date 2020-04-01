package com.xiaohuashifu.tm.service.constant;

/**
 * 描述: 会议相关常量
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:16
 */
public class MeetingConstant {

    /**
     * 会议二维码过期时间，单位秒
     */
    public static final int MEETING_QRCODE_EXPIRE_TIME = 60;

    /**
     * 会议二维码在redis里的key的前缀
     */
    public static final String PREFIX_OF_QRCODE_FOR_REDIS_KEY = "meeting:qrcode:";

}
