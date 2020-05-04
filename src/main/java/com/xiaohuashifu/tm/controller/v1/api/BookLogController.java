package com.xiaohuashifu.tm.controller.v1.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.BookLogManager;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.vo.BookLogVO;
import com.xiaohuashifu.tm.result.Result;

@RestController
@RequestMapping("v1/bookLogs")
@Validated
public class BookLogController {
	
	private final BookLogManager bookLogManager;
	
	@Autowired
	public BookLogController(BookLogManager bookLogManager) {
		this.bookLogManager = bookLogManager;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	@TokenAuth(tokenType = TokenType.USER)
	public Object listBookLog(TokenAO tokenAO, @RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "bookId", required = false) Integer bookId) {
		BookLogQuery query = new BookLogQuery();
		query.setUserId(tokenAO.getId());
		if (pageNum != null) {
			query.setPageNum(pageNum);
		}
		if (bookId != null) {
			BookDO book = new BookDO();
			book.setId(bookId);
			query.setBook(book);
		}
		Result<PageInfo<BookLogVO>> result = bookLogManager.listBookLogs(query);
		if (!result.isSuccess()) {
			return result;
		}
		PageInfo<BookLogVO> bookLogsInfo = result.getData();
		return bookLogsInfo;
	}
	
}
