package com.xiaohuashifu.tm.pojo.query;

import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.pojo.do0.BookDO;

public class BookLogQuery {
	private Integer pageNum = 1;
	private Integer pageSize = 10;
	private Integer userId;
	private BookDO book;
	private BookLogState state;
	
	public BookLogQuery() {}
	
	public BookLogQuery(Integer pageNum) {
		this.pageNum = pageNum;
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

	public BookDO getBook() {
		return book;
	}

	public void setBook(BookDO book) {
		this.book = book;
	}

	public BookLogState getState() {
		return state;
	}

	public void setState(BookLogState state) {
		this.state = state;
	}

}
