package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.MeetingParticipantDO;
import com.xiaohuashifu.tm.pojo.query.MeetingParticipantQuery;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:56
 */
public interface MeetingParticipantService {
    Result<MeetingParticipantDO> saveMeetingParticipant(Integer meetingId, Integer userId, String qrcode);

    Result<PageInfo<MeetingParticipantDO>> listMeetingParticipants(MeetingParticipantQuery meetingParticipantQuery);
}
