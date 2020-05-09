package com.xiaohuashifu.tm.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.manager.MeetingParticipantManager;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.do0.MeetingParticipantDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.MeetingParticipantQuery;
import com.xiaohuashifu.tm.pojo.vo.MeetingParticipantVO;
import com.xiaohuashifu.tm.pojo.vo.MeetingVO;
import com.xiaohuashifu.tm.pojo.vo.UserVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingParticipantService;
import com.xiaohuashifu.tm.service.MeetingService;
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
@Component("meetingParticipantManager")
public class MeetingParticipantManagerImpl implements MeetingParticipantManager {
    private static final Logger logger = LoggerFactory.getLogger(MeetingParticipantManagerImpl.class);

    private final MeetingParticipantService meetingParticipantService;

    private final MeetingService meetingService;
    
    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public MeetingParticipantManagerImpl(MeetingParticipantService meetingParticipantService,
    		MeetingService meetingService, UserService userService, Mapper mapper) {
        this.meetingParticipantService = meetingParticipantService;
        this.meetingService = meetingService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 获取PageInfo<MeetingParticipantVO>
     * @param meetingParticipantQuery 包含页码，页条数和会议id
     * @return Result<PageInfo<MeetingParticipantVO>>
     */
    @Override
    public Result<PageInfo<MeetingParticipantVO>> listMeetingParticipants(MeetingParticipantQuery meetingParticipantQuery) {
        Result<PageInfo<MeetingParticipantDO>> result =
                meetingParticipantService.listMeetingParticipants(meetingParticipantQuery);
        if (!result.isSuccess()) {
            return Result.fail(result);
        }
        
        PageInfo<MeetingParticipantDO> pageInfo = result.getData();
        List<MeetingParticipantDO> meetingParticipantDOList = pageInfo.getList();
        List<MeetingParticipantVO> meetingParticipantVOList = meetingParticipantDOList.stream()
                .map((meetingParticipantDO)->{
                    Result<UserDO> userDOResult = userService.getUser(meetingParticipantDO.getUserId());
                    UserVO userVO = mapper.map(userDOResult.getData(), UserVO.class);
                    Result<MeetingDO> meetingDOResult = meetingService.getMeeting(meetingParticipantDO.getMeetingId());
                    MeetingVO meetingVO = mapper.map(meetingDOResult.getData(), MeetingVO.class);
                    MeetingParticipantVO meetingParticipantVO = mapper.map(meetingParticipantDO, MeetingParticipantVO.class);
                    meetingParticipantVO.setMeeting(meetingVO);
                    meetingParticipantVO.setUser(userVO);
                    return meetingParticipantVO;
                }).collect(Collectors.toList());
        PageInfo<MeetingParticipantVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(meetingParticipantVOList);
        return Result.success(pageInfo0);

    }
}
