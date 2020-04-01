package com.xiaohuashifu.tm.pojo.query;

import com.xiaohuashifu.tm.pojo.group.Group;
import com.xiaohuashifu.tm.pojo.group.GroupGet;
import com.xiaohuashifu.tm.validator.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-02 2:24
 */
public class MeetingParticipantQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The meetingId must be not null.", groups = {GroupGet.class})
    @Id(groups = {Group.class})
    private Integer meetingId;

    public MeetingParticipantQuery() {
    }

    public MeetingParticipantQuery(Integer pageNum, Integer pageSize, Integer meetingId) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.meetingId = meetingId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public String toString() {
        return "MeetingParticipantQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", meetingId=" + meetingId +
                '}';
    }
}
