package com.xiaohuashifu.tm.timer.task;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.MeetingState;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 描述: 根据会议的进行时间，结束时间和状态自动改变会议的状态
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component
public class MeetingStateTask implements Runnable {

    private final MeetingService meetingService;

    @Autowired
    public MeetingStateTask(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Override
    public void run() {
        // 查看WAITING状态的会议是否需要改变成PROCESSING
        MeetingQuery meetingQuery = new MeetingQuery();
        meetingQuery.setPageSize(99999);
        meetingQuery.setState(MeetingState.WAITING);
        Result<PageInfo<MeetingDO>> listMeetingsResult = meetingService.listMeetings(meetingQuery);
        List<MeetingDO> meetingDOList = listMeetingsResult.getData().getList();
        for (MeetingDO meetingDO : meetingDOList) {
            if (meetingDO.getStartTime().before(new Date())) {
                meetingDO.setState(MeetingState.PROCESSING);
                meetingService.updateMeeting(meetingDO);
            }
        }

        // 查看PROCESSING状态的会议是否需要改变成FINISH
        meetingQuery.setState(MeetingState.PROCESSING);
        listMeetingsResult = meetingService.listMeetings(meetingQuery);
        meetingDOList = listMeetingsResult.getData().getList();
        for (MeetingDO meetingDO : meetingDOList) {
            if (meetingDO.getEndTime().before(new Date())) {
                meetingDO.setState(MeetingState.FINISH);
                meetingService.updateMeeting(meetingDO);
            }
        }
    }

}
