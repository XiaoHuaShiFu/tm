package com.xiaohuashifu.tm.pojo.do0;

import com.xiaohuashifu.tm.pojo.group.Group;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.group.GroupPut;
import com.xiaohuashifu.tm.validator.annotation.Id;
import com.xiaohuashifu.tm.validator.annotation.Latitude;
import com.xiaohuashifu.tm.validator.annotation.Longitude;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-03 13:40
 */
public class AttendanceDO {

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.",
            groups = {GroupPut.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.",
            groups = {GroupPost.class, GroupPut.class})
    @Id(groups = {Group.class})
    private Integer userId;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The latitude must be not null.", groups = {GroupPost.class})
    @Latitude(groups = {GroupPost.class})
    private BigDecimal latitude;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The longitude must be not null.", groups = {GroupPost.class})
    @Longitude(groups = {GroupPost.class})
    private BigDecimal longitude;

    private Date signInTime;

    private Date signOutTime;

    private Boolean available;

    private Date createTime;

    private Date updateTime;

    public AttendanceDO() {
    }

    public AttendanceDO(Integer id, Integer userId, BigDecimal latitude, BigDecimal longitude, Date signInTime,
                        Date signOutTime, Boolean available, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.signInTime = signInTime;
        this.signOutTime = signOutTime;
        this.available = available;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
        return "AttendanceDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", signInTime=" + signInTime +
                ", signOutTime=" + signOutTime +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
