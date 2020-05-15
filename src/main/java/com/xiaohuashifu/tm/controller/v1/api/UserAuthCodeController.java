package com.xiaohuashifu.tm.controller.v1.api;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.do0.UserAuthCodeDO;
import com.xiaohuashifu.tm.pojo.query.UserAuthCodeQuery;
import com.xiaohuashifu.tm.pojo.vo.UserAuthCodeVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.UserAuthCodeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * 描述: 用户认证码模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-16 23:55
 */
@RestController
@RequestMapping("v1/users/auth_codes")
@Validated
public class UserAuthCodeController {

    private final Mapper mapper;

    private final UserAuthCodeService userAuthCodeService;

    @Autowired
    public UserAuthCodeController(Mapper mapper, UserAuthCodeService userAuthCodeService) {
        this.mapper = mapper;
        this.userAuthCodeService = userAuthCodeService;
    }

    /**
     * 创建UserAuthCode
     * @return UserAuthCodeVO
     *
     * @success:
     * HttpStatus.CREATED
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.ADMIN)
    @ErrorHandler
    public Object post() {
        Result<UserAuthCodeDO> result = userAuthCodeService.generateAndSaveUserAuthCode();
        return result.isSuccess() ? mapper.map(result.getData(), UserAuthCodeVO.class) : result;
    }

    /**
     * 查询认证码
     * @param query 查询参数
     * @return UserAuthCodeVOList
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.ADMIN)
    @ErrorHandler
    public Object get(UserAuthCodeQuery query) {
        Result<PageInfo<UserAuthCodeDO>> result = userAuthCodeService.listUserAuthCodes(query);
        if (!result.isSuccess()) {
            return result;
        }

        PageInfo<UserAuthCodeVO> pageInfo = mapper.map(result.getData(), PageInfo.class);
        pageInfo.setList(result.getData().getList().stream()
                .map(userAuthCodeDO -> mapper.map(userAuthCodeDO, UserAuthCodeVO.class))
                .collect(Collectors.toList()));
        return pageInfo;
    }

}
