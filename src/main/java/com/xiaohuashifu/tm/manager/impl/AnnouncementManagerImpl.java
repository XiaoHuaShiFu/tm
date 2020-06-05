package com.xiaohuashifu.tm.manager.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.manager.AnnouncementManager;
import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;
import com.xiaohuashifu.tm.pojo.vo.AnnouncementVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;
import com.xiaohuashifu.tm.service.AnnouncementService;

@Component("announcementManager")
public class AnnouncementManagerImpl implements AnnouncementManager {

	private final AnnouncementService announcementService;
	private final AdminService adminService;
	private final Mapper mapper;
	
	@Autowired
	public AnnouncementManagerImpl(AnnouncementService announcementService, 
			AdminService adminService, Mapper mapper) {
		this.announcementService = announcementService;
		this.adminService = adminService;
		this.mapper = mapper;
	}
	
	@Override
	public Result<PageInfo<AnnouncementVO>> listAnnouncements(AnnouncementQuery announcementQuery) {
		Result<PageInfo<AnnouncementDO>> result = announcementService.listAnnouncements(announcementQuery);
		if (!result.isSuccess()) {
			return Result.fail(result.getErrorCode(), result.getMessage());
		}
        PageInfo<AnnouncementDO> pageInfo = result.getData();
        List<AnnouncementDO> announcementDOList = pageInfo.getList();
        List<AnnouncementVO> announcementVOList = announcementDOList.stream()
                .map((announcementDO)->{
                    Result<AdminDO> adminDOResult = adminService.getAdminById(announcementDO.getAdminId());
                    AnnouncementVO announcementVO = mapper.map(announcementDO, AnnouncementVO.class);
                    announcementVO.setAdmin(adminDOResult.getData());
                    return announcementVO;
                }).collect(Collectors.toList());
        PageInfo<AnnouncementVO> pageInfo0 = mapper.map(pageInfo, PageInfo.class);
        pageInfo0.setList(announcementVOList);
        return Result.success(pageInfo0);
	}

}
