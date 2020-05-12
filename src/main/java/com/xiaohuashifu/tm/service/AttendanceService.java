package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.ao.AttendanceQrcodeAO;
import com.xiaohuashifu.tm.pojo.do0.AttendanceDO;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-03 13:38
 */
public interface AttendanceService {
    Result<AttendanceDO> saveAttendance(AttendanceDO attendanceDO, String qrcode);

    Result<AttendanceQrcodeAO> createAndSaveQrcode();

    Result<AttendanceDO> getAttendance(Integer id);

    Result<PageInfo<AttendanceDO>> listAttendances(AttendanceQuery attendanceQuery);

    Result<AttendanceDO> updateAttendance(AttendanceDO attendanceDO);

    Result<AttendanceDO> updateAttendanceForSignOut(AttendanceDO attendanceDO, String qrcode);
}
