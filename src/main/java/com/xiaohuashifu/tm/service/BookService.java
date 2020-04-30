package com.xiaohuashifu.tm.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.result.Result;

public interface BookService {
	Result<BookDO> saveBook(BookDO book, MultipartFile cover);
	Result<Integer> deleteBook(Integer id);
	Result<BookDO> updateBook(BookDO book, MultipartFile cover);
	Result<Integer> updateCover(Integer id, MultipartFile cover);
	Result<BookDO> getBookById(Integer id);
	Result<PageInfo<BookDO>> listBooks(BookQuery bookQuery);
	
	Result<BookLogDO> saveBookLog(BookLogDO bookLog);
	Result<PageInfo<BookLogDO>> listBookLogs(BookLogQuery bookLogQuery);
}
