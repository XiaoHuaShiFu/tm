package com.xiaohuashifu.tm.controller.v1.api;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.constant.BookState;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.pojo.vo.BookVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/books")
@Validated
public class BookController {

	private final BookService bookService;
	private final Mapper mapper;
	
	@Autowired
	public BookController(BookService bookService, Mapper mapper) {
		this.bookService = bookService;
		this.mapper = mapper;
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

	/**
	 * 更新书籍
	 * @param book 要更新的信息
	 * @return 更新后的信息
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
//	@TokenAuth(tokenType = {TokenType.ADMIN})
	public Object put(@RequestPart("bookInfo") BookDO book,
			@RequestPart(value = "cover", required = false) MultipartFile cover) {
		Result<BookDO> result = bookService.updateBook(book, cover);
		return result.isSuccess() ? mapper.map(result.getData(), BookVO.class) : result;
	}

	/**
	 * 查询书籍通过query
	 * @param query 查询参数
	 * @return PageInfo<BookVO>
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object list(BookQuery query) {
		Result<PageInfo<BookDO>> result = bookService.listBooks(query);
		if (!result.isSuccess()) {
			return result;
		}

		PageInfo<BookDO> pageInfo = result.getData();
		List<BookDO> bookDOList = pageInfo.getList();
		List<BookVO> bookVOList = bookDOList.stream()
				.map(bookDO -> mapper.map(bookDO, BookVO.class))
				.collect(Collectors.toList());
		PageInfo<BookVO> pageInfo1 = mapper.map(pageInfo, PageInfo.class);
		pageInfo1.setList(bookVOList);
		return pageInfo;
	}

	/**
	 * 获取书通过id
	 * @param id 书的编号
	 * @return BookVO
	 */
	@RequestMapping(value ="{id}" ,method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object list(@PathVariable Integer id) {
		Result<BookDO> result = bookService.getBookById(id);
		return result.isSuccess() ? mapper.map(result.getData(), BookVO.class) : result;
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
		return result.isSuccess() ? mapper.map(result.getData(), BookLogDO.class) : result;
	}
	
}
