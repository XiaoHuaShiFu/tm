package com.xiaohuashifu.tm.timer.task;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookLogService;
import com.xiaohuashifu.tm.service.PointLogService;
import com.xiaohuashifu.tm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

// TODO: 2020/5/20 该模块暂时无法使用，无法查询指定的pointlog
/**
 *
 * 描述: 根据借书记录，把在Borrowed状态的书，如果当前时间已经超过expirationTime，扣除分数3
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
//@Component
public class BookLogPointLogTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(BookLogPointLogTask.class);

    private final BookLogService bookLogService;

    private final PointLogService pointLogService;

    private final UserService userService;

    /**
     * 图书逾期的积分惩罚
     */
    private static final int EXPIRATION_TIME_POINT = -3;

//    @Autowired
    public BookLogPointLogTask(BookLogService bookLogService, PointLogService pointLogService,
                               UserService userService) {
        this.bookLogService = bookLogService;
        this.pointLogService = pointLogService;
        this.userService = userService;
    }

    @Override
    public void run() {
        // 查看BORROWED状态的书籍是否需要已经到期了
        BookLogQuery query = new BookLogQuery();
        query.setPageSize(99999);
        query.setState(BookLogState.BORROWED);
        Result<PageInfo<BookLogDO>> listBookLogsResult = bookLogService.listBookLogs(query);
        if (!listBookLogsResult.isSuccess()) {
            return;
        }
        List<BookLogDO> bookLogDOList = listBookLogsResult.getData().getList();
        for (BookLogDO bookLogDO : bookLogDOList) {
            process(bookLogDO);
        }
    }

    @Transactional
    public void process(BookLogDO bookLogDO) {
        if (new Date().after(bookLogDO.getExpirationTime()) ) {
            // TODO: 2020/5/20 这里需要获取pointlog存不存在，避免重复扣分

            PointLogDO pointLogDO = new PointLogDO();
            pointLogDO.setUserId(bookLogDO.getUserId());
            pointLogDO.setPoint(EXPIRATION_TIME_POINT);
            pointLogDO.setContent("书籍逾期未还");
            Result<PointLogDO> savePointLogResult = pointLogService.savePointLog(pointLogDO);
            if (!savePointLogResult.isSuccess()) {
                logger.error("Add point failed.");
                return;
            }
            UserDO user = new UserDO();
            user.setId(bookLogDO.getUserId());
            Result<UserDO> getUserResult = userService.getUser(bookLogDO.getUserId());
            if (!getUserResult.isSuccess()) {
                logger.error("获取user失败");
                return;
            }
            user.setPoint(getUserResult.getData().getPoint() + EXPIRATION_TIME_POINT);
            Result<UserDO> updateUserResult = userService.updateUser(user);
            if (!updateUserResult.isSuccess()) {
                logger.error("更新user失败");
            }
        }
    }

}
