package com.xiaohuashifu.tm.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-25 18:37
 */
public class UserAuthCodeVO {

    private Integer id;

    private String authCode;

    private Boolean used;

    private Date createTime;

    private Date updateTime;

    public UserAuthCodeVO() {
    }

    public UserAuthCodeVO(Integer id, String authCode, Boolean used, Date createTime, Date updateTime) {
        this.id = id;
        this.authCode = authCode;
        this.used = used;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
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
        return "UserAuthCodeVO{" +
                "id=" + id +
                ", authCode='" + authCode + '\'' +
                ", used=" + used +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
