package com.xiaohuashifu.tm.controller.v1.page;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.MeetingState;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/v1/meeting/choose")
public class MeetingChooseController {

	private final MeetingService meetingService;

	@Autowired
	public MeetingChooseController(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Object login(@ModelAttribute(value = "userId") String userId, @ModelAttribute(value = "token") String token) {
		MeetingQuery meetingQuery = new MeetingQuery();
		meetingQuery.setState(MeetingState.PROCESSING);
		meetingQuery.setUserId(Integer.valueOf(userId));
		ModelMap modelMap = new ModelMap();
		Result<PageInfo<MeetingDO>> listMeetings = meetingService.listMeetings(meetingQuery);
		if (!listMeetings.isSuccess()) {
			modelMap.addAttribute("haveMeeting", false);
		} else {
			modelMap.addAttribute("haveMeeting", true);
			List<MeetingDO> list = listMeetings.getData().getList();
			for (MeetingDO meetingDO : list) {
				Date startTime = meetingDO.getStartTime();
				Date endTime = meetingDO.getEndTime();
				String date = String.format("%tm-%td %tH:%tM ~ %tm-%td %tH:%tM",
						startTime,startTime,startTime, startTime, endTime, endTime, endTime, endTime);
				meetingDO.setPlace(date);
			}

			modelMap.addAttribute("meetingList", list);
			modelMap.addAttribute("token", token);
		}

		return new ModelAndView("user/meeting_choose", modelMap);
	}

	@RequestMapping(value = "qrcode", method = RequestMethod.POST)
	@TokenAuth(tokenType = {TokenType.USER})
	public Object choose(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView("redirect:/v1/meeting/qrcode");
		redirectAttributes.addFlashAttribute("token", request.getHeader("authorization"));
		redirectAttributes.addFlashAttribute("meetingId", request.getParameter("meetingId"));
		return model;
	}
	
}
