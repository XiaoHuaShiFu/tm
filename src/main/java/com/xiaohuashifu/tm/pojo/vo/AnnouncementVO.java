package com.xiaohuashifu.tm.pojo.vo;

import java.util.Date;

import com.xiaohuashifu.tm.pojo.do0.AdminDO;

public class AnnouncementVO {
	private Integer id;
	private AdminDO admin;
	private String title;
	private String content;
	private Date publishTime;
	
	/**
	 * 发布该公告的管理员是否是当前进行操作的管理员
	 */
	private Boolean isCurrentAdmin;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdminDO getAdmin() {
		return admin;
	}

	public void setAdmin(AdminDO admin) {
		this.admin = admin;
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
	
	public Boolean getIsCurrentAdmin() {
		return isCurrentAdmin;
	}
	
	public void setIsCurrentAdmin(Boolean isCurrentAdmin) {
		this.isCurrentAdmin = isCurrentAdmin;
	}

	@Override
	public String toString() {
		return "AnnouncementVO ["
				+ "id=" + id + 
				", admin=" + admin + 
				", title=" + title + 
				", content=" + content + 
				", publishTime=" + publishTime + 
				", isCurrentAdmin=" + isCurrentAdmin +
				"]";
	}
	
}
