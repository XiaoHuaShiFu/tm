package com.xiaohuashifu.tm.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;
import com.xiaohuashifu.tm.result.Result;

public interface AnnouncementService {
	Result<AnnouncementDO> insertAnnouncement(AnnouncementDO announcement);
	Result<Map<Object, Object>> updateAnnouncement(AnnouncementDO announcement);
	Result<Integer> deleteAnnouncement(Integer id);
	Result<AnnouncementDO> getAnnouncement();
	Result<PageInfo<AnnouncementDO>> listAnnouncements(AnnouncementQuery announcementQuery);
}
