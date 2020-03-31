package com.xiaohuashifu.tm.service;

import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.result.Result;

public interface AdminService {
	Result<AdminDO> getAdminByJobNumber(String jobNumber);
}
