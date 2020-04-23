package com.xiaohuashifu.tm.service.impl;

import com.xiaohuashifu.tm.result.ErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.dao.AdminMapper;
import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;
import com.xiaohuashifu.tm.pojo.query.AdminLogQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;
	
	@Autowired
	public AdminServiceImpl(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

	public Result<AdminDO> getAdminById(Integer id){
		AdminDO admin = adminMapper.getAdminById(id);
		if (admin == null) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The admin for id: "
					+ id + " does not exist.");
		}
		return Result.success(admin);
	}
	
	/**
	 * 获取AdminDO通过jobNumber
	 *
	 * @param jobNumber 工号
	 * @return Result<AdminDO>
	 */
	@Override
	public Result<AdminDO> getAdminByJobNumber(String jobNumber) {
		AdminDO admin = adminMapper.getAdminByJobNumber(jobNumber);
		if (admin == null) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The admin for jobNumber: "
					+ jobNumber + " does not exist.");
		}
		return Result.success(admin);
	}

	@Override
	public Result saveAdminLog(AdminLogDO adminLogDO) {
		int count = adminMapper.insertAdminLog(adminLogDO);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert admin log failed.");
		}
		return Result.success();
	}

	@Override
	public Result<PageInfo<AdminLogDO>> listAdminLogs(AdminLogQuery adminLogQuery) {
		PageHelper.startPage(adminLogQuery);
		List<AdminLogDO> adminLogs = adminMapper.listAdminLogs();
		if (adminLogs == null) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "get admin logs failed.");
		}
		PageInfo<AdminLogDO> adminLogsInfo = new PageInfo<>((Page<AdminLogDO>) adminLogs);
		return Result.success(adminLogsInfo);
	}
	
	@Override
	public Result<String> getAnnouncement() {
		String announcement = adminMapper.getAnnouncement();
		return Result.success(announcement);
	}

	@Override
	@AdminLog(value = "更新公告", type = AdminLogType.UPDATE)
	public Result<Map<String, String>> updateAnnouncement(String announcement) {
		Map<String, String> map = new HashMap<String, String>();
		String oldAnnocement = adminMapper.getAnnouncement();
		map.put("oldValue", oldAnnocement);
		int count = adminMapper.updateAnnouncement(announcement);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update announcement failed.");
		}
		map.put("newValue", announcement);
		return Result.success(map);
	}

}
