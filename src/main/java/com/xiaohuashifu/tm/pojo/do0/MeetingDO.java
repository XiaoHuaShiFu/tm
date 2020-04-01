package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

import com.xiaohuashifu.tm.constant.MeetingState;
import com.xiaohuashifu.tm.pojo.group.Group;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.group.GroupPut;
import com.xiaohuashifu.tm.validator.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MeetingDO {

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.", groups = {GroupPut.class})
	@Id(groups = {Group.class})
	private Integer id;

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.", groups = {GroupPut.class})
	@Id(groups = {Group.class})
	private Integer userId;

	@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The name must be not blank.", groups = {GroupPost.class})
	@Size(message = "INVALID_PARAMETER_SIZE: The size of name must be between 1 to 50.",
			min = 1, max = 50,
			groups = {Group.class})
	private String name;

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The startTime must be not null.",
			groups = {GroupPost.class})
	private Date startTime;

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The endTime must be not null.",
			groups = {GroupPost.class})
	private Date endTime;

	@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The place must be not blank.",
			groups = {GroupPost.class})
	@Size(message = "INVALID_PARAMETER_SIZE: The size of place must be between 1 to 100.",
			min = 1, max = 100,
			groups = {Group.class})
	private String place;

	@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The department must be not blank.",
			groups = {GroupPost.class})
	private String department;

	@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The content must be not blank.",
			groups = {GroupPost.class})
	@Size(message = "INVALID_PARAMETER_SIZE: The size of content must be between 1 to 500.",
			min = 1, max = 100,
			groups = {Group.class})
	private String content;

	private MeetingState state;

	private Date createTime;

	private Date updateTime;

	public MeetingDO() {
	}

	public MeetingDO(Integer id, Integer userId, String name, Date startTime, Date endTime,
					 String place, String department, String content, MeetingState state, Date createTime,
					 Date updateTime) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.place = place;
		this.department = department;
		this.content = content;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
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
		return "MeetingDO{" +
				"id=" + id +
				", userId=" + userId +
				", name='" + name + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", place='" + place + '\'' +
				", department='" + department + '\'' +
				", content='" + content + '\'' +
				", state=" + state +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
