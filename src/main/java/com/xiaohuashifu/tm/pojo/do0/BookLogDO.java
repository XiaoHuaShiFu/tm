package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

import com.xiaohuashifu.tm.constant.BookLogState;

public class BookLogDO {
	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Date borrowTime;
	private Date expirationTime;
	private Date returnTime;
	private BookLogState state;
	private Date createTime;
	private Date updateTime;
	
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
	
	public Integer getBookId() {
		return bookId;
	}
	
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	
	public Date getBorrowTime() {
		return borrowTime;
	}
	
	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}
	
	public Date getExpirationTime() {
		return expirationTime;
	}
	
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	
	public Date getReturnTime() {
		return returnTime;
	}
	
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	public BookLogState getState() {
		return state;
	}
	
	public void setState(BookLogState state) {
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
		return "BookLogDO [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", borrowTime=" + borrowTime
				+ ", expirationTime=" + expirationTime + ", returnTime=" + returnTime + ", state=" + state
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}
