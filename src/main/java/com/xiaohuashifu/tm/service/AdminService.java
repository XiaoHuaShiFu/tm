package com.xiaohuashifu.tm.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;
import com.xiaohuashifu.tm.pojo.query.AdminLogQuery;
import com.xiaohuashifu.tm.result.Result;

public interface AdminService {
	Result<AdminDO> getAdminById(Integer id);
	Result<AdminDO> getAdminByJobNumber(String jobNumber);
	Result saveAdminLog(AdminLogDO adminLogDO);
	Result<PageInfo<AdminLogDO>> listAdminLogs(AdminLogQuery adminLogQuery);
	Result<String> getAnnouncement();
	Result<Map<String, String>> updateAnnouncement(String announcement);
}
