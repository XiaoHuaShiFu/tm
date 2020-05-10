package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xiaohuashifu.tm.aspect.annotation.PointLog;
import com.xiaohuashifu.tm.dao.MeetingParticipantMapper;
import com.xiaohuashifu.tm.pojo.ao.MeetingQrcodeAO;
import com.xiaohuashifu.tm.pojo.do0.MeetingParticipantDO;
import com.xiaohuashifu.tm.pojo.query.MeetingParticipantQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.CacheService;
import com.xiaohuashifu.tm.service.MeetingParticipantService;
import com.xiaohuashifu.tm.service.constant.MeetingConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("meetingParticipantService")
public class MeetingParticipantServiceImpl implements MeetingParticipantService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingParticipantServiceImpl.class);

	private final MeetingParticipantMapper meetingParticipantMapper;

	private final CacheService cacheService;

	private final Gson gson;

	@Autowired
	public MeetingParticipantServiceImpl(MeetingParticipantMapper meetingParticipantMapper, CacheService cacheService, Gson gson) {
		this.meetingParticipantMapper = meetingParticipantMapper;
		this.cacheService = cacheService;
		this.gson = gson;
	}

	/**
	 * 创建MeetingParticipant
	 * @param meetingId 会议编号
	 * @param userId 用户编号
	 * @param qrcode 会议二维码
	 * @return Result<MeetingParticipantDO> 会议参与人员DO
	 */
	@PointLog(point = 1, value = "会议签到")
	@Override
	public Result<MeetingParticipantDO> saveMeetingParticipant(Integer meetingId, Integer userId, String qrcode) {
		String key = MeetingConstant.PREFIX_OF_QRCODE_FOR_REDIS_KEY + meetingId;
		String value = cacheService.get(key);
		// 会议的二维码不存在
		if (value == null) {
			logger.error("The qrcode not exists. meetingId: {}", meetingId);
			return Result.fail(ErrorCode.INVALID_PARAMETER, "The qrcode not exists.");
		}

		MeetingQrcodeAO meetingQrcodeAO = gson.fromJson(value, MeetingQrcodeAO.class);
		// qrcode不正确或者已经过时
		if (!meetingQrcodeAO.getQrcode().equals(qrcode)) {
			logger.error("The qrcode is invalid. meetingId: {}", meetingId);
			return Result.fail(ErrorCode.INVALID_PARAMETER, "The qrcode is invalid.");
		}

		// 该用户已经参加此会议
		int count = meetingParticipantMapper.countByMeetingIdAndUserId(meetingId, userId);
		if (count >= 1) {
			logger.error("The user has been participate meeting. meetingId: {}, userId: {}", meetingId, userId);
			return Result.fail(ErrorCode.INVALID_PARAMETER, "The user has been participate meeting.");
		}

		MeetingParticipantDO meetingParticipantDO = new MeetingParticipantDO();
		meetingParticipantDO.setMeetingId(meetingId);
		meetingParticipantDO.setUserId(userId);
		meetingParticipantDO.setParticipationTime(new Date());
		count = meetingParticipantMapper.insertMeetingParticipant(meetingParticipantDO);
		// 插入会议参与信息失败
		if (count < 1) {
			logger.error("Insert meeting participant failed. meetingId: {}, userId: {}", meetingId, userId);
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert meeting participant failed.");
		}

		return Result.success(meetingParticipantDO);
	}

	/**
	 * 获取会议的参与者
	 * @param meetingParticipantQuery 包含页码，页条数和会议id
	 * @return Result<PageInfo>
	 */
	@Override
	public Result<PageInfo<MeetingParticipantDO>> listMeetingParticipants(MeetingParticipantQuery meetingParticipantQuery) {
		PageHelper.startPage(meetingParticipantQuery);
		PageInfo<MeetingParticipantDO> pageInfo =
				new PageInfo<>(meetingParticipantMapper.listMeetingParticipants(meetingParticipantQuery.getMeetingId()));
		if (pageInfo.getList().isEmpty()) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
		}
		return Result.success(pageInfo);
	}

}
