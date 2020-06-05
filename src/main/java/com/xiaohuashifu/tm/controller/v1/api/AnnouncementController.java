package com.xiaohuashifu.tm.controller.v1.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.AnnouncementManager;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.AnnouncementDO;
import com.xiaohuashifu.tm.pojo.query.AnnouncementQuery;
import com.xiaohuashifu.tm.pojo.vo.AnnouncementVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AnnouncementService;

@RestController
@RequestMapping("/v1/announcements")
@Validated
public class AnnouncementController {
	
	private final AnnouncementService announcementService;
	private final AnnouncementManager announcementManager;
	
	@Autowired
	public AnnouncementController(AnnouncementService announcementService, AnnouncementManager announcementManager) {
		this.announcementService = announcementService;
		this.announcementManager = announcementManager;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	@TokenAuth(tokenType = TokenType.ADMIN)
	@AdminLog(value = "'发布公告'", type = AdminLogType.INSERT)
	public Object post(TokenAO tokenAO, HttpServletRequest request, AnnouncementDO announcement) {
		announcement.setAdminId(tokenAO.getId());
		Result<AnnouncementDO> result = announcementService.insertAnnouncement(announcement);
		if (!result.isSuccess()) {
			return result;
		}
		return result.getData();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT)
	@TokenAuth(tokenType = TokenType.ADMIN)
	@AdminLog(value = "'更新公告'", type = AdminLogType.UPDATE)
	public Object put(TokenAO tokenAO, HttpServletRequest request, AnnouncementDO announcement) {
		if (!tokenAO.getId().equals(announcement.getAdminId())) {
			return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "Not allowed!");
		}
		Result<Map<Object, Object>> result = announcementService.updateAnnouncement(announcement);
		if (!result.isSuccess()) {
			return result;
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@TokenAuth(tokenType = TokenType.ADMIN)
	@AdminLog(value = "'删除公告'", type = AdminLogType.DELETE)
	public Object delete(TokenAO tokenAO, HttpServletRequest request, 
			@RequestParam("id") Integer id, @RequestParam("adminId") Integer adminId) {
		if (!tokenAO.getId().equals(adminId)) {
			return Result.fail(ErrorCode.FORBIDDEN_SUB_USER, "Not allowed!");
		}
		Result<Integer> result = announcementService.deleteAnnouncement(id);
		if (!result.isSuccess()) {
			return result;
		}
		return result;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	public Object list(@RequestParam(value = "pageNum", required = false) Integer pageNum) {
		AnnouncementQuery announcementQuery = new AnnouncementQuery();
		if (pageNum != null) {
			announcementQuery.setPageNum(pageNum);
		}
		Result<PageInfo<AnnouncementVO>> result = announcementManager.listAnnouncements(announcementQuery);
		if (!result.isSuccess()) {
			return result;
		}
		PageInfo<AnnouncementVO> announcementsInfo = result.getData();
		return announcementsInfo;
	}
	
}
