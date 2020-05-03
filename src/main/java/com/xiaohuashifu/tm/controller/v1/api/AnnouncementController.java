package com.xiaohuashifu.tm.controller.v1.api;

import java.util.List;
import java.util.Map;

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
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
//	@TokenAuth(tokenType = TokenType.ADMIN)
	public Object post(AnnouncementDO announcement) {
		Result<AnnouncementDO> result = announcementService.insertAnnouncement(announcement);
		if (!result.isSuccess()) {
			return result;
		}
		return result.getData();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT)
//	@TokenAuth(tokenType = TokenType.ADMIN)
	public Object put(AnnouncementDO announcement) {
		Result<Map<Object, Object>> result = announcementService.updateAnnouncement(announcement);
		if (!result.isSuccess()) {
			return result;
		}
		return result.getData().get("newValue");
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
//	@TokenAuth(tokenType = TokenType.ADMIN)
	public Object delete(@RequestParam("id") Integer id) {
		Result<Integer> result = announcementService.deleteAnnouncement(id);
		if (!result.isSuccess()) {
			return result;
		}
		return result.getData();
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
		return announcementsInfo;
	}
	
}
