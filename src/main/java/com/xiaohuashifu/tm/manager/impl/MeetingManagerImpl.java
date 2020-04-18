package com.xiaohuashifu.tm.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.manager.MeetingManager;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.pojo.vo.MeetingVO;
import com.xiaohuashifu.tm.pojo.vo.UserVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;
import com.xiaohuashifu.tm.service.UserService;
import org.dozer.Mapper;
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
@Component("meetingManager")
public class MeetingManagerImpl implements MeetingManager {

    private final MeetingService meetingService;

    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public MeetingManagerImpl(MeetingService meetingService, UserService userService, Mapper mapper) {
        this.meetingService = meetingService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 获取PageInfo<MeetingVO>
     * @param meetingQuery 包含页码，页条数
     * @return Result<PageInfo<MeetingVO>>
     */
    @Override
    public Result<PageInfo<MeetingVO>> listMeetings(MeetingQuery meetingQuery) {
        Result<PageInfo<MeetingDO>> result = meetingService.listMeetings(meetingQuery);
        PageInfo<MeetingDO> pageInfo = result.getData();
        List<MeetingDO> meetingDOList = pageInfo.getList();
        List<MeetingVO> meetingVOList = meetingDOList.stream()
                .map((meetingDO)->{
                    Result<UserDO> userDOResult = userService.getUser(meetingDO.getUserId());
                    UserVO userVO = mapper.map(userDOResult.getData(), UserVO.class);
                    MeetingVO meetingVO = mapper.map(meetingDO, MeetingVO.class);
                    meetingVO.setUser(userVO);
                    return meetingVO;
                }).collect(Collectors.toList());
        PageInfo<MeetingVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(meetingVOList);
        return Result.success(pageInfo0);

    }
}
