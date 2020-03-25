package com.xiaohuashifu.tm.controller.v1;

import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.do0.group.Group;
import com.xiaohuashifu.tm.pojo.do0.group.GroupPost;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import com.xiaohuashifu.tm.pojo.vo.UserVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.FileService;
import com.xiaohuashifu.tm.service.UserService;
import com.xiaohuashifu.tm.validator.annotation.Id;
import com.xiaohuashifu.tm.validator.annotation.WeChatMpCode;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述: 用户模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-16 23:55
 */
@RestController
@RequestMapping("v1/users")
@Validated
public class UserController {

    private final FileService fileService;

    private final Mapper mapper;

    private final UserService userService;

    @Autowired
    public UserController(FileService fileService, Mapper mapper, UserService userService) {
        this.fileService = fileService;
        this.mapper = mapper;
        this.userService = userService;
    }

    /**
     * 创建User并返回User
     * @param code 微信小程序的wx.login()接口返回值
     * @param userDO 用户信息
     * @return UserVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * OPERATION_CONFLICT: Request was denied due to conflict, the openid already exists.
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ErrorHandler
    public Object post(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.") @WeChatMpCode String code,
            @Validated(GroupPost.class) UserDO userDO) {
        Result<UserDO> result = userService.saveUser(userDO, code);
        return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
    }

    /**
     * 获取user
     * @param id 用户编号
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * FORBIDDEN_SUB_USER
     *
     * @bindErrors
     * INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object get(HttpServletRequest request, @PathVariable @Id Integer id) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        TokenType type = TokenType.valueOf(tokenAO.getType());

        if (type == TokenType.USER) {
            Result<UserDO> result = userService.getUser(id);
            return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
        }

        //非法权限token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

    // TODO: 2020/3/26 这里暂时不支持查询参数，只支持pageNum和pageSize，因为还不知道需要什么查询参数
    /**
     * 查询user
     * @param request HttpServletRequest
     * @param query 查询参数
     * @return UserVOList
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    // TODO: 2020/3/26 这里应该删去USER-TOKEN，但是因为ADMIN模块还未完成，
    //  无法获取到ADMIN-TOKEN，因此暂时使用USER-TOKEN进行测试
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object get(HttpServletRequest request, UserQuery query) {
        Result<List<UserDO>> result = userService.listUsers(query);
        if (!result.isSuccess()) {
            return result;
        }

        return result.getData().stream()
                .map(userDO -> mapper.map(userDO, UserVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新User并返回User
     * @param userDO User信息
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Update avatar failed.
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
    // TODO: 2020/3/26 这里应该分开两个权限，分别进行权限控制
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object put(HttpServletRequest request, @Validated(Group.class) UserDO userDO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        if (!userDO.getId().equals(tokenAO.getId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }
        Result<UserDO> result = userService.updateUser(userDO);

        return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
    }


}
