package com.xiaohuashifu.tm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.dao.MeetingMapper;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;

@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

	private final MeetingMapper meetingMapper;
	
	@Autowired
	public MeetingServiceImpl(MeetingMapper meetingMapper) {
		this.meetingMapper = meetingMapper;
	}
	
	@Override
	public Result<PageInfo<MeetingDO>> listMeetings(MeetingQuery meetingQuery) {
		PageHelper.startPage(meetingQuery);
		PageInfo<MeetingDO> meetings = new PageInfo<MeetingDO>((Page<MeetingDO>) meetingMapper.listMeetings());
		return Result.success(meetings);
	}

}
