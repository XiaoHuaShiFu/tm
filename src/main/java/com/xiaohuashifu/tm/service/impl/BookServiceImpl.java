package com.xiaohuashifu.tm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.dao.BookMapper;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.FileService;
import com.xiaohuashifu.tm.service.constant.BookConstant;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;

@Service("bookService")
public class BookServiceImpl implements BookService {

	private final BookMapper bookMapper;
	private final FileService fileService;
	
	@Autowired
	public BookServiceImpl(BookMapper bookMapper, FileService fileService) {
		this.bookMapper = bookMapper;
		this.fileService = fileService;
	}

	@Override
	@AdminLog(value = "添加书籍", type = AdminLogType.INSERT)
	public Result<BookDO> saveBook(BookDO book, MultipartFile cover) {
		if(cover != null) {
			String coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
			book.setCoverUrl(coverUrl);
		}
		bookMapper.insert(book);
		return Result.success(book);
	}
	
	@Override
	@AdminLog(value = "删除书籍", type = AdminLogType.DELETE)
	public Result<Integer> deleteBook(Integer id) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if(coverUrl != null) {
			fileService.delete(coverUrl);
		}
		bookMapper.delete(id);
		return Result.success(id);
	}
	
	@Override
	@AdminLog(value = "更新书籍", type = AdminLogType.UPDATE)
	public Result<Map<String, BookDO>> updateBook(BookDO book) {
		Result<BookDO> result = getBookById(book.getId());
		if(!result.isSuccess()) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Error happened when getting the book data");
		}
		BookDO oldBook = result.getData();
		if(book.getNumbering() == null || "".equals(book.getNumbering())) book.setNumbering(oldBook.getNumbering());
		if(book.getName() == null || "".equals(book.getName())) book.setName(oldBook.getName());
		if(book.getCoverUrl() == null || "".equals(book.getCoverUrl())) book.setCoverUrl(oldBook.getCoverUrl());
		if(book.getState() == null) book.setState(oldBook.getState());
		if(book.getAvailable() == null) book.setAvailable(oldBook.getAvailable());
		bookMapper.updateBook(book);
		Map<String, BookDO> map = new HashMap<String, BookDO>();
		map.put("oldBook", oldBook);
		map.put("newBook", book);
		return Result.success(map);
	}

	@Override
	public Result<Integer> updateCover(Integer id, MultipartFile cover) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if(coverUrl != null || !"".equals(coverUrl)) {
			fileService.delete(coverUrl);
		}
		coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
		bookMapper.updateCover(id, coverUrl);
		return Result.success(id);
	}
	
	@Override
	public Result<BookDO> getBookById(Integer id){
		return Result.success(bookMapper.getBookById(id));
	}
	
	@Override
	public Result<PageInfo<BookDO>> listBooks(BookQuery bookQuery) {
		//设置分页规则
		PageHelper.startPage(bookQuery);
		PageInfo<BookDO> books = new PageInfo<BookDO>((Page<BookDO>) bookMapper.listBooks());
		return Result.success(books);
	}
	
}
