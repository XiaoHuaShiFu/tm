package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xiaohuashifu.tm.dao.AttendanceMapper;
import com.xiaohuashifu.tm.pojo.ao.AttendanceQrcodeAO;
import com.xiaohuashifu.tm.pojo.do0.AttendanceDO;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AttendanceService;
import com.xiaohuashifu.tm.service.CacheService;
import com.xiaohuashifu.tm.service.constant.AttendanceConstant;
import com.xiaohuashifu.tm.service.constant.RedisStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 描述:出勤服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-26
 */
@Service("attendanceService")
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private final AttendanceMapper attendanceMapper;

    private final CacheService cacheService;

    private final Gson gson;

    @Autowired
    public AttendanceServiceImpl(AttendanceMapper attendanceMapper, CacheService cacheService, Gson gson) {
        this.attendanceMapper = attendanceMapper;
        this.cacheService = cacheService;
        this.gson = gson;
    }

    /**
     * 保存Attendance
     * @param qrcode 动态二维码
     * @param attendanceDO AttendanceDO
     * @return Result<AttendanceDO>
     */
    @Override
    public Result<AttendanceDO> saveAttendance(AttendanceDO attendanceDO, String qrcode) {
        // 经纬度不在签到点的范围内
        if (!(attendanceDO.getLatitude()
                .compareTo(AttendanceConstant.LATITUDE.subtract(AttendanceConstant.ERR_OF_LATITUDE_AND_LONGITUDE)) >= 0
                && attendanceDO.getLatitude()
                .compareTo(AttendanceConstant.LATITUDE.add(AttendanceConstant.ERR_OF_LATITUDE_AND_LONGITUDE)) <= 0
                && attendanceDO.getLongitude()
                .compareTo(AttendanceConstant.LONGITUDE.subtract(AttendanceConstant.ERR_OF_LATITUDE_AND_LONGITUDE)) >= 0
                && attendanceDO.getLongitude()
                .compareTo(AttendanceConstant.LONGITUDE.add(AttendanceConstant.ERR_OF_LATITUDE_AND_LONGITUDE)) <= 0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "Latitude and longitude are not within the range of the check-in point.");
        }

        String key = AttendanceConstant.PREFIX_OF_QRCODE_FOR_REDIS_KEY;
        String value = cacheService.get(key);

        // 出勤二维码不存在
        if (value == null) {
            logger.error("The qrcode not exists.");
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The qrcode not exists.");
        }
        AttendanceQrcodeAO attendanceQrcodeAO = gson.fromJson(value, AttendanceQrcodeAO.class);

        // qrcode不正确或者已经过时
        if (!attendanceQrcodeAO.getQrcode().equals(qrcode)) {
            logger.error("The qrcode is invalid.");
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The qrcode is invalid.");
        }

        AttendanceDO attendanceDO0 = new AttendanceDO();
        attendanceDO0.setUserId(attendanceDO.getUserId());
        attendanceDO0.setLatitude(attendanceDO.getLatitude());
        attendanceDO0.setLongitude(attendanceDO.getLongitude());
        attendanceDO0.setSignInTime(new Date());
        int count = attendanceMapper.insertAttendance(attendanceDO0);
        // 插入出勤（签到）信息失败
        if (count < 1) {
            logger.error("Insert attendance failed. userId: {}", attendanceDO.getUserId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert attendance failed.");
        }

        return getAttendance(attendanceDO0.getId());
    }

    /**
     * 创建并保存Qrcode
     * @return Result<AttendanceQrcodeAO> 二维码
     */
    @Override
    public Result<AttendanceQrcodeAO> createAndSaveQrcode() {
        // 新建出勤二维码
        AttendanceQrcodeAO attendanceQrcodeAO = new AttendanceQrcodeAO(createQrcode());

        // 保存出勤二维码
        return saveQrcode(attendanceQrcodeAO, AttendanceConstant.ATTENDANCE_QRCODE_EXPIRE_TIME);
    }

    /**
     * 获取出勤记录
     * @param id 出勤记录id
     * @return AttendanceDO
     */
    @Override
    public Result<AttendanceDO> getAttendance(Integer id) {
        AttendanceDO attendanceDO = attendanceMapper.selectAttendance(id);
        if (attendanceDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(attendanceDO);
    }

    /**
     * 获取出勤记录
     * @param attendanceQuery 包含页码，页条数和用户id
     * @return Result<PageInfo<AttendanceDO>>
     */
    @Override
    public Result<PageInfo<AttendanceDO>> listAttendances(AttendanceQuery attendanceQuery) {
        PageHelper.startPage(attendanceQuery);
        PageInfo<AttendanceDO> pageInfo =
                new PageInfo<>(attendanceMapper.listAttendances(attendanceQuery));
        return Result.success(pageInfo);
    }

    /**
     * 更新出勤记录
     *
     * @param attendanceDO 要更新的信息
     * @return AttendanceDO
     */
    @Override
    public Result<AttendanceDO> updateAttendance(AttendanceDO attendanceDO) {
        //只给更新某些属性
        AttendanceDO attendanceDO0 = new AttendanceDO();
        attendanceDO0.setId(attendanceDO.getId());
        attendanceDO0.setSignOutTime(attendanceDO.getSignOutTime());
        attendanceDO0.setAvailable(attendanceDO.getAvailable());

        int count = attendanceMapper.updateAttendance(attendanceDO0);
        if (count < 1) {
            logger.error("Update attendance failed. id: {}", attendanceDO0.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update attendance failed.");
        }

        return getAttendance(attendanceDO0.getId());
    }

    /**
     * 在缓存里添加qrcode，并设置过期时间
     *
     * @param attendanceQrcodeAO AttendanceQrcodeAO
     * @param expireTime 过期时间
     * @return Result<AttendanceQrcodeAO>
     */
    private Result<AttendanceQrcodeAO> saveQrcode(AttendanceQrcodeAO attendanceQrcodeAO, int expireTime) {
        String key = AttendanceConstant.PREFIX_OF_QRCODE_FOR_REDIS_KEY;
        //保存token
        String code = cacheService.set(key, gson.toJson(attendanceQrcodeAO));

        //保存失败
        if (!code.equals(RedisStatus.OK.name())) {
            logger.error("Failed to save qrcode.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to save qrcode.");
        }

        //设置过期时间
        Long result = cacheService.expire(key, expireTime);
        if (result.equals(0L)) {
            cacheService.del(key);
            logger.error("Failed to set expire.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to set expire.");
        }

        return Result.success(attendanceQrcodeAO);
    }

    /**
     * 创建出勤二维码
     * @return 出勤二维码
     */
    private String createQrcode() {
        return UUID.randomUUID().toString();
    }


}
