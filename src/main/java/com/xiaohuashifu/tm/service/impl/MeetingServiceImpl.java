package com.xiaohuashifu.tm.service.impl;

import com.xiaohuashifu.tm.result.ErrorCode;
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

@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

	private final MeetingMapper meetingMapper;
	
	@Autowired
	public MeetingServiceImpl(MeetingMapper meetingMapper) {
		this.meetingMapper = meetingMapper;
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
					+ meetingDO.getId() + " and userId:" + meetingDO.getUserId()+ " does not exist.");
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
	 * 保存Meeting
	 * @param meetingDO MeetingDO
	 * @return Result<MeetingDO>
	 */
	@Override
	public Result<MeetingDO> saveMeeting(MeetingDO meetingDO) {
		int count = meetingMapper.saveMeeting(meetingDO);
		//没有插入成功
		if (count < 1) {
			logger.error("Insert meeting fail.");
			return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert meeting fail.");
		}

		return getMeeting(meetingDO.getId());
	}

}
