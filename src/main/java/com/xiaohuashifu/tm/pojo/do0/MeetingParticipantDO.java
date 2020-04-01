package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:59
 */
public class MeetingParticipantDO {

    private Integer id;

    private Integer meetingId;

    private Integer userId;

    private Date participationTime;

    private Date createTime;

    private Date updateTime;

    public MeetingParticipantDO() {
    }

    public MeetingParticipantDO(Integer id, Integer meetingId, Integer userId, Date participationTime, Date createTime,
                                Date updateTime) {
        this.id = id;
        this.meetingId = meetingId;
        this.userId = userId;
        this.participationTime = participationTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MeetingParticipantDO{" +
                "id=" + id +
                ", meetingId=" + meetingId +
                ", userId=" + userId +
                ", participationTime=" + participationTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
