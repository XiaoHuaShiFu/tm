package com.xiaohuashifu.tm.pojo.do0;

import java.sql.Timestamp;

import com.xiaohuashifu.tm.constant.MeetingState;

public class MeetingDO {
	private Integer id;
	private Integer userId;
	private String name;
	private Timestamp startTime;
	private Timestamp endTime;
	private String place;
	private String department;
	private String content;
	private MeetingState state;
	
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
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Timestamp endTime) {
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
	
}
