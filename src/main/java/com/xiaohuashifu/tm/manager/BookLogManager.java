package com.xiaohuashifu.tm.manager;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.vo.BookLogVO;
import com.xiaohuashifu.tm.result.Result;

public interface BookLogManager {

	Result<BookLogVO> saveBookLog(BookLogDO bookLogDO);

	Result<BookLogVO> getBookLog(Integer id);

	Result<PageInfo<BookLogVO>> listBookLogs(BookLogQuery query);

	Result<BookLogVO> updateBookLog(TokenType tokenType, BookLogDO bookLogDO);
}
