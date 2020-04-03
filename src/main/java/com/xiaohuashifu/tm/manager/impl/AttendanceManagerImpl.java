package com.xiaohuashifu.tm.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.manager.AttendanceManager;
import com.xiaohuashifu.tm.pojo.do0.AttendanceDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import com.xiaohuashifu.tm.pojo.vo.AttendanceVO;
import com.xiaohuashifu.tm.pojo.vo.UserVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AttendanceService;
import com.xiaohuashifu.tm.service.UserService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-02 2:44
 */
@Component("attendanceManager")
public class AttendanceManagerImpl implements AttendanceManager {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceManagerImpl.class);

    private final AttendanceService attendanceService;

    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public AttendanceManagerImpl(AttendanceService attendanceService, UserService userService, Mapper mapper) {
        this.attendanceService = attendanceService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 获取PageInfo<AttendanceVO>
     * @param attendanceQuery 包含页码，页条数和用户id
     * @return Result<PageInfo<AttendanceVO>>
     */
    @Override
    public Result<PageInfo<AttendanceVO>> listAttendances(AttendanceQuery attendanceQuery) {
        Result<PageInfo<AttendanceDO>> result = attendanceService.listAttendances(attendanceQuery);
        PageInfo<AttendanceDO> pageInfo = result.getData();
        List<AttendanceDO> attendanceDOList = pageInfo.getList();
        List<AttendanceVO> attendanceVOList = attendanceDOList.stream()
                .map((attendanceDO)->{
                    Result<UserDO> userDOResult = userService.getUser(attendanceDO.getUserId());
                    UserVO userVO = mapper.map(userDOResult.getData(), UserVO.class);
                    AttendanceVO attendanceVO = mapper.map(attendanceDO, AttendanceVO.class);
                    attendanceVO.setUser(userVO);
                    return attendanceVO;
                }).collect(Collectors.toList());
        PageInfo<AttendanceVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(attendanceVOList);
        return Result.success(pageInfo0);

    }
}
