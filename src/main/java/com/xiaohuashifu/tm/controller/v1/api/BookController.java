package com.xiaohuashifu.tm.controller.v1.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.constant.BookState;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.TokenService;

@RestController
@RequestMapping("v1/books")
@Validated
public class BookController {

	private final BookService bookService;
	private final TokenService tokenService;
	
	@Autowired
	public BookController(BookService bookService, TokenService tokenService) {
		this.bookService = bookService;
		this.tokenService = tokenService;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public Boolean insertBook(@RequestPart("bookInfo") BookDO book,
							  @RequestPart(value = "cover", required = false) MultipartFile cover) {
		Result<?> result = bookService.saveBook(book, cover);
		return result.isSuccess();
	}
	
//	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.DELETE)
	public Boolean deleteBook(@RequestParam("id") Integer id) {
		Result<?> result = bookService.deleteBook(id);
		return result.isSuccess();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT)
	public Boolean updateBook(@RequestPart("bookInfo") BookDO book) {
		Result<?> result = bookService.updateBook(book);
		return result.isSuccess();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "cover", method = RequestMethod.PUT)
	public Boolean updateCover(@RequestParam("id") Integer id, @RequestParam("cover") MultipartFile cover) {
		Result<?> result = bookService.updateCover(id, cover);
		return result.isSuccess();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{pageNum}", method = RequestMethod.GET)
	public Object getBooks(@PathVariable("pageNum") Integer pageNum) {
		BookQuery bookQuery = new BookQuery(pageNum);
		Result<PageInfo<BookDO>> result = bookService.listBooks(bookQuery);
		if (!result.isSuccess()) {
			return result;
		}
		PageInfo<BookDO> booksInfo = result.getData();
		List<BookDO> books = booksInfo.getList();
		return books;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{bookId}", method = RequestMethod.POST)
	@TokenAuth(tokenType = TokenType.USER)
	public Object postBookLog(TokenAO tokenAO, @PathVariable("bookId") Integer bookId,
			@RequestParam("state") BookLogState bookLogState) {
		Result<BookDO> bookResult = bookService.getBookById(bookId);
		if (!bookResult.isSuccess()) {
			return bookResult;
		}
		BookDO book = bookResult.getData();
		if (!BookState.IDLE.equals(book.getState())) {
			return Result.fail(ErrorCode.FORBIDDEN, "The book is not available");
		}
		BookLogDO bookLog = new BookLogDO();
		bookLog.setBookId(bookId);
		bookLog.setUserId(tokenAO.getId());
		bookLog.setBorrowTime(new Date());
		bookLog.setState(bookLogState);
		Result<BookLogDO> result = bookService.saveBookLog(bookLog);
		return result;
	}
	
}
