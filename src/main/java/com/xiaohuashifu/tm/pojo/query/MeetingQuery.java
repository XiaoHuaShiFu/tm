package com.xiaohuashifu.tm.pojo.query;


import com.xiaohuashifu.tm.constant.Department;
import com.xiaohuashifu.tm.constant.MeetingState;

import java.util.List;

public class MeetingQuery {
	private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

	private List<Integer> idList;
	/**
	 * 模糊搜素
	 */
	private String name;

	/**
	 * 模糊搜素
	 */
    private String place;

	private Department department;

    private MeetingState state;

    public MeetingQuery() {}

	public MeetingQuery(Integer pageNum, Integer pageSize, Integer id, List<Integer> idList, String name, String place,
						Department department, MeetingState state) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.id = id;
		this.idList = idList;
		this.name = name;
		this.place = place;
		this.department = department;
		this.state = state;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public MeetingState getState() {
		return state;
	}

	public void setState(MeetingState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MeetingQuery{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", id=" + id +
				", idList=" + idList +
				", name='" + name + '\'' +
				", place='" + place + '\'' +
				", department=" + department +
				", state=" + state +
				'}';
	}
}
