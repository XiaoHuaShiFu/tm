package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述: 借书记录Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface BookLogService {

    Result<BookLogDO> saveBookLog(BookLogDO bookLogDO);

    Result<BookLogDO> getBookLog(Integer id);

    Result<PageInfo<BookLogDO>> listBookLogs(BookLogQuery query);

    Integer count(BookLogQuery query);

    Result<BookLogDO> updateBookLog(BookLogDO bookLogDO);

}
