package com.xiaohuashifu.tm.timer.task;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 描述: 根据借书记录，把在booked状态的书，如果借书时间已经超过一天，则改为give_up
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component
public class BookLogStateTask implements Runnable {

    private final BookLogService bookLogService;

    @Autowired
    public BookLogStateTask(BookLogService bookLogService) {
        this.bookLogService = bookLogService;
    }

    @Override
    public void run() {
        // 查看Booked状态的书籍是否需要改变成GIVE_UP
        BookLogQuery query = new BookLogQuery();
        query.setPageSize(99999);
        query.setState(BookLogState.BOOKED);
        Result<PageInfo<BookLogDO>> listBookLogsResult = bookLogService.listBookLogs(query);
        List<BookLogDO> bookLogDOList = listBookLogsResult.getData().getList();
        for (BookLogDO bookLogDO : bookLogDOList) {
            long now = new Date().getTime();
            if ((bookLogDO.getBorrowTime().getTime() + 24 * 60 * 60 * 1000)  < now) {
                BookLogDO newBookLogDO = new BookLogDO();
                newBookLogDO.setId(bookLogDO.getId());
                newBookLogDO.setUserId(bookLogDO.getUserId());
                newBookLogDO.setState(BookLogState.GIVE_UP);
                bookLogService.updateBookLog(TokenType.ADMIN, newBookLogDO);
            }
        }
    }

}
