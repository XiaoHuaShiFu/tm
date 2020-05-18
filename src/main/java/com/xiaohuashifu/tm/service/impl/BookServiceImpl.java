package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.constant.BookState;
import com.xiaohuashifu.tm.dao.BookMapper;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.CacheService;
import com.xiaohuashifu.tm.service.FileService;
import com.xiaohuashifu.tm.service.constant.BookConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	public Result<BookDO> saveBook(BookDO book, MultipartFile cover) {
		if (cover != null) {
			String coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
			book.setCoverUrl(coverUrl);
		}
		int count = bookMapper.insertBook(book);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert book failed.");
		}
		return Result.success(book);
	}
	
	@Override
	public Result<Integer> deleteBook(Integer id) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if (coverUrl != null) {
			fileService.delete(coverUrl);
		}
		int count = bookMapper.deleteBook(id);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete book failed.");
		}
		return Result.success(id);
	}
	
	@Override
	@Transactional
	public Result<BookDO> updateBook(BookDO book, MultipartFile cover) {
		int count = bookMapper.updateBook(book);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "update book failed.");
		}
		if (cover != null) {
			Result result = updateCover(book.getId(), cover);
			if (!result.isSuccess()) {
				return result;
			}
			return getBook(book.getId());
		}
		return Result.success(book);
	}

	@Override
	public Result updateCover(Integer id, MultipartFile cover) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if (coverUrl != null && !"".equals(coverUrl)) {
			fileService.delete(coverUrl);
		}
		coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
		int count = bookMapper.updateCover(id, coverUrl);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update book coverurl failed.");
		}
		return Result.success(id);
	}

	/**
	 * 通过Id获取书籍
	 * @param id 编号
	 * @return BookDO
	 */
	@Override
	public Result<BookDO> getBook(Integer id){
		BookDO book = bookMapper.getBookById(id);
		if (book == null) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
		}
		return Result.success(book);
	}

	@Override
	public Result<List<BookDO>> listUnreturnedBooksByUserId(Integer id) {
		List<Integer> bookIds = bookMapper.listBorrowedBookIdByUserId(id);
		// 获取最近借取bookIds所对应的书籍的bookLog
		List<BookLogDO> newestBookLogs = bookIds.stream().map((bookId)->{
			return bookMapper.getBookLog(bookId, BookLogState.BORROWED);
		}).collect(Collectors.toList());
		// 求id和newestBookLogs交集后对应的状态为borrowed的书
		List<BookDO> books = newestBookLogs.stream().filter((bookLog)->{
			return id.equals(bookLog.getUserId());
		}).map((bookLog)->{
			return bookMapper.getBookById(bookLog.getBookId());
		}).filter((book)->{
			return BookState.BORROWED.equals(book.getState());
		}).collect(Collectors.toList());
		
		if (books.size() == 0) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Get unreturned books failed.");
		}
		return Result.success(books);
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
	public Result<Integer> countBooks(BookQuery bookQuery) {
		Integer count = bookMapper.countBooks(bookQuery);
		if (count < 1) {
			return Result.fail(ErrorCode.INVALID_OPERATION_NOT_FOUND, "Not found.");
		}
		return Result.success(count);
	}
	
	@Override
	@Transactional
	public Result<BookLogDO> saveBookLog(BookLogDO bookLog) {
		BookDO book = new BookDO();
		BookDO prevBook = bookMapper.getBookById(bookLog.getBookId());
		book.setId(bookLog.getBookId());
		if (BookLogState.BOOKED.equals(bookLog.getState())) {
			if (!BookState.IDLE.equals(prevBook.getState())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "The book is not idle");
			}
			bookLog.setExpirationTime(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
			book.setState(BookState.BOOKED);
			String key = "bookId:" + bookLog.getBookId();
			cacheService.set(key, bookLog.getUserId().toString());
			cacheService.expire(key, 86400);
		}else if (BookLogState.BORROWED.equals(bookLog.getState())) {
			if (!BookState.BOOKED.equals(prevBook.getState())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "The book has not been booked");
			}
			BookLogDO prevBookLog = bookMapper.getBookLog(bookLog.getBookId(), BookLogState.BOOKED);
			// 防止其他没有book这本书的用户误操作
			if (!bookLog.getUserId().equals(prevBookLog.getUserId())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "Current user has not book this book");
			}
			bookLog.setExpirationTime(Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant()));
			book.setState(BookState.BORROWED);
			cacheService.del("bookId:" + bookLog.getBookId());
		}else if (BookLogState.GIVE_UP.equals(bookLog.getState())) {
			if (!BookState.BOOKED.equals(prevBook.getState())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "The book has not been booked");
			}
			BookLogDO prevBookLog = bookMapper.getBookLog(bookLog.getBookId(), BookLogState.BOOKED);
			// 防止其他没有book这本书的用户误操作
			if (!bookLog.getUserId().equals(prevBookLog.getUserId())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "Current user has not book this book");
			}
			bookLog.setExpirationTime(new Date());
			book.setState(BookState.IDLE);
			cacheService.del("bookId:" + bookLog.getBookId());
		}else if (BookLogState.RETURNED.equals(bookLog.getState())) {
			if (!BookState.BORROWED.equals(prevBook.getState())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "The book has not lent out");
			}
			BookLogDO prevBookLog = bookMapper.getBookLog(bookLog.getBookId(), BookLogState.BORROWED);
			// 防止其他没有borrow这本书的用户误操作
			if (!bookLog.getUserId().equals(prevBookLog.getUserId())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "Current user has not borrow this book");
			}
			bookLog.setReturnTime(new Date());
			book.setState(BookState.IDLE);
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

	/**
	 * 通过查询参数查找图书日志
	 * @param bookLogQuery 查询参数
	 * @return PageInfo<BookLogDO>
	 */
	@Override
	public Result<PageInfo<BookLogDO>> listBookLogs(BookLogQuery bookLogQuery) {
		PageHelper.startPage(bookLogQuery);
		PageInfo<BookLogDO> pageInfo = new PageInfo<>(bookMapper.listBookLogs(bookLogQuery));
		if (pageInfo.getList().size() == 0) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
		}
		return Result.success(pageInfo);
	}

}
