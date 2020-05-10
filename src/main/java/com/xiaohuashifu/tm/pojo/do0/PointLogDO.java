package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

public class PointLogDO {
	private Integer id;
	private Integer userId;
	private Integer point;
	private String content;
	private Date createTime;
	private Date updateTime;
	
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
	
	public Integer getPoint() {
		return point;
	}
	
	public void setPoint(Integer point) {
		this.point = point;
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

	@Override
	public String toString() {
		return "PointLogDO [id=" + id + 
				", userId=" + userId +
				", point=" + point +
				", content=" + content
				+ ", createTime=" + createTime +
				", updateTime=" + updateTime + "]";
	}
	
}
