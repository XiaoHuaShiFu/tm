package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;

@Mapper
public interface AdminMapper {
	AdminDO getAdminById(Integer id);
	AdminDO getAdminByJobNumber(String jobNumber);
	int insertAdminLog(@Param("adminLog") AdminLogDO adminLogDO);
	List<AdminLogDO> listAdminLogs();
}
