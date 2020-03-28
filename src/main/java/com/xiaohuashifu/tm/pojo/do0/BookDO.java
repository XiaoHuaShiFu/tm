package com.xiaohuashifu.tm.pojo.do0;

public class BookDO {
	private Integer id;
	private String numbering;
	private String name;
	private String coverUrl;
	private BookState state;
	private Integer available;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getCoverUrl() {
		return coverUrl;
	}
	
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	public BookState getState() {
		return state;
	}
	
	public void setState(BookState state) {
		this.state = state;
	}
	
	public Integer getAvailable() {
		return available;
	}
	
	public void setAvailable(Integer available) {
		this.available = available;
	}
	
	@Override
	public String toString() {
		return "Book{" +
                "id=" + id +
                ", numbering=" + numbering +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", available=" + available +
                '}';
	}
	
}
