package com.xiaohuashifu.tm.pojo.vo;

import java.util.Date;

import com.xiaohuashifu.tm.constant.BookLogState;

public class BookLogVO {
	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Date borrowTime;
	private Date expirationTime;
	private Date returnTime;
	private BookLogState state;
	private UserVO user;
	private BookVO book;

	public BookLogVO() {
	}

	public BookLogVO(Integer id, Integer userId, Integer bookId, Date borrowTime, Date expirationTime, Date returnTime,
					 BookLogState state, UserVO user, BookVO book) {
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.borrowTime = borrowTime;
		this.expirationTime = expirationTime;
		this.returnTime = returnTime;
		this.state = state;
		this.user = user;
		this.book = book;
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

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public BookVO getBook() {
		return book;
	}

	public void setBook(BookVO book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "BookLogVO{" +
				"id=" + id +
				", userId=" + userId +
				", bookId=" + bookId +
				", borrowTime=" + borrowTime +
				", expirationTime=" + expirationTime +
				", returnTime=" + returnTime +
				", state=" + state +
				", user=" + user +
				", book=" + book +
				'}';
	}
}
