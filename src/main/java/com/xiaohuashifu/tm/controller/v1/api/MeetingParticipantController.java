package com.xiaohuashifu.tm.controller.v1.api;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.MeetingParticipantManager;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.MeetingParticipantDO;
import com.xiaohuashifu.tm.pojo.group.GroupGet;
import com.xiaohuashifu.tm.pojo.query.MeetingParticipantQuery;
import com.xiaohuashifu.tm.pojo.vo.MeetingParticipantVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingParticipantService;
import com.xiaohuashifu.tm.validator.annotation.Id;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * 会议参与人员模块
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-31 23:55
 */
@RestController
@RequestMapping("v1/meetings/participants")
@Validated
public class MeetingParticipantController {

	private final MeetingParticipantService meetingParticipantService;

	private final MeetingParticipantManager meetingParticipantManager;

	private final Mapper mapper;

	@Autowired
	public MeetingParticipantController(MeetingParticipantService meetingParticipantService, MeetingParticipantManager meetingParticipantManager, Mapper mapper) {
		this.meetingParticipantService = meetingParticipantService;
		this.meetingParticipantManager = meetingParticipantManager;
		this.mapper = mapper;
	}

	/**
	 * 创建MeetingParticipant并返回MeetingParticipant
	 * @param meetingId 会议的id
	 * @param qrcode 会议的二维码
	 * @return MeetingParticipantVO
	 *
	 * @success:
	 * HttpStatus.CREATED
	 *
	 * @errors:
	 * INTERNAL_ERROR: Insert meeting participant fail.
	 *
	 * @bindErrors
	 * INVALID_PARAMETER_IS_BLANK
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@TokenAuth(tokenType = TokenType.USER)
	@ErrorHandler
	public Object post(
			HttpServletRequest request,
			@Id Integer meetingId,
			@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The qrcode must be not blank.") String qrcode) {
		TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
		Result<MeetingParticipantDO> result =
				meetingParticipantService.saveMeetingParticipant(meetingId, tokenAO.getId(), qrcode);
		return !result.isSuccess() ? result : mapper.map(result.getData(), MeetingParticipantVO.class);
	}

	/**
	 * 获取MeetingParticipant
	 * @param request HttpServletRequest
	 * @param query 查询参数，包含页码，页条数和会议编号
	 * @return PageInfo<MeetingParticipantVO>
	 *
	 * @success:
	 * HttpStatus.OK
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object get(HttpServletRequest request, @Validated(GroupGet.class) MeetingParticipantQuery query) {
		Result<PageInfo<MeetingParticipantVO>> result = meetingParticipantManager.listMeetingParticipants(query);
		if (!result.isSuccess()) {
			return result;
		}
		return result.getData();
	}


}
