package com.xiaohuashifu.tm.service.impl;

import com.xiaohuashifu.tm.pojo.ao.UserAO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 描述:用户服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-26
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public Result<String> getOpenid(Integer userFormId) {
        return null;
    }

    @Override
    public Result<UserAO> getUserByCode(String code) {
        return null;
    }
}
