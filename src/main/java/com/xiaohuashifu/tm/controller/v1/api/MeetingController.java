package com.xiaohuashifu.tm.controller.v1.api;

import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;

import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.MeetingManager;
import com.xiaohuashifu.tm.pojo.ao.MeetingQrcodeAO;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.group.GroupPut;
import com.xiaohuashifu.tm.pojo.vo.MeetingQrcodeVO;
import com.xiaohuashifu.tm.pojo.vo.MeetingVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.validator.annotation.Id;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.MeetingService;


/**
 * 会议模块
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-31 23:55
 */
@RestController
@RequestMapping("v1/meetings")
@Validated
public class MeetingController {
	private final MeetingService meetingService;

	private final MeetingManager meetingManager;

	private final Mapper mapper;

	@Autowired
	public MeetingController(MeetingService meetingService, MeetingManager meetingManager, Mapper mapper) {
		this.meetingService = meetingService;
		this.meetingManager = meetingManager;
		this.mapper = mapper;
	}

	/**
	 * 创建Meeting并返回Meeting
	 * @param meetingDO 会议信息
	 * @return MeetingVO
	 *
	 * @success:
	 * HttpStatus.CREATED
	 *
	 * @errors:
	 * INTERNAL_ERROR: Insert meeting fail.
	 *
	 * @bindErrors
	 * INVALID_PARAMETER_IS_NULL
	 * INVALID_PARAMETER_IS_BLANK
	 * INVALID_PARAMETER_SIZE
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@TokenAuth(tokenType = {TokenType.USER})
	@ErrorHandler
	public Object post(TokenAO tokenAO, @Validated(GroupPost.class) MeetingDO meetingDO) {
		meetingDO.setUserId(tokenAO.getId());
		Result<MeetingDO> result = meetingService.saveMeeting(meetingDO);
		return !result.isSuccess() ? result : mapper.map(result.getData(), MeetingVO.class);
	}

	// TODO: 2020/4/1 这里在不同权限下应该返回不同的数据,
	//  ADMIN返回的信息应该多过USER
	/**
	 * 获取Meeting
	 * @param id 会议编号
	 * @return MeetingVO
	 *
	 * @success:
	 * HttpStatus.OK
	 *
	 * @errors:
	 * INVALID_PARAMETER_NOT_FOUND
	 *
	 * @bindErrors
	 * INVALID_PARAMETER_VALUE_BELOW: INVALID_PARAMETER_VALUE_BELOW: The parameter of id cannot be negative.
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object get(@PathVariable @Id Integer id) {
		Result<MeetingDO> result = meetingService.getMeeting(id);
		return !result.isSuccess() ? result : mapper.map(result.getData(), MeetingVO.class);
	}

	/**
	 * 查询Meeting
	 * @param query 查询参数
	 * @return PageInfo<MeetingVO>
	 *
	 * @success:
	 * HttpStatus.OK
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object get(MeetingQuery query) {
		Result<PageInfo<MeetingVO>> result = meetingManager.listMeetings(query);
		if (!result.isSuccess()) {
			return result;
		}
		return result.getData();
	}


	/**
	 * 更新meeting并返回meetingDO
	 * @param meetingDO meeting信息
	 * @return MeetingDO
	 *
	 * @success:
	 * HttpStatus.OK
	 *
	 * @errors:
	 * INTERNAL_ERROR: Update Meeting failed.
	 * FORBIDDEN_SUB_USER
	 *
	 * @bindErrors
	 * INVALID_PARAMETER_IS_NULL
	 * INVALID_PARAMETER_IS_BLANK
	 * INVALID_PARAMETER_SIZE
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
	@ErrorHandler
	public Object put(TokenAO tokenAO, @Validated(GroupPut.class) MeetingDO meetingDO) {
		TokenType type = tokenAO.getType();
		if (type == TokenType.USER) {
			// 普通用户不能修改别人的会议（普通用户只能修改自己的会议）
			if (!tokenAO.getId().equals(meetingDO.getUserId())) {
				return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
			}
			Result<MeetingDO> result = meetingService.updateMeeting(meetingDO);
			return !result.isSuccess() ? result : mapper.map(result.getData(), MeetingVO.class);
		}
		if (type == TokenType.ADMIN) {
			Result<MeetingDO> result = meetingService.updateMeeting(meetingDO);
			return !result.isSuccess() ? result : mapper.map(result.getData(), MeetingVO.class);
		}

		// 非法权限token
		return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
	}

	/**
	 * 创建Meeting的QRCode并返回QRCode
	 * @param id 会议id
	 * @return MeetingQrcodeAO
	 *
	 * @success:
	 * HttpStatus.CREATED
	 *
	 * @errors:
	 *
	 * @bindErrors
	 * INVALID_PARAMETER_VALUE_BELOW
	 */
	@RequestMapping(value="/qrcodes", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@TokenAuth(tokenType = TokenType.USER)
	@ErrorHandler
	public Object postQrcode(TokenAO tokenAO, @Id Integer id) {
		Result<MeetingQrcodeAO> result = meetingService.createAndSaveQrcode(id, tokenAO.getId());
		return !result.isSuccess() ? result : mapper.map(result.getData(), MeetingQrcodeVO.class);
	}

}
