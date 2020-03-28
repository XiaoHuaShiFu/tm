package com.xiaohuashifu.tm.pojo.query;

public class MeetingQuery {
	private Integer pageNum = 1;
    private Integer pageSize = 5;  //默认一页5条数据
    
    public MeetingQuery() {}
    
    public MeetingQuery(Integer pageNum) {
    	this.pageNum = pageNum;
    }
    
    public MeetingQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
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
    
	@Override
    public String toString() {
        return "MeetingQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
	
}
