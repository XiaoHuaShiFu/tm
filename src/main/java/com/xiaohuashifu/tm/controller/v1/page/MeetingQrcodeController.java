package com.xiaohuashifu.tm.controller.v1.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/v1/meeting/qrcode")
public class MeetingQrcodeController {
	@Autowired
	public MeetingQrcodeController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	public String login(@ModelAttribute(value = "token") String token,
						@ModelAttribute(value = "meetingId") String meetingId) {
		return "user/meeting_qrcode";
	}
	
}
