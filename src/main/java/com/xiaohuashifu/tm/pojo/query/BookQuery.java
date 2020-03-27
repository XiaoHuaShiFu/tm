package com.xiaohuashifu.tm.pojo.query;

public class BookQuery {
	private Integer pageNum = 1;
    private Integer pageSize = 5;  //默认一页5条数据
    
    public BookQuery() {}
    
    public BookQuery(Integer pageNum) {
    	this.pageNum = pageNum;
    }
    
    public BookQuery(Integer pageNum, Integer pageSize) {
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
        return "BookQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
