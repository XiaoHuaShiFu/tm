package com.xiaohuashifu.tm.pojo.query;

import java.util.List;

public class PointLogQuery {

	private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

	private List<Integer> idList;

    private Integer userId;

	/**
	 * 模糊搜索
	 */
	private String content;
    
    public PointLogQuery() {}
    
    public PointLogQuery(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public PointLogQuery(Integer pageNum, Integer pageSize, Integer id, List<Integer> idList, Integer userId,
						 String content) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.id = id;
		this.idList = idList;
		this.userId = userId;
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PointLogQuery{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", id=" + id +
				", idList=" + idList +
				", userId=" + userId +
				", content='" + content + '\'' +
				'}';
	}
}
