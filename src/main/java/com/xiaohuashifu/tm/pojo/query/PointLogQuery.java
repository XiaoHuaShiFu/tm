package com.xiaohuashifu.tm.pojo.query;

public class PointLogQuery {
	private Integer pageNum = 1;
    private Integer pageSize = 5;  //默认一页5条数据
    private Integer userId;
    
    public PointLogQuery(Integer pageNum, Integer userId) {
    	this.pageNum = pageNum;
    	this.userId = userId;
    }
    
    public PointLogQuery(Integer pageNum, Integer pageSize, Integer userId) {
    	this.pageNum = pageNum;
    	this.pageSize = pageSize;
    	this.userId = userId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
    public String toString() {
        return "PointLogQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", userId=" + userId +
                '}';
    }
    
}
