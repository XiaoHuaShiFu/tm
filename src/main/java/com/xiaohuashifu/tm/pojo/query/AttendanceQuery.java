package com.xiaohuashifu.tm.pojo.query;

import java.util.List;

import com.xiaohuashifu.tm.constant.Department;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class AttendanceQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

    private Integer userId;

    private List<Integer> idList;

    private Boolean available;
    
    private Department department;
    
    private Integer month;

    public AttendanceQuery() {}
    
    public AttendanceQuery(Integer pageNum) {
    	this.pageNum = pageNum;
    }

    public AttendanceQuery(Integer pageNum, Integer pageSize, Integer id, Integer userId, List<Integer> idList,
                           Boolean available) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.id = id;
        this.userId = userId;
        this.idList = idList;
        this.available = available;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	@Override
    public String toString() {
        return "AttendanceQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", userId=" + userId +
                ", idList=" + idList +
                ", available=" + available +
                ", department=" + department +
                ", month=" + month +
                '}';
    }
}
