package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.pojo.query.PointLogQuery;
import com.xiaohuashifu.tm.result.Result;

public interface PointLogService {
	Result<PointLogDO> savePointLog(PointLogDO pointLog);
	Result<PageInfo<PointLogDO>> listPointLogs(PointLogQuery pointLogQuery);
}
