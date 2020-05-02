package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;

@Mapper
public interface AnnouncementMapper {
	int updateAnnouncement(AnnouncementDO announcement);
	
	/**
	 * 获取最新发布的公告
	 * @return AnnouncementDO对象
	 */
	AnnouncementDO getAnnouncement();
	
	List<AnnouncementDO> listAnnouncements();
}
