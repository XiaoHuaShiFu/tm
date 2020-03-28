package com.xiaohuashifu.tm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaohuashifu.tm.pojo.do0.MeetingDO;

@Mapper
public interface MeetingMapper {
	List<MeetingDO> listMeetings();
}
