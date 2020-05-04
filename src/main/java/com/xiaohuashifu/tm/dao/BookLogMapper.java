package com.xiaohuashifu.tm.dao;

import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookLogMapper {

    /**
     * 保存借书记录
     * @param bookLog 借书记录
     * @return 保存的数量
     */
    int insertBookLog(BookLogDO bookLog);

    /**
     * 获取借书记录
     * @param id 借书记录编号
     * @return BookLogDO
     */
    BookLogDO getBookLog(Integer id);

    /**
     * 获取query过滤参数后的借书记录列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return BookLogList
     */
    List<BookLogDO> listBookLogs(BookLogQuery query);

    /**
     * 获取query过滤参数后的借书记录数量，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return 数量
     */
    int count(BookLogQuery query);

    /**
     * 更新借书记录
     * @param bookLog 要更新的借书记录
     * @return 成功更新的条数
     */
    int updateBookLog(BookLogDO bookLog);

}