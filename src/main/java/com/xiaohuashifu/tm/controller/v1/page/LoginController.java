package com.xiaohuashifu.tm.controller.v1.page;

import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/v1/login")
public class LoginController {

	private final TokenService tokenService;

	@Autowired
	public LoginController(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "user/login_user";
	}
	
	@ResponseBody
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public Object validate(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("account") String account, @RequestParam("password") String password) {
		Result<TokenAO> result = tokenService.createAndSaveToken(TokenType.USER, account, password);
		if (!result.isSuccess()) {
			return Result.fail(ErrorCode.INVALID_PARAMETER, "The account or the password is invalid");
		}
		return result.getData().getToken();
	}
	
	@RequestMapping(value = "index", method = RequestMethod.POST)
	@TokenAuth(tokenType = {TokenType.USER})
	public Object attend(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
		ModelAndView model = new ModelAndView("redirect:/v1/meeting/choose");
		redirectAttributes.addFlashAttribute("token", request.getHeader("authorization"));
		redirectAttributes.addFlashAttribute("userId", tokenAO.getId());
		return model;
	}
	
}
