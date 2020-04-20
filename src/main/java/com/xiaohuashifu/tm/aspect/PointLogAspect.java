package com.xiaohuashifu.tm.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaohuashifu.tm.aspect.annotation.PointLog;
import com.xiaohuashifu.tm.pojo.do0.MeetingParticipantDO;
import com.xiaohuashifu.tm.pojo.do0.PointLogDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.PointLogService;

@Aspect
@Component
public class PointLogAspect {

	private final PointLogService pointLogService;
	private static final Logger logger = LoggerFactory.getLogger(PointLogAspect.class);
	
	@Autowired
	public PointLogAspect(PointLogService pointLogService) {
		this.pointLogService = pointLogService;
	}
	
	@Pointcut("@annotation(com.xiaohuashifu.tm.aspect.annotation.PointLog) && @annotation(pointLog) && within(com.xiaohuashifu.tm.service.impl.*)")
	public void point(PointLog pointLog) {}
	
	@AfterReturning(value = "point(pointLog)", returning = "result")
	public void serviceLog(PointLog pointLog, Result result) {  //这里不能在Result加入泛型参数, 否则类型不对应而不能进入此切面
		if (!result.isSuccess()) {
			logger.error("操作失败");
			return;
		}
		Object data = result.getData();
		int point = pointLog.point();
		PointLogDO pointLogDO = new PointLogDO();
		if (data instanceof MeetingParticipantDO) {  //会议签到增加积分
			MeetingParticipantDO meetingParticipantDO = (MeetingParticipantDO) data;
			pointLogDO.setContent(pointLog.value());
			pointLogDO.setPoint(point);
			pointLogDO.setUserId(meetingParticipantDO.getUserId());
		}
		pointLogService.savePointLog(pointLogDO);
	}
	
}
