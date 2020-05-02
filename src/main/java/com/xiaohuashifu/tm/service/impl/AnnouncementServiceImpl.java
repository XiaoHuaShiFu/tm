package com.xiaohuashifu.tm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.dao.AnnouncementMapper;
import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AnnouncementService;

@Service("announcementService")
public class AnnouncementServiceImpl implements AnnouncementService {
	
	private final AnnouncementMapper announcementMapper;
	
	@Autowired
	public AnnouncementServiceImpl(AnnouncementMapper announcementMapper) {
		this.announcementMapper = announcementMapper;
	}
	
	@Override
	@AdminLog(value = "'更新公告'", type = AdminLogType.UPDATE)
	public Result<Map<String, String>> updateAnnouncement(AnnouncementDO announcement) {
		Map<String, String> map = new HashMap<String, String>();
		AnnouncementDO oldAnnouncement = announcementMapper.getAnnouncement();
		map.put("oldValue", oldAnnouncement.toString());
		int count = announcementMapper.updateAnnouncement(announcement);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update announcement failed.");
		}
		map.put("newValue", announcement.toString());
		return Result.success(map);
	}

	@Override
	public Result<AnnouncementDO> getAnnouncement() {
		AnnouncementDO announcement = announcementMapper.getAnnouncement();
		if (announcement == null) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Get announcement fail");
		}
		return Result.success(announcement);
	}

	@Override
	public Result<PageInfo<AnnouncementDO>> listAnnouncements(AnnouncementQuery announcementQuery) {
		PageHelper.startPage(announcementQuery);
		PageInfo<AnnouncementDO> pageInfo = new PageInfo<>(announcementMapper.listAnnouncements());
		if (pageInfo.getList().size() == 0) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
		}
		return Result.success(pageInfo);
	}
}
