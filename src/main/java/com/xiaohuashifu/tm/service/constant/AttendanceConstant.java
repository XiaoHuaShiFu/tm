package com.xiaohuashifu.tm.service.constant;

import java.math.BigDecimal;

/**
 * 描述: 出勤相关常量
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:16
 */
public class AttendanceConstant {

    /**
     * 出勤（签到）二维码过期时间，单位秒
     */
    public static final int ATTENDANCE_QRCODE_EXPIRE_TIME = 60;

    /**
     * 出勤（签到）二维码在redis里的key的前缀
     */
    public static final String PREFIX_OF_QRCODE_FOR_REDIS_KEY = "attendance:qrcode:";

    /**
     * QRCODE-TOKEN的用户名
     */
    // TODO: 2020/4/3 这里不应该出现账号密码，但是由于没有权限认证模块，只能这样做
    public static final String USERNAME_FOR_QRCODE_TOKEN = "qrcodeCreator";

    /**
     * QRCODE-TOKEN的密码
     */
    // TODO: 2020/4/3 这里不应该出现账号密码，但是由于没有权限认证模块，只能这样做
    public static final String PASSWORD_FOR_QRCODE_TOKEN = "qrcodePassword";

    /**
     * 签到点的纬度
     */
    // TODO: 2020/4/3 这里不应该这样写，签到点的经纬度应该是放在数据库里，专门有一张表存储签到点的信息
    public static final BigDecimal LATITUDE = new BigDecimal("23.18139");

    /**
     * 签到点的经度
     */
    // TODO: 2020/4/3 这里不应该这样写，签到点的经纬度应该是放在数据库里，专门有一张表存储签到点的信息
    public static final BigDecimal LONGITUDE = new BigDecimal("113.48067");

    /**
     * 签到点的经纬度误差
     */
    public static final BigDecimal ERR_OF_LATITUDE_AND_LONGITUDE = new BigDecimal("10");
}
