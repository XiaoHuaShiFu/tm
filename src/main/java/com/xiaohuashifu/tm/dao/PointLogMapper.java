package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.pojo.query.PointLogQuery;

@Mapper
public interface PointLogMapper {
	int insertPointLog(@Param("pointLog") PointLogDO pointLog);
	List<PointLogDO> listPointLogs(PointLogQuery pointLogQuery);
}
