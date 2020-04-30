package com.xiaohuashifu.tm.manager.impl;

import java.util.List;
import java.util.stream.Collectors;

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

@Component("bookLogManager")
public class BookLogMannagerImpl implements BookLogManager {
	private final UserService userService;
    
    private final BookService bookService;
    
    private final Mapper mapper;
    
    @Autowired
    public BookLogMannagerImpl(UserService userService, BookService bookService, Mapper mapper) {
    	this.userService = userService;
    	this.bookService = bookService;
    	this.mapper = mapper;
	}
    
	@Override
	public Result<PageInfo<BookLogVO>> listBookLogs(BookLogQuery bookLogQuery) {
		Result<PageInfo<BookLogDO>> result = bookService.listBookLogs(bookLogQuery);
        PageInfo<BookLogDO> pageInfo = result.getData();
        List<BookLogDO> bookLogDOList = pageInfo.getList();
        List<BookLogVO> bookLogVOList = bookLogDOList.stream()
                .map((bookLogDO)->{
                    Result<UserDO> userDOResult = userService.getUser(bookLogDO.getUserId());
                    UserVO userVO = mapper.map(userDOResult.getData(), UserVO.class);
                    Result<BookDO> bookDOResult = bookService.getBookById(bookLogDO.getBookId());
                    BookVO bookVO = mapper.map(bookDOResult.getData(), BookVO.class);
                    BookLogVO bookLogVO = mapper.map(bookLogDO, BookLogVO.class);
                    bookLogVO.setUser(userVO);
                    bookLogVO.setBook(bookVO);
                    return bookLogVO;
                }).collect(Collectors.toList());
        PageInfo<BookLogVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(bookLogVOList);
        return Result.success(pageInfo0);
	}

}
