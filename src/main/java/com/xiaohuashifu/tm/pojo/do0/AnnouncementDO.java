package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

public class AnnouncementDO {
	private Integer id;
	private Integer adminId;
	private String title;
	private String content;
	private Date publishTime;
	private Date createTime;
	private Date updateTime;
	
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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
		return "AnnouncementDO [id=" + id +
				", adminId=" + adminId +
				", title=" + title +
				", content=" + content +
				", publishTime=" + publishTime +
				", createTime=" + createTime +
				", updateTime=" + updateTime + "]";
	}
	
}
