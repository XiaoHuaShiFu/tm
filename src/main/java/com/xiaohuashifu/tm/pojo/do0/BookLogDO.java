package com.xiaohuashifu.tm.pojo.do0;

import java.util.Date;

import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.pojo.group.Group;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.group.GroupPut;
import com.xiaohuashifu.tm.validator.annotation.Id;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;

public class BookLogDO {

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.", groups = {GroupPut.class})
	@Null(message = "INVALID_PARAMETER: The id must be null.", groups = {GroupPost.class})
	@Id(groups = {Group.class})
	private Integer id;

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.", groups = {GroupPost.class})
	@Id(groups = {Group.class})
	private Integer userId;

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The bookId must be not null.", groups = {GroupPost.class})
	@Id(groups = {Group.class})
	private Integer bookId;

	@Null(message = "INVALID_PARAMETER: The borrowTime must be null.", groups = {GroupPost.class})
	private Date borrowTime;

	@Null(message = "INVALID_PARAMETER: The expirationTime must be null.", groups = {GroupPost.class})
	private Date expirationTime;

	@Null(message = "INVALID_PARAMETER: The returnTime must be null.", groups = {GroupPost.class})
	private Date returnTime;

	@Null(message = "INVALID_PARAMETER: The state must be null.", groups = {GroupPost.class})
	private BookLogState state;

	@Null(message = "INVALID_PARAMETER: The createTime must be null.", groups = {GroupPost.class})
	private Date createTime;

	@Null(message = "INVALID_PARAMETER: The updateTime must be null.", groups = {GroupPost.class})
	private Date updateTime;

	public BookLogDO() {
	}

	public BookLogDO(Integer id, Integer userId, Integer bookId, Date borrowTime, Date expirationTime,
					 Date returnTime, BookLogState state, Date createTime, Date updateTime) {
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.borrowTime = borrowTime;
		this.expirationTime = expirationTime;
		this.returnTime = returnTime;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
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
		return "BookLogDO{" +
				"id=" + id +
				", userId=" + userId +
				", bookId=" + bookId +
				", borrowTime=" + borrowTime +
				", expirationTime=" + expirationTime +
				", returnTime=" + returnTime +
				", state=" + state +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
