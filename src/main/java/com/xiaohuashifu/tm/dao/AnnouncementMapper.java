package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;

@Mapper
public interface AnnouncementMapper {
	int insertAnnouncement(AnnouncementDO announcement);
	int updateAnnouncement(AnnouncementDO announcement);
	int deleteAnnouncement(Integer id);
	AnnouncementDO getAnnouncement(AnnouncementQuery announcementQuery);
	List<AnnouncementDO> listAnnouncements();
}
