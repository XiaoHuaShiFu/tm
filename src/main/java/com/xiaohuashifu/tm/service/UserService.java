package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.Department;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import com.xiaohuashifu.tm.result.Result;
import org.springframework.web.multipart.MultipartFile;

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

    Result<UserDO> getUserByJobNumber(String jobNumber);
    
    Result<UserDO> saveUser(UserDO userDO, String code);

    Result<UserDO> getUser(Integer id);

    Result<PageInfo<UserDO>> listUsers(UserQuery query);
    
    Result<UserDO> updateUser(UserDO userDO);

    Result<UserDO> updateAvatar(Integer id, MultipartFile avatar);
}
