package com.xiaohuashifu.tm.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;

@Controller
@RequestMapping("v1/meetings")
public class MeetingController {
	private final MeetingService meetingService;
	
	@Autowired
	public MeetingController(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Object listMeeting(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		Result<PageInfo<MeetingDO>> result = meetingService.listMeetings(new MeetingQuery(pageNum));
		if(result.isSuccess()) {
			return result.getData();
		}
		return result.getMessage();
	}
}
