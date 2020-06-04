package com.xiaohuashifu.tm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	public Result<AnnouncementDO> insertAnnouncement(AnnouncementDO announcement) {
		int count = announcementMapper.insertAnnouncement(announcement);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert announcement failed.");
		}
		return Result.success(announcement);
	}
	
	@Override
	public Result<Map<Object, Object>> updateAnnouncement(AnnouncementDO announcement) {
		Map<Object, Object> map = new HashMap<>();
		map.put("oldValue", announcement);
		int count = announcementMapper.updateAnnouncement(announcement);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update announcement failed.");
		}
		map.put("newValue", announcement);
		return Result.success(map);
	}
	
	@Override
	public Result<Integer> deleteAnnouncement(Integer id) {
		int count = announcementMapper.deleteAnnouncement(id);
		if (count < 1) {
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete announcement failed.");
		}
		return Result.success(id);
	}
	
	@Override
	public Result<AnnouncementDO> getAnnouncement(Integer id) {
		AnnouncementDO announcement = announcementMapper.getAnnouncement(id);
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
