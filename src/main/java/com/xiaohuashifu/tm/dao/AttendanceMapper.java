package com.xiaohuashifu.tm.dao;

import com.xiaohuashifu.tm.pojo.do0.AttendanceDO;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    int insertAttendance(AttendanceDO attendanceDO);

    List<AttendanceDO> listAttendances(AttendanceQuery attendanceQuery);

    int updateAttendance(AttendanceDO attendanceDO);

    AttendanceDO selectAttendance(Integer id);
}