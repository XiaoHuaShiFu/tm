package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

public class AdminLogDO {
	private Integer id;
	private Integer adminId;
	private String content;
	private Date createTime;
	private Date updateTime;
	
	public AdminLogDO() {}
	
	public AdminLogDO(Integer adminId, String content) {
		this.adminId = adminId;
		this.content = content;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAdminId() {
		return adminId;
	}
	
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
	
	
}
