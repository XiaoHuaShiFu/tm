package com.xiaohuashifu.tm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.dao.PointLogMapper;
import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.pojo.query.PointLogQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.PointLogService;

@Service("pointLogService")
public class PointLogServiceImpl implements PointLogService {

	private final PointLogMapper pointLogMapper;
	
	@Autowired
	public PointLogServiceImpl(PointLogMapper pointLogMapper) {
		this.pointLogMapper = pointLogMapper;
	}
	
	@Override
	public Result<PointLogDO> savePointLog(PointLogDO pointLog) {
		int count = pointLogMapper.insertPointLog(pointLog);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert point log failed");
		}
		return Result.success(pointLog);
	}

	@Override
	public Result<PageInfo<PointLogDO>> listPointLogs(PointLogQuery pointLogQuery) {
		PageHelper.startPage(pointLogQuery);
		List<PointLogDO> pointLogs = pointLogMapper.listPointLogs(pointLogQuery);
		if (pointLogs.size() == 0) {
			return Result.fail(ErrorCode.INVALID_OPERATION_NOT_FOUND, "Not found");
		}
		PageInfo<PointLogDO> pointLogsInfo = new PageInfo<>(pointLogs);
		return Result.success(pointLogsInfo);
	}
	
}
