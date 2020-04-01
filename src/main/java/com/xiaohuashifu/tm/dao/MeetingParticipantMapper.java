package com.xiaohuashifu.tm.dao;

import com.xiaohuashifu.tm.pojo.do0.MeetingParticipantDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetingParticipantMapper {

    int insertMeetingParticipant(MeetingParticipantDO meetingParticipantDO);

    int countByMeetingIdAndUserId(@Param("meetingId") Integer meetingId, @Param("userId") Integer userId);

    List<MeetingParticipantDO> listMeetingParticipants(Integer meetingId);
}
