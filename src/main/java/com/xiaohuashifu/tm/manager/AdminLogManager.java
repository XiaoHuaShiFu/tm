package com.xiaohuashifu.tm.manager;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.query.AdminLogQuery;
import com.xiaohuashifu.tm.pojo.vo.AdminLogVO;
import com.xiaohuashifu.tm.result.Result;

public interface AdminLogManager {

	Result<PageInfo<AdminLogVO>> listAdminLogs(AdminLogQuery adminLogQuery);
	
}
