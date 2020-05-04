package com.xiaohuashifu.tm.controller.v1.page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dozer.Mapper;
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
	private final Mapper mapper;
	
	@Autowired
	public AttendController(TokenService tokenService, Mapper mapper) {
		this.tokenService = tokenService;
		this.mapper = mapper;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public Object validate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("account") String account, @RequestParam("password") String password) {
		Result<TokenAO> result = tokenService.createAndSaveToken(TokenType.QRCODE, account, password);
		if (result.isSuccess()) {
			request.getSession().setAttribute("token", result.getData().getToken());
			try {
				response.sendRedirect(request.getScheme() + "://" +
						request.getServerName() + ":"+
						request.getServerPort() + "/v1/attend/index");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Result.fail(ErrorCode.INVALID_PARAMETER, "The account or the password is invalid");
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	@TokenAuth(tokenType = {TokenType.QRCODE})
	public ModelAndView attend(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("user/attend");
		model.addObject("token", request.getSession().getAttribute("token"));
		request.getSession().removeAttribute("token");
		return model;
	}
	
}
