package com.xiaohuashifu.tm.aspect;

import com.xiaohuashifu.tm.constant.BookLogState;
import com.xiaohuashifu.tm.pojo.do0.*;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xiaohuashifu.tm.aspect.annotation.PointLog;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.PointLogService;
import com.xiaohuashifu.tm.service.UserService;

@Aspect
@Component
public class PointLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(PointLogAspect.class);
	private final PointLogService pointLogService;
	private final UserService userService;
	
	@Autowired
	public PointLogAspect(PointLogService pointLogService, UserService userService) {
		this.pointLogService = pointLogService;
		this.userService = userService;
	}
	
	@Pointcut("@annotation(com.xiaohuashifu.tm.aspect.annotation.PointLog) && @annotation(pointLog) && within(com.xiaohuashifu.tm.service.impl.*)")
	public void point(PointLog pointLog) {}
	
	@Transactional
	@AfterReturning(value = "point(pointLog)", returning = "result", argNames = "pointLog,result")
	public void serviceLog(PointLog pointLog, Result result) {  // 这里不能在Result加入泛型参数, 否则类型不对应而不能进入此切面
		if (!result.isSuccess()) {
			return;
		}
		Object data = result.getData();
		Integer point = pointLog.point();
		Integer userId = null;
		PointLogDO pointLogDO = new PointLogDO();
		if (data instanceof AttendanceDO) {  // 值班签到增加积分
			AttendanceDO attendance = (AttendanceDO) data;
			pointLogDO.setContent(pointLog.value());
			pointLogDO.setPoint(point);
			pointLogDO.setUserId((userId = attendance.getUserId()));
		} else if (data instanceof MeetingParticipantDO) {  // 会议签到增加积分
			MeetingParticipantDO meetingParticipant = (MeetingParticipantDO) data;
			pointLogDO.setContent(pointLog.value());
			pointLogDO.setPoint(point);
			pointLogDO.setUserId((userId = meetingParticipant.getUserId()));
		} else if (data instanceof BookLogDO) {  // 借还书增加积分
			BookLogDO bookLogDO = (BookLogDO) data;
			// 预定和放弃借书不加分
			if (bookLogDO.getState() == BookLogState.BOOKED || bookLogDO.getState() == BookLogState.GIVE_UP) {
				return;
			}
			if (bookLogDO.getState() == BookLogState.BORROWED) {
				pointLogDO.setContent("借用书籍");
			} else if (bookLogDO.getState() == BookLogState.RETURNED) {
				pointLogDO.setContent("归还书籍");
			}
			pointLogDO.setPoint(point);
			pointLogDO.setUserId((userId = bookLogDO.getUserId()));
		}
		pointLogService.savePointLog(pointLogDO);
		UserDO user = new UserDO();
		user.setId(userId);
		Result<UserDO> userResult = userService.getUser(userId);
		if (!userResult.isSuccess()) {
			logger.error("获取user失败");
			return;
		}
		Integer prevPoint = userResult.getData().getPoint();
		user.setPoint(prevPoint + point);
		userService.updateUser(user);
	}
	
}
