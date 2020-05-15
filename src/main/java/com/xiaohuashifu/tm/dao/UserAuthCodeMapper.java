package com.xiaohuashifu.tm.dao;

import com.xiaohuashifu.tm.pojo.do0.UserAuthCodeDO;
import com.xiaohuashifu.tm.pojo.query.UserAuthCodeQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAuthCodeMapper {

    /**
     * 保存用户认证码
     * @param userAuthCodeDO 用户认证码对象
     * @return 保存的数量
     */
    int insertUserAuthCode(UserAuthCodeDO userAuthCodeDO);

    /**
     * 获取用户认证码
     * @param id 用户认证码编号
     * @return userAuthCodeDO
     */
    UserAuthCodeDO getUserAuthCode(Integer id);

    /**
     * 获取用户认证码
     * @param authCode 认证码
     * @return userAuthCodeDO
     */
    UserAuthCodeDO getUserAuthCodeByAuthCode(String authCode);

    /**
     * 获取query过滤参数后的用户认证码列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return UserAuthCodeDOList
     */
    List<UserAuthCodeDO> listUserAuthCodes(UserAuthCodeQuery query);

    /**
     * 更新用户认证码信息
     * @param userAuthCodeDO 要更新的用户认证码信息
     * @return 成功更新的条数
     */
    int updateUserAuthCode(UserAuthCodeDO userAuthCodeDO);

}