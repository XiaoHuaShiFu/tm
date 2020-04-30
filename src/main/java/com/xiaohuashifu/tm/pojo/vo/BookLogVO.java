package com.xiaohuashifu.tm.pojo.vo;

import java.util.Date;

import com.xiaohuashifu.tm.constant.BookLogState;

public class BookLogVO {
	private Integer id;
	private UserVO user;
	private BookVO book;
	private Date borrowTime;
	private Date expirationTime;
	private Date returnTime;
	private BookLogState state;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO userVO) {
		this.user = userVO;
	}

	public BookVO getBook() {
		return book;
	}

	public void setBook(BookVO book) {
		this.book = book;
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

	@Override
	public String toString() {
		return "BookLogVO [id=" + id + ", user=" + user + ", book=" + book + ", borrowTime=" + borrowTime
				+ ", expirationTime=" + expirationTime + ", returnTime=" + returnTime + ", state=" + state + "]";
	}

}
