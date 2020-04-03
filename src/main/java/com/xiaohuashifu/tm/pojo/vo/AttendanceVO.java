package com.xiaohuashifu.tm.pojo.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-03 13:40
 */
public class AttendanceVO {

    private Integer id;

    private Integer userId;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Date signInTime;

    private Date signOutTime;

    private Boolean available;

    private UserVO user;

    public AttendanceVO() {
    }

    public AttendanceVO(Integer id, Integer userId, BigDecimal latitude, BigDecimal longitude, Date signInTime,
                        Date signOutTime, Boolean available, UserVO user) {
        this.id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.signInTime = signInTime;
        this.signOutTime = signOutTime;
        this.available = available;
        this.user = user;
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

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AttendanceVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", signInTime=" + signInTime +
                ", signOutTime=" + signOutTime +
                ", available=" + available +
                ", user=" + user +
                '}';
    }
}
