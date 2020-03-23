package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

public class WeChatMpFormIdDO {
    private Integer id;

    private Integer fid;

    private String formId;

    private Date createTime;

    private Date updateTime;

    public WeChatMpFormIdDO(Integer id, Integer fid, String formId) {
        this.id = id;
        this.fid = fid;
        this.formId = formId;
    }

    public WeChatMpFormIdDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId == null ? null : formId.trim();
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
        return "WeChatMpFormIdDO{" +
                "id=" + id +
                ", fid=" + fid +
                ", formId='" + formId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}