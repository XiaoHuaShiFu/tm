package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;

@Mapper
public interface BookMapper {
	int insert(@Param("book") BookDO book);
	int delete(Integer id);
	int updateBook(BookDO book);
	int updateCover(@Param("id") Integer id, @Param("cover_url") String coverUrl);
	String getCoverUrlById(Integer id);
	BookDO getBookById(Integer id);
	List<BookDO> listBooks(BookQuery bookQuery);
	
	int insertBookLog(@Param("bookLog") BookLogDO bookLog);
	int updateBookLog(@Param("bookLog") BookLogDO bookLog);
	List<BookLogDO> listBookLogs();
}
