package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.ao.MeetingQrcodeAO;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;

public interface MeetingService {
	Result<PageInfo<MeetingDO>> listMeetings(MeetingQuery meetingQuery);

    Result<MeetingDO> saveMeeting(MeetingDO meetingDO);

    Result<MeetingDO> getMeeting(Integer id);

    Result<MeetingDO> updateMeeting(MeetingDO meetingDO);

    Result<MeetingQrcodeAO> createAndSaveQrcode(Integer meetingId, Integer userId);
}
