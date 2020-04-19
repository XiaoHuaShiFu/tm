package com.xiaohuashifu.tm.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:59
 */
public class MeetingParticipantVO {

    private Integer id;

    private Integer meetingId;

    private Integer userId;

    private Date participationTime;

    private MeetingVO meeting;
    
    private UserVO user;

    public MeetingParticipantVO() {
    }

    public MeetingParticipantVO(Integer id, Integer meetingId, Integer userId, Date participationTime, MeetingVO meeting, UserVO user) {
        this.id = id;
        this.meetingId = meetingId;
        this.userId = userId;
        this.participationTime = participationTime;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getParticipationTime() {
        return participationTime;
    }

    public void setParticipationTime(Date participationTime) {
        this.participationTime = participationTime;
    }

    public MeetingVO getMeeting() {
    	return meeting;
    }
    
    public void setMeeting(MeetingVO meeting) {
    	this.meeting = meeting;
    }
    
    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MeetingParticipantVO{" +
                "id=" + id +
                ", meetingId=" + meetingId +
                ", userId=" + userId +
                ", participationTime=" + participationTime +
                ", meeting=" + meeting +
                ", user=" + user +
                '}';
    }
}
