package com.xiaohuashifu.tm.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.stereotype.Component;

import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.AdminLogDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;
import com.xiaohuashifu.tm.service.TokenService;

/**
 * 管理员日志切面
 * 
 * @author TAO
 * @date 2020/4/3
 */
@Aspect
@Component
public class AdminLogAspect {
	
	private final AdminService adminService;
	private final TokenService tokenService;
	private final ExpressionParser expressionParser;
	private final EvaluationContext evaluationContext;
	private static final Logger logger = LoggerFactory.getLogger(AdminLogAspect.class);
	
	@Autowired
	public AdminLogAspect(AdminService adminService, TokenService tokenService,
			ExpressionParser expressionParser, EvaluationContext evaluationContext) {
		this.adminService = adminService;
		this.tokenService = tokenService;
		this.expressionParser = expressionParser;
		this.evaluationContext = evaluationContext;
	}
	
	@Pointcut("@annotation(com.xiaohuashifu.tm.aspect.annotation.AdminLog) && @annotation(adminLog)"
			+ "&& within(com.xiaohuashifu.tm.controller.v1.page.AdminController)")
	public void loginPoint(AdminLog adminLog) {}
	
	@Pointcut("@annotation(com.xiaohuashifu.tm.aspect.annotation.AdminLog) && @annotation(adminLog)"
			+ "&& within(com.xiaohuashifu.tm.controller.v1.api.*) && args(request, ..)")
	public void controllerPoint(AdminLog adminLog, HttpServletRequest request) {}
	
	@AfterReturning(value = "loginPoint(adminLog)", returning = "object")
	public void loginLog(AdminLog adminLog, Object object) {
		// 登录失败
		if (object instanceof Result) {
			return;
		}
		String token = (String) object;
        Result<TokenAO> result = tokenService.getToken(token);
        if (!result.isSuccess()) {
        	logger.error("token获取失败");
        	return;
        }
        AdminLogDO adminLogDO = new AdminLogDO(result.getData().getId(), adminLog.value());
		adminService.saveAdminLog(adminLogDO);
	}
	
	/**
	 * type为update时，要返回一个HashMap，存放一个key为"oldValue"的旧值，和一个key为"newValue"的新值
	 * 
	 * @param adminLog 对应的注解信息
	 */
	@AfterReturning(value = "controllerPoint(adminLog, request)", returning = "object")
	public void controllerLog(AdminLog adminLog, HttpServletRequest request,
			Object object) {  //这里不能在Result加入泛型参数, 否则类型不对应而不能进入此切面
		if (object instanceof Result && !((Result) object).isSuccess()) {
			logger.error("操作失败");
			return;
		}
//		String token = request.getHeader("authorization");
//        Result<TokenAO> tokenResult = tokenService.getToken(token);
//        if (!tokenResult.isSuccess()) {
//        	logger.error("token获取失败");
//        	return;
//        }
		Object data = object;
		if (object instanceof Result) {
			data = ((Result) object).getData();
		}
		String logValue = expressionParser.parseExpression(adminLog.value()).getValue(evaluationContext, String.class);
		AdminLogDO adminLogDO = new AdminLogDO();
//		adminLogDO.setAdminId(tokenResult.getData().getId());
		adminLogDO.setAdminId(1);
		if (adminLog.type().equals(AdminLogType.INSERT)) {
			adminLogDO.setContent(logValue + ", 添加的数据 : " + data.toString());
		}else if (adminLog.type().equals(AdminLogType.UPDATE)) {
			if (data instanceof HashMap) {
				Map<String, Object> dataMap = (HashMap<String, Object>) data;
				adminLogDO.setContent(logValue + "。更新前的数据 : " + dataMap.get("oldValue").toString()
					+ ";  更新后的数据 : " + dataMap.get("newValue").toString());
			}else {
				adminLogDO.setContent(logValue + ", 更新的数据 : " + data.toString());
			}
		}else if (adminLog.type().equals(AdminLogType.DELETE)) {
			adminLogDO.setContent(logValue + ", 删除的数据id是 : " + data.toString());
		}
		adminService.saveAdminLog(adminLogDO);
	}
	
}
