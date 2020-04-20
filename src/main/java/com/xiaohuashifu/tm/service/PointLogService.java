package com.xiaohuashifu.tm.service;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.pojo.query.PointLogQuery;
import com.xiaohuashifu.tm.result.Result;

public interface PointLogService {
	Result<PointLogDO> savePointLog(@Param("pointLog") PointLogDO pointLog);
	Result<PageInfo<PointLogDO>> getPointLogs(PointLogQuery pointLogQuery);
	Result<PageInfo<PointLogDO>> listPointLog(PointLogQuery pointLogQuery);
}
