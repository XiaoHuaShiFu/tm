package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xiaohuashifu.tm.dao.AttendanceMapper;
import com.xiaohuashifu.tm.pojo.ao.AttendanceQrcodeAO;
import com.xiaohuashifu.tm.pojo.do0.AttendanceDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
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

import java.math.BigDecimal;
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
        if (!(attendanceDO.getLatitude().compareTo(AttendanceConstant.LATITUDE.subtract(BigDecimal.ONE)) >= 0
                && attendanceDO.getLatitude().compareTo(AttendanceConstant.LATITUDE.add(BigDecimal.ONE)) <= 0
                && attendanceDO.getLongitude().compareTo(AttendanceConstant.LONGITUDE.subtract(BigDecimal.ONE)) >= 0
                && attendanceDO.getLongitude().compareTo(AttendanceConstant.LONGITUDE.add(BigDecimal.ONE)) <= 0)) {
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

//    @Override
//    public Result<String> getOpenid(Integer userId) {
//        return null;
//    }
//
//    /**
//     * 获取UserDO通过code
//     *
//     * @param code wx.login()接口的返回值
//     * @return Result<UserDO>
//     */
//    @Override
//    public Result<UserDO> getUserByCode(String code) {
//        String openid = weChatMpManager.getOpenid(code, WeChatMp.TM);
//        //通过openid获取失败
//        if (openid == null) {
//            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified openid does not exist.");
//        }
//        UserDO userDO = userMapper.getUserByOpenid(openid);
//
//        return Result.success(userDO);
//    }
//
//    /**
//     * 获取UserDO通过jobNumber
//     *
//     * @param jobNumber 工号
//     * @return Result<UserDO>
//     */
//    @Override
//    public Result<UserDO> getUserByJobNumber(String jobNumber) {
//        UserDO userDO = userMapper.getUserByJobNumber(jobNumber);
//        if (userDO == null) {
//            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified user for jobNumber="
//                    + jobNumber + " does not exist.");
//        }
//        return Result.success(userDO);
//    }
//
//    /**
//     * 保存User
//     * @param code String
//     * @param userDO UserDO
//     * @return Result<UserDO>
//     */
//    @Override
//    public Result<UserDO> saveUser(UserDO userDO, String code) {
//        String openid = weChatMpManager.getOpenid(code, WeChatMp.TM);
//        //获取openid失败
//        if (openid == null) {
//            return Result.fail(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
//        }
//
//        //openid已经在数据库里
//        int count = userMapper.getCountByOpenid(openid);
//        if (count >= 1) {
//            return Result.fail(ErrorCode.OPERATION_CONFLICT,
//                    "Request was denied due to conflict, the openid already exists.");
//        }
//
//        userDO.setOpenid(openid);
//        count = userMapper.saveUser(userDO);
//        //没有插入成功
//        if (count < 1) {
//            logger.error("Insert user fail.");
//            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert user fail.");
//        }
//
//        return getUser(userDO.getId());
//    }
//
//    /**
//     * 获取UserDO通过id
//     *
//     * @param id 用户编号
//     * @return UserDO
//     */
//    @Override
//    public Result<UserDO> getUser(Integer id) {
//        UserDO userDO = userMapper.getUser(id);
//        if (userDO == null) {
//            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
//        }
//        return Result.success(userDO);
//    }
//
//    /**
//     * 获取UserDOList通过查询参数qquery
//     *
//     * @param query 查询参数
//     * @return UserDOList
//     */
//    @Override
//    public Result<List<UserDO>> listUsers(UserQuery query) {
//        PageHelper.startPage(query.getPageNum(), query.getPageSize());
//        List<UserDO> userDOList = userMapper.listUsers(query);
//        if (userDOList.size() < 1) {
//            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
//        }
//
//        return Result.success(userDOList);
//    }
//
//    /**
//     * 更新用户信息
//     *
//     * @param userDO 要更新的信息
//     * @return 更新后的用户信息
//     */
//    @Override
//    public Result<UserDO> updateUser(UserDO userDO) {
//        //只给更新某些属性
//        UserDO userDO0 = new UserDO();
//        userDO0.setJobNumber(userDO.getJobNumber());
//        userDO0.setPassword(userDO.getPassword());
//        userDO0.setNickName(userDO.getNickName());
//        userDO0.setFullName(userDO.getFullName());
//        userDO0.setGender(userDO.getGender());
//        userDO0.setBirthday(userDO.getBirthday());
//        userDO0.setPhone(userDO.getPhone());
//        userDO0.setEmail(userDO.getEmail());
//        userDO0.setDepartment(userDO.getDepartment());
//        userDO0.setPoint(userDO.getPoint());
//        userDO0.setAvailable(userDO.getAvailable());
//        //所有参数都为空
//        if (BeanUtils.allFieldIsNull(userDO0)) {
//            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
//                    "The required parameter must be not all null.");
//        }
//
//        userDO0.setId(userDO.getId());
//        int count = userMapper.updateUser(userDO0);
//        if (count < 1) {
//            logger.error("Update avatar failed. userId: {}", userDO0.getId());
//            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update user failed.");
//        }
//
//        return getUser(userDO0.getId());
//    }
//
//    /**
//     * 更新头像
//     *
//     * @param id userId
//     * @param avatar MultipartFile
//     * @return 新文件url
//     */
//    @Override
//    public Result<UserDO> updateAvatar(Integer id, MultipartFile avatar) {
//        // 获取用户信息，主要是为了获取旧文件Url
//        UserDO userDO = userMapper.getUser(id);
//
//        // 更新头像文件
//        String newAvatarUrl = fileService.updateFile(avatar, userDO.getAvatarUrl(),
//                UserConstant.PREFIX_AVATAR_FILE_DIRECTORY);
//
//        // 更新数据库里的avatar_url
//        UserDO userDO0 = new UserDO();
//        userDO0.setId(id);
//        userDO0.setAvatarUrl(newAvatarUrl);
//        int count = userMapper.updateUser(userDO0);
//        if (count < 1) {
//            logger.error("Update avatar failed. id: {}", id);
//            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update avatar failed.");
//        }
//
//        userDO.setAvatarUrl(newAvatarUrl);
//        return Result.success(userDO);
//    }

}
