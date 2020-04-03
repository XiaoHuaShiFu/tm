package com.xiaohuashifu.tm.controller.v1.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.TokenService;

@RestController
@RequestMapping("v1/login")
public class LoginController {

	private final TokenService tokenService;
	
	@Autowired
	public LoginController(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "admin", method = RequestMethod.POST)
	public String adminLogin(HttpServletRequest request, HttpServletResponse response, 
					@RequestParam("jobNumber") String jobNumber, @RequestParam("password") String password) throws Exception {
		Result<TokenAO> result = tokenService.createAndSaveToken(TokenType.ADMIN.name(), jobNumber, password);
		if(result.isSuccess()) {
			request.getSession().setAttribute("token", result.getData().getToken());
			response.sendRedirect(request.getContextPath() + "/v1/admin/index");
			return "ok";
		}
		return "error";
	}
	
}
