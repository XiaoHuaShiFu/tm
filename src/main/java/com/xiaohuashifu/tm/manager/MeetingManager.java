package com.xiaohuashifu.tm.manager;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.query.MeetingParticipantQuery;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.pojo.vo.MeetingParticipantVO;
import com.xiaohuashifu.tm.pojo.vo.MeetingVO;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-02 2:43
 */
public interface MeetingManager {

    Result<PageInfo<MeetingVO>> listMeetings(MeetingQuery meetingQuery);

}
