package com.xiaohuashifu.tm.controller.v1.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;

@Controller
@RequestMapping(value = "v1/admin", method = RequestMethod.GET)
public class AdminController {
	
	@RequestMapping("login")
	public String login() {
		return "admin/login";
	}
	
	@RequestMapping("index")
	@TokenAuth(tokenType = {TokenType.ADMIN})
	@AdminLog("登录")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("admin/index");
		model.addObject("token", request.getSession().getAttribute("token"));
		request.getSession().removeAttribute("token");
		return model;
	}
	
}
