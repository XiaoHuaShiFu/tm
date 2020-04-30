package com.xiaohuashifu.tm.manager.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.manager.AdminLogManager;
import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;
import com.xiaohuashifu.tm.pojo.query.AdminLogQuery;
import com.xiaohuashifu.tm.pojo.vo.AdminLogVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;

@Component("AdminLogManager")
public class AdminLogManagerImpl implements AdminLogManager {

    private final AdminService adminService;

    private final Mapper mapper;
	
    @Autowired
    public AdminLogManagerImpl(AdminService adminService, Mapper mapper) {
    	this.adminService = adminService;
    	this.mapper = mapper;
	}
    
	@Override
	public Result<PageInfo<AdminLogVO>> listAdminLogs(AdminLogQuery adminLogQuery) {
		Result<PageInfo<AdminLogDO>> result = adminService.listAdminLogs(adminLogQuery);
        PageInfo<AdminLogDO> pageInfo = result.getData();
        List<AdminLogDO> adminLogDOList = pageInfo.getList();
        List<AdminLogVO> adminLogVOList = adminLogDOList.stream()
                .map((adminLogDO)->{
                    Result<AdminDO> adminDOResult = adminService.getAdminById(adminLogDO.getAdminId());
                    AdminLogVO adminLogVO = mapper.map(adminLogDO, AdminLogVO.class);
                    adminLogVO.setAdmin(adminDOResult.getData());
                    return adminLogVO;
                }).collect(Collectors.toList());
        PageInfo<AdminLogVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(adminLogVOList);
        return Result.success(pageInfo0);
	}
	
}
