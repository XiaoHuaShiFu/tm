package com.xiaohuashifu.tm.pojo.query;

import com.xiaohuashifu.tm.constant.BookLogState;

import java.util.List;

public class BookLogQuery {
	private Integer pageNum = 1;

	private Integer pageSize = 10;

	private Integer id;

	private List<Integer> idList;

	private Integer userId;

	private Integer bookId;

	private BookLogState state;
	
	public BookLogQuery() {
	}
	
	public BookLogQuery(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public BookLogQuery(Integer pageNum, Integer pageSize, Integer id, List<Integer> idList, Integer userId,
						Integer bookId, BookLogState state) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.id = id;
		this.idList = idList;
		this.userId = userId;
		this.bookId = bookId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public BookLogState getState() {
		return state;
	}

	public void setState(BookLogState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "BookLogQuery{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", id=" + id +
				", idList=" + idList +
				", userId=" + userId +
				", bookId=" + bookId +
				", state=" + state +
				'}';
	}
}
