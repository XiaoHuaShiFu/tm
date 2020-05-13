package com.xiaohuashifu.tm.controller.v1.api;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.auth.TokenAuth;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.group.Group;
import com.xiaohuashifu.tm.pojo.group.GroupPost;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import com.xiaohuashifu.tm.pojo.vo.UserVO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.UserService;
import com.xiaohuashifu.tm.validator.annotation.Id;
import com.xiaohuashifu.tm.validator.annotation.WeChatMpCode;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    private final Mapper mapper;

    private final EvaluationContext evaluationContext;
    
    private final UserService userService;

    @Autowired
    public UserController(Mapper mapper, EvaluationContext evaluationContext, UserService userService) {
        this.mapper = mapper;
        this.evaluationContext = evaluationContext;
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

    // TODO: 2020/3/31 这里在不同权限下应该返回不同的数据,
    //  ADMIN返回的信息应该多过USER
    /**
     * 获取user
     * @param tokenAO TokenAO
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
    public Object get(TokenAO tokenAO, @PathVariable @Id Integer id) {
        TokenType type = tokenAO.getType();

        if (type == TokenType.USER) {
            Result<UserDO> result = userService.getUser(id);
            return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
        }
        if (type == TokenType.ADMIN) {
            Result<UserDO> result = userService.getUser(id);
            return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
        }

        //非法权限token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

    /**
     * 查询user
     * @param query 查询参数
     * @return UserVOList
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object get(UserQuery query) {
        Result<PageInfo<UserDO>> result = userService.listUsers(query);
        if (!result.isSuccess()) {
            return result;
        }

        PageInfo<UserVO> pageInfo = mapper.map(result.getData(), PageInfo.class);
        pageInfo.setList(result.getData().getList().stream()
                .map(userDO -> mapper.map(userDO, UserVO.class))
                .collect(Collectors.toList()));
        return pageInfo;
    }

    /**
     * 更新User并返回User
     * @param tokenAO TokenAO
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
    // TODO: 2020/3/31 ADMIN可以控制修改一些信息，USER可以修改一些信息
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object put(TokenAO tokenAO, @Validated(Group.class) UserDO userDO) {
        if (!userDO.getId().equals(tokenAO.getId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }
        Result<UserDO> result = userService.updateUser(userDO);

        return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
    }

    /**
     * 修改头像
     *
     * @param tokenAO TokenAO
     * @param avatar MultipartFile
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Update avatar exception.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/avatars", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.USER)
    @ErrorHandler
    public Object putAvatar(
            TokenAO tokenAO,
            @NotNull(message = "INVALID_PARAMETER_IS_BLANK: The id must be not blank.") @Id Integer id,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.") MultipartFile avatar) {
        if (!tokenAO.getId().equals(id)) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }
        Result<UserDO> result = userService.updateAvatar(tokenAO.getId(), avatar);

        return result.isSuccess() ? mapper.map(result.getData(), UserVO.class) : result;
    }

    /**
     * 修改头像，为了适配微信小程序没有PUT方法
     *
     * @param tokenAO TokenAO
     * @param avatar MultipartFile
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Update avatar exception.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/avatars/u", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.USER)
    @ErrorHandler
    public Object putAvatarForWeChatMP(
            TokenAO tokenAO,
            @NotNull(message = "INVALID_PARAMETER_IS_BLANK: The id must be not blank.") @Id Integer id,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.") MultipartFile avatar) {
        return putAvatar(tokenAO, id, avatar);
    }

    /**
     * 管理员封号和解封成员
     * 
     * @param id 成员id
     * @param available 将要设置的user的available的值
     */
    @RequestMapping(value = "/available", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
//    @TokenAuth(tokenType = TokenType.ADMIN)
    @AdminLog(value = "#available ? '解封' : '封号'", type = AdminLogType.UPDATE)
    public Object putAvailable(HttpServletRequest request,
    		@RequestParam("id") Integer id, @RequestParam("available") Boolean available) {
    	Result<UserDO> result = userService.getUser(id);
    	if (!result.isSuccess()) {
    		return Result.fail(ErrorCode.INTERNAL_ERROR, "Get user failed");
    	}
    	UserDO user = result.getData();
    	user.setAvailable(available);
    	Result<UserDO> udResult = userService.updateUser(user);
    	if (!udResult.isSuccess()) {
    		return Result.fail(ErrorCode.INTERNAL_ERROR, "Update user failed");
    	}
    	evaluationContext.setVariable("available", available);
    	user.setPassword(null);
    	user.setOpenid(null);
    	user.setAvatarUrl(null);
    	user.setCreateTime(null);
    	user.setUpdateTime(null);
    	return Result.success(user);
    }
}
