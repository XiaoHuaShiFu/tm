package com.xiaohuashifu.tm.manager.impl;

import java.util.ArrayList;
import java.util.List;

import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.service.BookLogService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.manager.BookLogManager;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.vo.BookLogVO;
import com.xiaohuashifu.tm.pojo.vo.BookVO;
import com.xiaohuashifu.tm.pojo.vo.UserVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.UserService;

/**
 * 描述: 借书记录管理层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component("bookLogManager")
public class BookLogManagerImpl implements BookLogManager {

	private final BookLogService bookLogService;

    private final UserService userService;
    
    private final BookService bookService;
    
    private final Mapper mapper;
    
    @Autowired
    public BookLogManagerImpl(BookLogService bookLogService, UserService userService, BookService bookService,
                              Mapper mapper) {
        this.bookLogService = bookLogService;
        this.userService = userService;
    	this.bookService = bookService;
    	this.mapper = mapper;
	}

    /**
     * 保存BookLog
     * @param bookLogDO BookLog
     * @return BookLogVO
     */
    @Override
    public Result<BookLogVO> saveBookLog(BookLogDO bookLogDO) {
        Result<BookLogDO> saveBookLogResult = bookLogService.saveBookLog(bookLogDO);
        if (!saveBookLogResult.isSuccess()) {
            return Result.fail(saveBookLogResult);
        }

        return getBookLog(saveBookLogResult.getData().getId());
    }

    /**
     * 获取BookLogVO
     * @param id 编号
     * @return BookLogVO
     */
    @Override
    public Result<BookLogVO> getBookLog(Integer id) {
        Result<BookLogDO> getBookLogResult = bookLogService.getBookLog(id);
        if (!getBookLogResult.isSuccess()) {
            return Result.fail(getBookLogResult);
        }

        BookLogDO bookLogDO = getBookLogResult.getData();
        // 获取UserVO
        Result<UserDO> getUserResult = userService.getUser(bookLogDO.getUserId());
        if (!getUserResult.isSuccess()) {
            return Result.fail(getUserResult);
        }
        UserDO userDO = getUserResult.getData();
        UserVO userVO = mapper.map(userDO, UserVO.class);

        // 获取BookVO
        Result<BookDO> getBookResult = bookService.getBook(bookLogDO.getBookId());
        if (!getBookResult.isSuccess()) {
            return Result.fail(getBookResult);
        }
        BookDO bookDO = getBookResult.getData();
        BookVO bookVO = mapper.map(bookDO, BookVO.class);

        // 组装
        BookLogVO bookLogVO = mapper.map(bookLogDO, BookLogVO.class);
        bookLogVO.setUser(userVO);
        bookLogVO.setBook(bookVO);
        return Result.success(bookLogVO);
    }

    /**
     * 查询bookLogVO
     * @param query 查询参数
     * @return PageInfo<BookLogVO>
     */
    @Override
    public Result<PageInfo<BookLogVO>> listBookLogs(BookLogQuery query) {
        Result<PageInfo<BookLogDO>> result = bookService.listBookLogs(query);
        if (!result.isSuccess()) {
            return Result.fail(result);
        }
        PageInfo<BookLogDO> pageInfo = result.getData();
        List<BookLogDO> bookLogDOList = pageInfo.getList();
        List<BookLogVO> bookLogVOList = new ArrayList<>();
        for (BookLogDO bookLogDO : bookLogDOList) {
            Result<BookLogVO> getBookLogResult = getBookLog(bookLogDO.getId());
            if (!getBookLogResult.isSuccess()) {
                return Result.fail(getBookLogResult);
            }
            bookLogVOList.add(getBookLogResult.getData());
        }
        PageInfo<BookLogVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(bookLogVOList);
        return Result.success(pageInfo0);
    }

    /**
     * 更新借书信息
     * @param  tokenType TokenType
     * @param bookLogDO 要更新的借书信息
     * @return 更新后的借书信息
     */
    @Override
    public Result<BookLogVO> updateBookLog(TokenType tokenType, BookLogDO bookLogDO) {
        Result<BookLogDO> updateBookLogResult = bookLogService.updateBookLog(tokenType, bookLogDO);
        if (!updateBookLogResult.isSuccess()) {
            return Result.fail(updateBookLogResult);
        }
        return getBookLog(bookLogDO.getId());
    }


}
