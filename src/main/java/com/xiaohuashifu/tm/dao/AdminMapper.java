package com.xiaohuashifu.tm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;

@Mapper
public interface AdminMapper {
	AdminDO getAdminByJobNumber(@Param("jobNumber") String jobNumber);
	void insertAdminLog(@Param("adminLog") AdminLogDO adminLogDO);
}
