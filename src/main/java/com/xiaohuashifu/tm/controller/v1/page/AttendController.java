package com.xiaohuashifu.tm.controller.v1.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.TokenService;

@Controller
@RequestMapping("/v1/attend")
public class AttendController {
	
	private final TokenService tokenService;
	
	@Autowired
	public AttendController(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "user/login1";
	}
	
	@ResponseBody
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public Object validate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("account") String account, @RequestParam("password") String password) {
		Result<TokenAO> result = tokenService.createAndSaveToken(TokenType.QRCODE, account, password);
		if (!result.isSuccess()) {
			return Result.fail(ErrorCode.INVALID_PARAMETER, "The account or the password is invalid");
		}
		return result.getData().getToken();
	}
	
	@RequestMapping(value = "index", method = RequestMethod.POST)
	@TokenAuth(tokenType = {TokenType.QRCODE})
	public Object attend(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("user/attend");
		model.addObject("token", request.getHeader("authorization"));
		return model;
	}
	
}
