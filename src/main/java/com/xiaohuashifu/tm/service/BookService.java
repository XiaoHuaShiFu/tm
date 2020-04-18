package com.xiaohuashifu.tm.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.result.Result;

public interface BookService {
	Result<BookDO> saveBook(BookDO book, MultipartFile cover);
	Result<Integer> deleteBook(Integer id);
	Result<Map<String, BookDO>> updateBook(BookDO book);
	Result<Integer> updateCover(Integer id, MultipartFile cover);
	Result<BookDO> getBookById(Integer id);
	Result<PageInfo<BookDO>> getBooksByName(String name, BookQuery bookQuery);
	Result<PageInfo<BookDO>> listBooks(BookQuery bookQuery);
	
	Result<BookLogDO> borrowBook(BookLogDO bookLog);
}
