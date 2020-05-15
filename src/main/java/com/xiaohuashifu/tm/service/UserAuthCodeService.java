package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.UserAuthCodeDO;
import com.xiaohuashifu.tm.pojo.query.UserAuthCodeQuery;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述: 用户认证码Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface UserAuthCodeService {

    Result<UserAuthCodeDO> generateAndSaveUserAuthCode();

    Result<UserAuthCodeDO> getUserAuthCode(Integer id);

    Result<UserAuthCodeDO> getUserAuthCodeByAuthCode(String authCode);

    Result<PageInfo<UserAuthCodeDO>> listUserAuthCodes(UserAuthCodeQuery query);
    
    Result<UserAuthCodeDO> updateUserAuthCode(UserAuthCodeDO userAuthCodeDO);
}
