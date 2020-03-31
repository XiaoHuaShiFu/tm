package com.xiaohuashifu.tm.service.impl;

import com.xiaohuashifu.tm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaohuashifu.tm.dao.AdminMapper;
import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;
	
	@Autowired
	public AdminServiceImpl(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
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

}
