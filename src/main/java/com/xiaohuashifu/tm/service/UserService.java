package com.xiaohuashifu.tm.service;

import com.xiaohuashifu.tm.pojo.ao.UserAO;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述: 用户Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-9
 */
public interface UserService {

    Result<String> getOpenid(Integer userFormId);

    Result<UserAO> getUserByCode(String code);
}
