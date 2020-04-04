package com.xiaohuashifu.tm.controller.v1.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;

@Controller
@RequestMapping("v1/admin")
public class AdminController {
	
	private final AdminService adminService;
	
	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	@TokenAuth(tokenType = {TokenType.ADMIN})
	@AdminLog(value = "登录", type = AdminLogType.LOGIN)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("admin/index");
		model.addObject("token", request.getSession().getAttribute("token"));
		request.getSession().removeAttribute("token");
		Result<String> result = adminService.getAnnouncement();
		if (result.isSuccess()) {
			model.addObject("announcement", result.getData());
		}else {
			model.setViewName("admin/error");	
			model.addObject("error_msg", "服务器获取公告时发生错误");
		}
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "announcement", method = RequestMethod.PUT)
	public String updateAnnouncement(@RequestParam("announcement") String announcement) {
		Result result = adminService.updateAnnouncement(announcement);
		if (result.isSuccess()) {
			return "ok";
		}
		return "error";
	}
	
}
