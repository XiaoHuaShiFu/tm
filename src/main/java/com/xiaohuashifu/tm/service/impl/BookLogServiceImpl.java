package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.constant.BookState;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.dao.BookLogMapper;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookLogService;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述:借书记录服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("bookLogService")
public class BookLogServiceImpl implements BookLogService {

    private static final Logger logger = LoggerFactory.getLogger(BookLogServiceImpl.class);

    private final BookLogMapper bookLogMapper;

    private final BookService bookService;

    @Autowired
    public BookLogServiceImpl(BookLogMapper bookLogMapper, BookService bookService) {
        this.bookLogMapper = bookLogMapper;
        this.bookService = bookService;
    }

    /**
     * 保存BookLog
     * @param bookLogDO BookLogDO
     * @return Result<BookLogDO>
     */
    @Override
    public Result<BookLogDO> saveBookLog(BookLogDO bookLogDO) {
        // 查看数据库是否有该book
        Result<BookDO> getBookResult = bookService.getBook(bookLogDO.getBookId());
        if (!getBookResult.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The book of id={1} not exists.",
                    bookLogDO.getBookId());
        }

        // 判断book的状态是否合法，必须是IDLE状态的书籍才可以借阅
        BookDO bookDO = getBookResult.getData();
        if (bookDO.getState() != BookState.IDLE) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the book state not idle.");
        }

        // 把图书的状态更新为BOOKED状态
        BookDO newBookDO = new BookDO();
        newBookDO.setId(bookDO.getId());
        newBookDO.setState(BookState.BOOKED);
        Result<BookDO> updateBookResult = bookService.updateBook(newBookDO, null);
        if (!updateBookResult.isSuccess()) {
            return Result.fail(updateBookResult);
        }

        // 第一次的时候是预定状态
        bookLogDO.setState(BookLogState.BOOKED);
        int count = bookLogMapper.insertBookLog(bookLogDO);
        //没有插入成功
        if (count < 1) {
            logger.error("Insert bookLog fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert bookLog fail.");
        }

        return getBookLog(bookLogDO.getId());
    }

    /**
     * 获取BookLogDO通过id
     *
     * @param id 借阅信息编号
     * @return BookLogDO
     */
    @Override
    public Result<BookLogDO> getBookLog(Integer id) {
        BookLogDO bookLogDO = bookLogMapper.getBookLog(id);
        if (bookLogDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(bookLogDO);
    }

    /**
     * 获取PageInfo<BookLogDO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<BookLogDO>
     */
    @Override
    public Result<PageInfo<BookLogDO>> listBookLogs(BookLogQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<BookLogDO> pageInfo = new PageInfo<>(bookLogMapper.listBookLogs(query));
        if (pageInfo.getList().size() < 1) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

    /**
     * 获取借书记录数量通过查询参数query
     *
     * @param query 查询参数
     * @return 借书记录数量
     */
    @Override
    public Integer count(BookLogQuery query) {
        return bookLogMapper.count(query);
    }

    /**
     * 更新借书信息
     * @param tokenType TokenType
     * @param bookLogDO 要更新的信息
     * @return 更新后的借书信息
     */
    @Override
    public Result<BookLogDO> updateBookLog(TokenType tokenType, BookLogDO bookLogDO) {
        // 要更新的借书信息必须存在
        Result<BookLogDO> getBookLogResult = getBookLog(bookLogDO.getId());
        if (!getBookLogResult.isSuccess()) {
            return Result.fail(getBookLogResult);
        }

        BookLogDO bookLogDO1 = getBookLogResult.getData();
        // 要更新的借书记录必须正确，即借书记录所对应userId必须正确
        if (!bookLogDO1.getUserId().equals(bookLogDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        // 如果已经处于终态（GIVE_UP和RETURNED），则不给更新
        if (bookLogDO1.getState() == BookLogState.GIVE_UP || bookLogDO1.getState() == BookLogState.RETURNED) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT, "The current bookLog state is already final.");
        }

        BookLogDO bookLogDO0 = new BookLogDO();
        // 如果要更新状态
        if (bookLogDO.getState() != null) {
            // 用户只能放弃借书
            if (tokenType == TokenType.USER) {
                if (bookLogDO.getState() != BookLogState.GIVE_UP) {
                    return Result.fail(ErrorCode.INVALID_PARAMETER,
                            "You don't permission to update this state.");
                }
            }

            // 如果当前状态是BOOKED，下一个状态只能BORROWED或GIVE_UP
            if (bookLogDO1.getState() == BookLogState.BOOKED) {
                if (bookLogDO.getState() != BookLogState.BORROWED && bookLogDO.getState() != BookLogState.GIVE_UP) {
                    return Result.fail(ErrorCode.INVALID_PARAMETER,
                            "Update failed, the next state must be one of BORROWED or GIVE_UP.");
                }
            }
            // 如果当前状态是BORROWED，下一个状态只能是RETURNED
            if (bookLogDO1.getState() == BookLogState.BORROWED) {
                if (bookLogDO.getState() != BookLogState.RETURNED) {
                    return Result.fail(ErrorCode.INVALID_PARAMETER,
                            "Update failed, the next state must be RETURNED.");
                }
            }

            // 下面是可以进行更新的情况
            BookDO newBookDO = new BookDO();
            // 如果下一个状态是BORROWED，BOOK的状态应该更新为BORROWED
            if (bookLogDO.getState() == BookLogState.BORROWED) {
                newBookDO.setState(BookState.BORROWED);
            }
            // 如果下一个状态是GIVE_UP，BOOK的状态应该更新为IDLE
            if (bookLogDO.getState() == BookLogState.GIVE_UP) {
                newBookDO.setState(BookState.IDLE);
            }
            // 如果下一个状态是RETURNED，BOOK的状态应该更新为IDLE，并且设置返回时间
            if (bookLogDO.getState() == BookLogState.RETURNED) {
                newBookDO.setState(BookState.IDLE);
                bookLogDO0.setReturnTime(new Date());
            }

            newBookDO.setId(bookLogDO1.getBookId());
            Result<BookDO> updateBookResult = bookService.updateBook(newBookDO, null);
            if (!updateBookResult.isSuccess()) {
                return Result.fail(updateBookResult);
            }
            bookLogDO0.setState(bookLogDO.getState());
        }
        // 只给更新某些属性
        else {
            bookLogDO0.setExpirationTime(bookLogDO.getExpirationTime());
        }

        // 不能所有参数都为空
        if (BeanUtils.allFieldIsNull(bookLogDO0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_NULL,"Update at least one of parameter.");
        }

        // 更新bookLog
        bookLogDO0.setId(bookLogDO.getId());
        int count = bookLogMapper.updateBookLog(bookLogDO0);
        if (count < 1) {
            logger.error("Update bookLog failed. id={1}", bookLogDO0.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update bookLog failed.");
        }

        return getBookLog(bookLogDO0.getId());
    }

}
