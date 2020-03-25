package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaohuashifu.tm.dao.UserMapper;
import com.xiaohuashifu.tm.manager.WeChatMpManager;
import com.xiaohuashifu.tm.manager.constant.WeChatMp;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.UserService;
import com.xiaohuashifu.tm.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:用户服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-26
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;

    private final WeChatMpManager weChatMpManager;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, WeChatMpManager weChatMpManager) {
        this.userMapper = userMapper;
        this.weChatMpManager = weChatMpManager;
    }

    @Override
    public Result<String> getOpenid(Integer userId) {
        return null;
    }

    /**
     * 获取UserAO通过code
     *
     * @param code wx.login()接口的返回值
     * @return Result<UserDO>
     */
    @Override
    public Result<UserDO> getUserByCode(String code) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.TM);

        UserDO userDO = userMapper.getUserByOpenid(openid);
        //通过openid获取失败
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified openid does not exist.");
        }

        return Result.success(userDO);
    }

    /**
     * 保存User
     * @param code String
     * @param userDO UserDO
     * @return Result<UserDO>
     */
    @Override
    public Result<UserDO> saveUser(UserDO userDO, String code) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.TM);
        //获取openid失败
        if (openid == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        //openid已经在数据库里
        int count = userMapper.getCountByOpenid(openid);
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }

        userDO.setOpenid(openid);
        count = userMapper.saveUser(userDO);
        //没有插入成功
        if (count < 1) {
            logger.error("Insert user fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert user fail.");
        }

        return getUser(userDO.getId());
    }

    /**
     * 获取UserDO通过id
     *
     * @param id 用户编号
     * @return UserDO
     */
    @Override
    public Result<UserDO> getUser(Integer id) {
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(userDO);
    }

    /**
     * 获取UserDOList通过查询参数qquery
     *
     * @param query 查询参数
     * @return UserDOList
     */
    @Override
    public Result<List<UserDO>> listUsers(UserQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserDO> userDOList = userMapper.listUsers(query);
        if (userDOList.size() < 1) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(userDOList);
    }

    /**
     * 更新用户信息
     *
     * @param userDO 要更新的信息
     * @return 更新后的用户信息
     */
    @Override
    public Result<UserDO> updateUser(UserDO userDO) {
        //只给更新某些属性
        UserDO userDO0 = new UserDO();
        userDO0.setJobNumber(userDO.getJobNumber());
        userDO0.setPassword(userDO.getPassword());
        userDO0.setNickName(userDO.getNickName());
        userDO0.setFullName(userDO.getFullName());
        userDO0.setGender(userDO.getGender());
        userDO0.setBirthday(userDO.getBirthday());
        userDO0.setPhone(userDO.getPhone());
        userDO0.setEmail(userDO.getEmail());
        userDO0.setDepartment(userDO.getDepartment());
        userDO0.setPoint(userDO.getPoint());
        userDO0.setAvailable(userDO.getAvailable());
        //所有参数都为空
        if (BeanUtils.allFieldIsNull(userDO0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
                    "The required parameter must be not all null.");
        }

        userDO0.setId(userDO.getId());
        int count = userMapper.updateUser(userDO0);
        if (count < 1) {
            logger.error("Update avatar failed. userId: {}", userDO0.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update user failed.");
        }

        return getUser(userDO0.getId());
    }


}
