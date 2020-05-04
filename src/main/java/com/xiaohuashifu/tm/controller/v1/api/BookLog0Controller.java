package com.xiaohuashifu.tm.controller.v1.api;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.BookLogManager;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.group.GroupPut;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.vo.BookLogVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.validator.annotation.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述: 借书信息模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/books/logs")
@Validated
public class BookLog0Controller {

	private final BookLogManager bookLogManager;

	@Autowired
	public BookLog0Controller(BookLogManager bookLogManager) {
		this.bookLogManager = bookLogManager;
	}

	/**
	 * 创建BookLog并返回UBookLog
	 * @param bookLogDO 借书信息
	 * @return BookLogVO
	 *
	 * @success:
	 * HttpStatus.CREATED
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@TokenAuth(tokenType = TokenType.USER)
	@ErrorHandler
	public Object post(TokenAO tokenAO, @Validated(GroupPost.class) BookLogDO bookLogDO) {
		// 越权新增资源
		if (!tokenAO.getId().equals(bookLogDO.getUserId())) {
			return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
		}
		Result<BookLogVO> result = bookLogManager.saveBookLog(bookLogDO);
		return result.isSuccess() ? result.getData() : result;
	}


	/**
	 * 获取BookLog
	 * @param id 借书信息编号
	 * @return BookLogVO
	 *
	 * @success:
	 * HttpStatus.OK
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object get(@PathVariable @Id Integer id) {
		Result<BookLogVO> result = bookLogManager.getBookLog(id);
		return result.isSuccess() ? result.getData() : result;
	}

	/**
	 * 查询借书信息
	 * @param query 查询参数
	 * @return PageInfo<BookLogVO>
	 *
	 * @success:
	 * HttpStatus.OK
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object get(BookLogQuery query) {
		Result<PageInfo<BookLogVO>> result = bookLogManager.listBookLogs(query);
		return result.isSuccess() ? result.getData() : result;
	}

	/**
	 * 更新借书信息
	 * @param bookLogDO 借书信息
	 * @return BookLogVO
	 *
	 * @success:
	 * HttpStatus.OK
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.QRCODE})
	@ErrorHandler
	public Object put(@Validated(GroupPut.class) BookLogDO bookLogDO) {
		Result<BookLogVO> result = bookLogManager.updateBookLog(bookLogDO);
		return result.isSuccess() ? result.getData() : result;
	}

}
