package com.xiaohuashifu.tm.service;

import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import com.xiaohuashifu.tm.result.Result;

import java.util.List;

/**
 * 描述: 用户Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-9
 */
public interface UserService {

    Result<String> getOpenid(Integer userFormId);

    Result<UserDO> getUserByCode(String code);

    Result<UserDO> saveUser(UserDO userDO, String code);

    Result<UserDO> getUser(Integer id);

    Result<List<UserDO>> listUsers(UserQuery query);

    Result<UserDO> updateUser(UserDO userDO);
}
