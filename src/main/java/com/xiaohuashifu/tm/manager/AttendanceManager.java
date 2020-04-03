package com.xiaohuashifu.tm.manager;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import com.xiaohuashifu.tm.pojo.vo.AttendanceVO;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-03 22:08
 */
public interface AttendanceManager {
    Result<PageInfo<AttendanceVO>> listAttendances(AttendanceQuery attendanceQuery);
}
