package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiaohuashifu.tm.pojo.do0.PointLogDO;

@Mapper
public interface PointLogMapper {
	void insertPointLog(@Param("pointLog") PointLogDO pointLog);
	List<PointLogDO> getPointLogByUserId(Integer id);
	List<PointLogDO> listPointLog();
}
