package com.xiaohuashifu.tm.dao;

import java.util.List;

import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import org.apache.ibatis.annotations.Mapper;

import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MeetingMapper {
	List<MeetingDO> listMeetings(MeetingQuery meetingQuery);

    int saveMeeting(MeetingDO meetingDO);

    MeetingDO getMeeting(Integer id);

    int countByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    int updateMeeting(MeetingDO meetingDO0);
}
