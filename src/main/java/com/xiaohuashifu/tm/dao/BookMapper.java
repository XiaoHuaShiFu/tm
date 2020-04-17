package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;

@Mapper
public interface BookMapper {
	void insert(@Param("book") BookDO book);
	void delete(Integer id);
	void updateBook(@Param("book") BookDO book);
	void updateCover(@Param("id") Integer id, @Param("cover_url") String coverUrl);
	String getCoverUrlById(Integer id);
	BookDO getBookById(Integer id);
	List<BookDO> listBooks();
	
	void insertBookLog(@Param("bookLog") BookLogDO bookLog);
	void updateBookLog(@Param("bookLog") BookLogDO bookLog);
	void getBookLogByUserId(@Param("id") Integer id);
}
