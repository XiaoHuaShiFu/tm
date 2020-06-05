package com.xiaohuashifu.tm.manager;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;
import com.xiaohuashifu.tm.pojo.vo.AnnouncementVO;
import com.xiaohuashifu.tm.result.Result;

public interface AnnouncementManager {

	Result<PageInfo<AnnouncementVO>> listAnnouncements(AnnouncementQuery announcementQuery);
	
}
