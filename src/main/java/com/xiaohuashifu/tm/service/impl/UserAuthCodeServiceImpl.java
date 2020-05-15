package com.xiaohuashifu.tm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.dao.UserAuthCodeMapper;
import com.xiaohuashifu.tm.pojo.do0.UserAuthCodeDO;
import com.xiaohuashifu.tm.pojo.query.UserAuthCodeQuery;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.UserAuthCodeService;
import com.xiaohuashifu.tm.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * 描述:用户认证码服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("userAuthCodeService")
public class UserAuthCodeServiceImpl implements UserAuthCodeService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthCodeServiceImpl.class);

    private final UserAuthCodeMapper userAuthCodeMapper;

    @Autowired
    public UserAuthCodeServiceImpl(UserAuthCodeMapper userAuthCodeMapper) {
        this.userAuthCodeMapper = userAuthCodeMapper;
    }

    /**
     * 生成并保存UserAuthCode
     * @return Result<UserAuthCodeDO>
     */
    @Override
    public Result<UserAuthCodeDO> generateAndSaveUserAuthCode() {
        UserAuthCodeDO userAuthCodeDO = new UserAuthCodeDO();
        String userAuthCode = generateUserAuthCode();
        userAuthCodeDO.setAuthCode(userAuthCode);
        int count = userAuthCodeMapper.insertUserAuthCode(userAuthCodeDO);
        //没有插入成功
        if (count < 1) {
            logger.error("Insert userAuthCode fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert userAuthCode fail.");
        }

        return getUserAuthCode(userAuthCodeDO.getId());
    }

    /**
     * 获取UserAuthCodeDO通过id
     *
     * @param id 用户认证码编号
     * @return UserAuthCodeDO
     */
    @Override
    public Result<UserAuthCodeDO> getUserAuthCode(Integer id) {
        UserAuthCodeDO userAuthCodeDO = userAuthCodeMapper.getUserAuthCode(id);
        if (userAuthCodeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(userAuthCodeDO);
    }

    /**
     * 获取UserAuthCodeDO通过id
     *
     * @param authCode 认证码
     * @return UserAuthCodeDO
     */
    @Override
    public Result<UserAuthCodeDO> getUserAuthCodeByAuthCode(String authCode) {
        UserAuthCodeDO userAuthCodeDO = userAuthCodeMapper.getUserAuthCodeByAuthCode(authCode);
        if (userAuthCodeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(userAuthCodeDO);
    }

    /**
     * 获取UserAuthCodeDOList通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<UserAuthCodeDO>
     */
    @Override
    public Result<PageInfo<UserAuthCodeDO>> listUserAuthCodes(UserAuthCodeQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserAuthCodeDO> pageInfo = new PageInfo<>(userAuthCodeMapper.listUserAuthCodes(query));
        if (pageInfo.getList().size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

    /**
     * 更新用户认证码信息
     *
     * @param userAuthCodeDO 要更新的信息
     * @return 更新后的用户认证码信息
     */
    @Override
    public Result<UserAuthCodeDO> updateUserAuthCode(UserAuthCodeDO userAuthCodeDO) {
        // 查看该认证码存不存在
        Result<UserAuthCodeDO> getUserAuthCodeResult = getUserAuthCode(userAuthCodeDO.getId());
        if (!getUserAuthCodeResult.isSuccess()) {
            return getUserAuthCodeResult;
        }

        // 只给更新某些属性
        UserAuthCodeDO userAuthCodeDO0 = new UserAuthCodeDO();
        userAuthCodeDO0.setUsed(userAuthCodeDO.getUsed());
        // 检查是否所有参数都为空
        if (BeanUtils.allFieldIsNull(userAuthCodeDO0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
                    "The required parameter must be not all null.");
        }

        // 更新数据库
        userAuthCodeDO0.setId(userAuthCodeDO.getId());
        int count = userAuthCodeMapper.updateUserAuthCode(userAuthCodeDO0);
        if (count < 1) {
            logger.error("Update userAuthCode failed. id: {}", userAuthCodeDO0.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update userAuthCode failed.");
        }

        return getUserAuthCode(userAuthCodeDO.getId());
    }


    /**
     * 生成UserAuthCode
     * @return UserAuthCode
     */
    private String generateUserAuthCode() {
        return UUID.randomUUID().toString();
    }

}
