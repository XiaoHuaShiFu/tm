package com.xiaohuashifu.tm.controller.v1.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohuashifu.tm.pojo.do0.AdminDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;

@Controller
@RequestMapping("v1/login")
public class LoginController {

	private final AdminService adminService;
	
	@Autowired
	public LoginController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@RequestMapping("")
	public String getLoginPage() {
		return "admin/login";
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, 
					@RequestParam("jobNumber") String jobNumber, @RequestParam("password") String password) throws Exception {
		Result<AdminDO> result = adminService.getAdminByJobNumber(jobNumber);
		if(result.isSuccess()) {
			AdminDO admin = result.getData();
			if(admin.getPassword().equals(password)) {
				request.getSession().setAttribute("admin", admin);
				response.sendRedirect(request.getContextPath() + "/v1/books");
				return "ok";
			}
		}
		return "error";
	}
	
}
