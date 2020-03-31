package com.xiaohuashifu.tm.pojo.query;


public class MeetingQuery {
	private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

    private String name;

    private String place;

	private String department;

    private String state;

    public MeetingQuery() {}

	public MeetingQuery(Integer pageNum, Integer pageSize, Integer id, String name, String place, String department,
						String state) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.id = id;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MeetingQuery{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", id=" + id +
				", name='" + name + '\'' +
				", place='" + place + '\'' +
				", department='" + department + '\'' +
				", state='" + state + '\'' +
				'}';
	}
}
