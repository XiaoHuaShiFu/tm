package com.xiaohuashifu.tm.controller.v1.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AnnouncementService;

@RestController
@RequestMapping("/v1/announcements")
@Validated
public class AnnouncementController {
	
	private final AnnouncementService announcementService;
	
	@Autowired
	public AnnouncementController(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	public Object list(@RequestParam(value = "pageNum", required = false) Integer pageNum) {
		AnnouncementQuery announcementQuery = new AnnouncementQuery();
		if (pageNum != null) {
			announcementQuery.setPageNum(pageNum);
		}
		Result<PageInfo<AnnouncementDO>> result = announcementService.listAnnouncements(announcementQuery);
		if (!result.isSuccess()) {
			return result;
		}
		PageInfo<AnnouncementDO> announcementsInfo = result.getData();
		List<AnnouncementDO> announcements = announcementsInfo.getList();
		return announcements;
	}
	
}
