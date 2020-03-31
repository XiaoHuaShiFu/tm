package com.xiaohuashifu.tm.pojo.do0;

import com.xiaohuashifu.tm.constant.Department;

import java.util.Date;

public class AdminDO {
	private Integer id;
	private String fullName;
	private String jobNumber;
	private String password;
	private Department department;
	private String phone;
	private String email;
	private Integer available;
	private Date createTime;
	private Date updateTime;
	
	public AdminDO() {}

	public AdminDO(Integer id, String fullName, String jobNumber, String password, Department department,
				   String phone, String email, Integer available, Date createTime, Date updateTime) {
		this.id = id;
		this.fullName = fullName;
		this.jobNumber = jobNumber;
		this.password = password;
		this.department = department;
		this.phone = phone;
		this.email = email;
		this.available = available;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
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
		return "AdminDO{" +
				"id=" + id +
				", fullName='" + fullName + '\'' +
				", jobNumber='" + jobNumber + '\'' +
				", password='" + password + '\'' +
				", department=" + department +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", available=" + available +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
