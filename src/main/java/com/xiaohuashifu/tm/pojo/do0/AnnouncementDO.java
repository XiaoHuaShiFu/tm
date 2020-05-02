package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

public class AnnouncementDO {
	private Integer id;
	private String title;
	private String content;
	private Date publish_time;
	private Date create_time;
	private Date update_time;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(Date publish_time) {
		this.publish_time = publish_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return "AnnouncementDO [id=" + id +
				", title=" + title +
				", content=" + content +
				", publish_time=" + publish_time +
				", create_time=" + create_time +
				", update_time=" + update_time + "]";
	}
	
}
