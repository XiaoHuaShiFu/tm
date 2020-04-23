package com.xiaohuashifu.tm.pojo.vo;

import java.util.Date;

import com.xiaohuashifu.tm.pojo.do0.AdminDO;

public class AdminLogVO {
	
	private Integer id;
	private String content;
	private Date operateTime;
	private AdminDO admin;
	
	public AdminLogVO() {}

	public AdminLogVO(Integer id, String content, Date operateTime, AdminDO admin) {
		this.id = id;
		this.content = content;
		this.operateTime = operateTime;
		this.admin = admin;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public AdminDO getAdmin() {
		return admin;
	}

	public void setAdmin(AdminDO admin) {
		this.admin = admin;
	}
	
	@Override
    public String toString() {
        return "adminLogVO{" +
                "id=" + id +
                ", content=" + content +
                ", operateTime=" + operateTime +
                ", admin= [ " + admin.toString() + " ]" +
                '}';
    }
	
}
