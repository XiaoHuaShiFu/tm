package com.xiaohuashifu.tm.service.impl;

import com.google.gson.Gson;
import com.xiaohuashifu.tm.constant.MeetingState;
import com.xiaohuashifu.tm.pojo.ao.MeetingQrcodeAO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.service.CacheService;
import com.xiaohuashifu.tm.service.constant.MeetingConstant;
import com.xiaohuashifu.tm.service.constant.RedisStatus;
import com.xiaohuashifu.tm.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.dao.MeetingMapper;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;

import java.util.UUID;

@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

	private final MeetingMapper meetingMapper;

	private final CacheService cacheService;

	private final Gson gson;

	@Autowired
	public MeetingServiceImpl(MeetingMapper meetingMapper, CacheService cacheService, Gson gson) {
		this.meetingMapper = meetingMapper;
		this.cacheService = cacheService;
		this.gson = gson;
	}

	/**
	 * 获取PageInfo<MeetingDO>通过查询参数
	 *
	 * @param meetingQuery 查询参数
	 * @return PageInfo<MeetingDO>
	 */
	@Override
	public Result<PageInfo> listMeetings(MeetingQuery meetingQuery) {
		PageHelper.startPage(meetingQuery);
		PageInfo meetings = new PageInfo<>(meetingMapper.listMeetings(meetingQuery));
		return Result.success(meetings);
	}

	/**
	 * 获取MeetingDO通过id
	 *
	 * @param id 会议编号
	 * @return MeetingDO
	 */
	@Override
	public Result<MeetingDO> getMeeting(Integer id) {
		MeetingDO meetingDO = meetingMapper.getMeeting(id);
		if (meetingDO == null) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The meeting id:"
					+ id + " does not exist.");
		}
		return Result.success(meetingDO);
	}

	/**
	 * 更新会议信息
	 *
	 * @param meetingDO 要更新的信息
	 * @return 更新后的会议信息
	 */
	@Override
	public Result<MeetingDO> updateMeeting(MeetingDO meetingDO) {
		// 判断该id的会议是否和userId对应
		int count = meetingMapper.countByIdAndUserId(meetingDO.getId(), meetingDO.getUserId());
		if (count < 1) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The meeting id:"
					+ meetingDO.getId() + " and userId:" + meetingDO.getUserId() + " does not exist.");
		}

		// 只给更新某些属性
		MeetingDO meetingDO0 = new MeetingDO();
		meetingDO0.setId(meetingDO.getId());
		meetingDO0.setContent(meetingDO.getContent());
		meetingDO0.setName(meetingDO.getName());
		meetingDO0.setStartTime(meetingDO.getStartTime());
		meetingDO0.setEndTime(meetingDO.getEndTime());
		meetingDO0.setPlace(meetingDO.getPlace());
		meetingDO0.setDepartment(meetingDO.getDepartment());
		meetingDO0.setState(meetingDO.getState());
		// 所有参数都为空
		if (BeanUtils.allFieldIsNull(meetingDO0)) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
					"The required parameter must be not all null.");
		}

		count = meetingMapper.updateMeeting(meetingDO0);
		if (count < 1) {
			logger.error("Update meeting failed. id: {}", meetingDO0.getId());
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Update meeting failed.");
		}

		return getMeeting(meetingDO0.getId());
	}

	/**
	 * 创建并保存Qrcode
	 * @param meetingId 会议编号
	 * @param userId 用户编号
	 * @return Result<MeetingQrcodeAO> 二维码
	 */
	@Override
	public Result<MeetingQrcodeAO> createAndSaveQrcode(Integer meetingId, Integer userId) {
		// 判断该会议是否是该用户的
		int count = meetingMapper.countByIdAndUserId(meetingId, userId);
		if (count < 1) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The meeting id:"
					+ meetingId + " and userId:" + userId + " does not exist.");
		}

		// 判断会议是否正在进行，只有正在进行的会议才允许生成二维码
		count = meetingMapper.countByIdAndState(meetingId, MeetingState.PROCESSING);
		if (count < 1) {
			return Result.fail(ErrorCode.FORBIDDEN, "The meeting id:"
					+ meetingId + " cannot check in because the state of the meeting not is PROCESSING.");
		}

		// 新建会议二维码
		MeetingQrcodeAO meetingQrcodeAO = new MeetingQrcodeAO(meetingId, createQrcode());

		// 保存会议二维码
		return saveQrcode(meetingQrcodeAO, MeetingConstant.MEETING_QRCODE_EXPIRE_TIME);
	}

	/**
	 * 保存Meeting
	 * @param meetingDO MeetingDO
	 * @return Result<MeetingDO>
	 */
	@Override
	public Result<MeetingDO> saveMeeting(MeetingDO meetingDO) {
		int count = meetingMapper.insertMeeting(meetingDO);
		//没有插入成功
		if (count < 1) {
			logger.error("Insert meeting fail.");
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert meeting fail.");
		}

		return getMeeting(meetingDO.getId());
	}

	/**
	 * 在缓存里添加qrcode，并设置过期时间
	 *
	 * @param meetingQrcodeAO MeetingQrcodeAO
	 * @param expireTime 过期时间
	 * @return Result<MeetingQrcodeAO>
	 */
	private Result<MeetingQrcodeAO> saveQrcode(MeetingQrcodeAO meetingQrcodeAO, int expireTime) {
		String key = MeetingConstant.PREFIX_OF_QRCODE_FOR_REDIS_KEY + meetingQrcodeAO.getId();
		//保存token
		String code = cacheService.set(key, gson.toJson(meetingQrcodeAO));

		//保存失败
		if (!code.equals(RedisStatus.OK.name())) {
			logger.error("Failed to save qrcode, id: {}", meetingQrcodeAO.getId());
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to save qrcode.");
		}

		//设置过期时间
		Long result = cacheService.expire(key, expireTime);
		if (result.equals(0L)) {
			cacheService.del(key);
			logger.error("Failed to set expire, id: {}", meetingQrcodeAO.getId());
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to set expire.");
		}

		return Result.success(meetingQrcodeAO);
	}

	/**
	 * 创建会议二维码
	 * @return 会议二维码
	 */
	private String createQrcode() {
		return UUID.randomUUID().toString();
	}

}
