package com.xiaohuashifu.tm.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.constant.BookState;
import com.xiaohuashifu.tm.dao.BookMapper;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.CacheService;
import com.xiaohuashifu.tm.service.FileService;
import com.xiaohuashifu.tm.service.constant.BookConstant;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;

@Service("bookService")
public class BookServiceImpl implements BookService {

	private final BookMapper bookMapper;
	private final FileService fileService;
	private final CacheService cacheService;
	
	@Autowired
	public BookServiceImpl(BookMapper bookMapper, FileService fileService, CacheService cacheService) {
		this.bookMapper = bookMapper;
		this.fileService = fileService;
		this.cacheService = cacheService;
	}

	@Override
	@AdminLog(value = "'添加书籍'", type = AdminLogType.INSERT)
	public Result<BookDO> saveBook(BookDO book, MultipartFile cover) {
		if (cover != null) {
			String coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
			book.setCoverUrl(coverUrl);
		}
		int count = bookMapper.insert(book);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert book failed.");
		}
		return Result.success(book);
	}
	
	@Override
	@AdminLog(value = "'删除书籍'", type = AdminLogType.DELETE)
	public Result<Integer> deleteBook(Integer id) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if (coverUrl != null) {
			fileService.delete(coverUrl);
		}
		int count = bookMapper.delete(id);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete book failed.");
		}
		return Result.success(id);
	}
	
	@Override
	@AdminLog(value = "'更新书籍'", type = AdminLogType.UPDATE)
	public Result<Map<String, BookDO>> updateBook(BookDO book) {
		Result<BookDO> result = getBookById(book.getId());
		if (!result.isSuccess()) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Error happened when getting the book data");
		}
		BookDO oldBook = result.getData();
		if (book.getNumbering() == null || "".equals(book.getNumbering())) book.setNumbering(oldBook.getNumbering());
		if (book.getName() == null || "".equals(book.getName())) book.setName(oldBook.getName());
		if (book.getCoverUrl() == null || "".equals(book.getCoverUrl())) book.setCoverUrl(oldBook.getCoverUrl());
		if (book.getState() == null) book.setState(oldBook.getState());
		if (book.getAvailable() == null) book.setAvailable(oldBook.getAvailable());
		int count = bookMapper.updateBook(book);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "update book failed.");
		}
		Map<String, BookDO> map = new HashMap<String, BookDO>();
		map.put("oldValue", oldBook);
		map.put("newValue", book);
		return Result.success(map);
	}

	@Override
	public Result<Integer> updateCover(Integer id, MultipartFile cover) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if (coverUrl != null || !"".equals(coverUrl)) {
			fileService.delete(coverUrl);
		}
		coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
		int count = bookMapper.updateCover(id, coverUrl);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update book coverurl failed.");
		}
		return Result.success(id);
	}
	
	@Override
	public Result<BookDO> getBookById(Integer id){
		return Result.success(bookMapper.getBookById(id));
	}

	/**
	 * 查询书籍列表
	 * @param bookQuery 查询参数
	 * @return PageInfo<BookDO>
	 */
	@Override
	public Result<PageInfo<BookDO>> listBooks(BookQuery bookQuery) {
		PageHelper.startPage(bookQuery);
		PageInfo<BookDO> pageInfo = new PageInfo<>(bookMapper.listBooks(bookQuery));
		if (pageInfo.getList().size() == 0) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
		}
		return Result.success(pageInfo);
	}

	@Override
	@Transactional
	public Result<BookLogDO> saveBookLog(BookLogDO bookLog) {
		BookDO book = new BookDO();
		book.setId(bookLog.getBookId());
		if (BookLogState.BOOKED.equals(bookLog.getState())) {
			bookLog.setExpirationTime(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
			book.setState(BookState.BOOKED);
			String key = new StringBuilder("bookId:").append(bookLog.getBookId()).toString();
			cacheService.set(key, bookLog.getUserId().toString());
			cacheService.expire(key, 86400);
		}else if (BookLogState.BORROWED.equals(bookLog.getState())) {
			bookLog.setExpirationTime(Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant()));
			book.setState(BookState.BORROWED);
			cacheService.del(new StringBuilder("bookId:").append(bookLog.getBookId()).toString());
		}else if (BookLogState.GIVE_UP.equals(bookLog.getState())) {
			bookLog.setExpirationTime(new Date());
			book.setState(BookState.IDLE);
			cacheService.del(new StringBuilder("bookId:").append(bookLog.getBookId()).toString());
		}
		int count = bookMapper.insertBookLog(bookLog);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert book log failed.");
		}
		count = bookMapper.updateBook(book);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update book state failed.");
		}
		return Result.success(bookLog);
	}
	
}
