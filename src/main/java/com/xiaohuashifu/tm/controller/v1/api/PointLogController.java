package com.xiaohuashifu.tm.controller.v1.api;

import com.xiaohuashifu.tm.result.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.pojo.query.PointLogQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.PointLogService;

@RestController
@RequestMapping("v1/points/logs")
@Validated
public class PointLogController {
	
	private final PointLogService pointLogService;
	
	@Autowired
	public PointLogController(PointLogService pointLogService) {
		this.pointLogService = pointLogService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object list(TokenAO tokenAO, PointLogQuery query) {
		// USER-TOKEN只能获取自己的积分日志
		if (tokenAO.getType() == TokenType.USER) {
			if (query.getUserId() != null && !query.getUserId().equals(tokenAO.getId())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
			}
			query.setUserId(tokenAO.getId());
		}
		Result<PageInfo<PointLogDO>> result = pointLogService.listPointLogs(query);
		return result.isSuccess() ? result.getData() : result;
	}
	
}
