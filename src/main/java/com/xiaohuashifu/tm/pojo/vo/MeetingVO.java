package com.xiaohuashifu.tm.pojo.vo;

import com.xiaohuashifu.tm.constant.MeetingState;
import java.util.Date;

public class MeetingVO {

	private Integer id;

	private Integer userId;

	private String name;

	private Date startTime;

	private Date endTime;

	private String place;

	private String department;

	private String content;

	private MeetingState state;


	public MeetingVO() {
	}

	public MeetingVO(Integer id, Integer userId, String name, Date startTime, Date endTime, String place,
					 String department, String content, MeetingState state) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.place = place;
		this.department = department;
		this.content = content;
		this.state = state;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MeetingState getState() {
		return state;
	}

	public void setState(MeetingState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MeetingVO{" +
				"id=" + id +
				", userId=" + userId +
				", name='" + name + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", place='" + place + '\'' +
				", department='" + department + '\'' +
				", content='" + content + '\'' +
				", state=" + state +
				'}';
	}
}
