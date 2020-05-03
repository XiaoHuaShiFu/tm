package com.xiaohuashifu.tm.pojo.query;

import com.xiaohuashifu.tm.constant.BookState;

import java.util.List;

public class BookQuery {

	private Integer pageNum = 1;
	/**
	 * 默认一页10条数据
	 */
    private Integer pageSize = 10;

    private Integer id;

	private List<Integer> idList;

    private String numbering;

	/**
	 * 模糊搜索
	 */
    private String name;

	private Boolean available;

	private BookState state;
	
	/**
     * 用于用户查询未还的书籍，该值为true时生效
     */
    private Boolean unreturned;
    
    public BookQuery() {}
    
    public BookQuery(Integer pageNum) {
    	this.pageNum = pageNum;
    }
    
	public BookQuery(Integer pageNum, Integer pageSize, Integer id, List<Integer> idList, String numbering,
			String name, Boolean available, BookState state, Boolean unreturned) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.id = id;
		this.idList = idList;
		this.numbering = numbering;
		this.name = name;
		this.available = available;
		this.state = state;
		this.unreturned = unreturned;
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

	public String getNumbering() {
		return numbering;
	}

	public void setNumbering(String numbering) {
		this.numbering = numbering;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public BookState getState() {
		return state;
	}

	public void setState(BookState state) {
		this.state = state;
	}

	public Boolean getUnreturned() {
		return unreturned;
	}
	
	public void setUnreturned(Boolean unreturned) {
		this.unreturned = unreturned;
	}
	
	@Override
	public String toString() {
		return "BookQuery{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", id=" + id +
				", idList=" + idList +
				", numbering='" + numbering + '\'' +
				", name='" + name + '\'' +
				", available=" + available +
				", state=" + state +
				", unreturn=" + unreturned +
				'}';
	}
}
