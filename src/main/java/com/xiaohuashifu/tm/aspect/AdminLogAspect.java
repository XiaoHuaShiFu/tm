package com.xiaohuashifu.tm.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;
import com.xiaohuashifu.tm.service.TokenService;

/**
 * 管理员日志切面
 * 
 * @author TAO
 * @date 2020年4月3日 上午10:28:32
 */
@Aspect
@Component
public class AdminLogAspect {
	
	private Integer currentAdminId;
	private final AdminService adminService;
	private final TokenService tokenService;
	private static final Logger logger = LoggerFactory.getLogger(AdminLogAspect.class);
	
	@Autowired
	public AdminLogAspect(AdminService adminService, TokenService tokenService) {
		this.currentAdminId = null;
		this.adminService = adminService;
		this.tokenService = tokenService;
	}
	
	@Pointcut("@annotation(com.xiaohuashifu.tm.aspect.annotation.AdminLog) && @annotation(adminLog)")
	public void loginPoint(AdminLog adminLog) {}
	
	@Pointcut("@annotation(com.xiaohuashifu.tm.aspect.annotation.AdminLog) && within(com.xiaohuashifu.tm.service.impl.*)")
	public void servicePoint() {}
	
	@AfterReturning(value = "loginPoint(adminLog)",returning = "model")
	public void loginLog(AdminLog adminLog, ModelAndView model) {
		String token = (String) model.getModel().get("token");
        Result<TokenAO> result = tokenService.getToken(token);
        if(!result.isSuccess()) {
        	logger.error("token获取失败");
        	return;
        }
        currentAdminId = result.getData().getId();
        AdminLogDO adminLogDO = new AdminLogDO(currentAdminId, adminLog.value());
		adminService.saveAdminLog(adminLogDO);
	}
	
}
