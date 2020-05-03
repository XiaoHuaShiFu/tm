package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.query.BookQuery;

@Mapper
public interface BookMapper {
	int insert(@Param("book") BookDO book);
	int delete(Integer id);
	int updateBook(BookDO book);
	int updateCover(@Param("id") Integer id, @Param("cover_url") String coverUrl);
	String getCoverUrlById(Integer id);
	BookDO getBookById(Integer id);

	/**
	 * 查询用户未还的书籍
	 * @param id 用户id
	 * @return 用户未还的book
	 */
	List<BookDO> listUnreturnedBooksByUserId(Integer id);
	
	/**
	 * 查询书籍
	 * @param bookQuery 查询参数
	 * @return BookDOList
	 */
	List<BookDO> listBooks(BookQuery bookQuery);
	
	int insertBookLog(@Param("bookLog") BookLogDO bookLog);
	int updateBookLog(@Param("bookLog") BookLogDO bookLog);
	
	/**
	 * 查询某个状态下的某本书的最新记录
	 * @param bookId 书籍Id
	 * @param state 
	 * @return BookLogDO
	 */
	BookLogDO getBookLog(@Param("bookId") Integer bookId, @Param("state") BookLogState state);
	
	List<BookLogDO> listBookLogs(BookLogQuery bookLogQuery);
}
