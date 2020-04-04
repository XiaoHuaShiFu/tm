package com.xiaohuashifu.tm.controller.v1.api;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.AttendanceManager;
import com.xiaohuashifu.tm.pojo.ao.AttendanceQrcodeAO;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.AttendanceDO;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.group.GroupPut;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import com.xiaohuashifu.tm.pojo.vo.AttendanceQrcodeVO;
import com.xiaohuashifu.tm.pojo.vo.AttendanceVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AttendanceService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 描述: 出勤模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-16 23:55
 */
@RestController
@RequestMapping("v1/attendances")
@Validated
public class AttendanceController {

    private final Mapper mapper;

    private final AttendanceService attendanceService;

    private final AttendanceManager attendanceManager;

    @Autowired
    public AttendanceController(Mapper mapper, AttendanceService attendanceService,
                                AttendanceManager attendanceManager) {
        this.mapper = mapper;
        this.attendanceService = attendanceService;
        this.attendanceManager = attendanceManager;
    }

    /**
     * 创建Attendance并返回Attendance
     * @param qrcode 签到所用的动态二维码
     * @param attendanceDO 签到信息
     * @return AttendanceVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.USER)
    @ErrorHandler
    public Object post(TokenAO tokenAO,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The qrcode must be not blank.") String qrcode,
            @Validated(GroupPost.class) AttendanceDO attendanceDO) {
        // 只能给自己创建签到记录
        if (!tokenAO.getId().equals(attendanceDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }
        Result<AttendanceDO> result = attendanceService.saveAttendance(attendanceDO, qrcode);
        return !result.isSuccess() ? result : mapper.map(result.getData(), AttendanceVO.class);
    }

    /**
     * 查询attendance
     * @param query 查询参数
     * @return AttendanceVOList
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.ADMIN})
    @ErrorHandler
    public Object get(AttendanceQuery query) {
        Result<PageInfo<AttendanceVO>> result = attendanceManager.listAttendances(query);
        return result.isSuccess() ? result.getData() : result;
    }

    /**
     * 更新Attendance并返回Attendance
     * @param attendanceDO attendance信息
     * @return AttendanceVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Update attendance failed.
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object put(TokenAO tokenAO,  @Validated(GroupPut.class) AttendanceDO attendanceDO) {
        // TODO: 2020/4/3 这里用户部分有一些细节没有处理->应该只能更新签退时间，且在签退记录不存在时才能更新
        // TODO: 2020/4/3 这里应该只有USET-TOKEN才需要带上userid，ADMIN-TOKEN应该可以更新所有的出勤记录
        // 如果Token类型是USER，用户只能更新自己的签到信息，且只能更新签退时间（且必须是第一次更新时才有效）
        Result<AttendanceDO> result = null;
        if (tokenAO.getType() == TokenType.USER) {
            if (!attendanceDO.getUserId().equals(tokenAO.getId())) {
                return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
            }
            result = attendanceService.updateAttendance(attendanceDO);
        } else if (tokenAO.getType() == TokenType.ADMIN){
            result = attendanceService.updateAttendance(attendanceDO);
        }

        return !result.isSuccess() ? result : mapper.map(result.getData(), AttendanceVO.class);
    }


    // TODO: 2020/4/3 此接口较为简陋（窄），没有考虑权限认证问题，也没有考虑扩展性问题
    /**
     * 创建出勤（签到）的动态QRCode并返回QRCode
     * @return AttendanceQrcodeAO
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
    @TokenAuth(tokenType = TokenType.QRCODE)
    @ErrorHandler
    public Object postQrcode() {
        Result<AttendanceQrcodeAO> result = attendanceService.createAndSaveQrcode();
        return !result.isSuccess() ? result : mapper.map(result.getData(), AttendanceQrcodeVO.class);
    }

}
