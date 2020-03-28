package com.xiaohuashifu.tm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	public Result saveBook(BookDO book, MultipartFile cover) {
		if(cover != null) {
			String coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
			book.setCoverUrl(coverUrl);
		}
		bookMapper.insert(book);
		return Result.success();
	}
	
	@Override
	public Result deleteBook(Integer id) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if(coverUrl != null) {
			fileService.delete(coverUrl);
		}
		bookMapper.delete(id);
		return Result.success();
	}
	
	public Result updateBook(BookDO book) {
		Result<BookDO> result = getBookById(book.getId());
		if(result.isSuccess()) {
			BookDO oldBook = result.getData();
			if(book.getNumbering() == null || "".equals(book.getNumbering())) book.setNumbering(oldBook.getNumbering());
			if(book.getName() == null || "".equals(book.getName())) book.setName(oldBook.getName());
			if(book.getCoverUrl() == null || "".equals(book.getCoverUrl())) book.setCoverUrl(oldBook.getCoverUrl());
			if(book.getState() == null) book.setState(oldBook.getState());
			if(book.getAvailable() == null) book.setAvailable(oldBook.getAvailable());
		}else {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Error happened when getting the book data");
		}
		bookMapper.updateBook(book);
		return Result.success();
	}

	public Result updateCover(Integer id, MultipartFile cover) {
		String coverUrl = bookMapper.getCoverUrlById(id);
		if(coverUrl != null || !"".equals(coverUrl)) {
			fileService.delete(coverUrl);
		}
		coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
		bookMapper.updateCover(id, coverUrl);
		return Result.success();
	}
	
	public Result<BookDO> getBookById(Integer id){
		return Result.success(bookMapper.getBookById(id));
	}
	
	public Result<PageInfo<BookDO>> listBooks(BookQuery bookQuery) {
		//设置分页规则
		PageHelper.startPage(bookQuery);
		PageInfo<BookDO> books = new PageInfo<BookDO>((Page<BookDO>) bookMapper.listBooks());
		return Result.success(books);
	}
	
}
