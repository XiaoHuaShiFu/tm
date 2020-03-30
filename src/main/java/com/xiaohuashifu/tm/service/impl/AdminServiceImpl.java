package com.xiaohuashifu.tm.service.impl;

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
	
	@Override
	public Result<AdminDO> getAdminByJobNumber(String jobNumber) {
		AdminDO admin = adminMapper.getAdminByJobNumber(jobNumber);
		return Result.success(admin);
	}

}
