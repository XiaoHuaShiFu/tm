package com.xiaohuashifu.tm.dao;

import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 保存用户
     * @param user 用户对象
     * @return 保存的数量
     */
    int saveUser(UserDO user);

    /**
     * 获取用户
     * @param id 用户编号
     * @return userDO
     */
    UserDO getUser(Integer id);

    /**
     * 通过openid获取对应用户
     * @param openid openid
     * @return UserDO
     */
    UserDO getUserByOpenid(String openid);

    /**
     * 获取openid对应用户的数量，用于判断此openid的用户是否已经存在数据库力
     * @param openid openid
     * @return openid对应用户的数量
     */
    int getCountByOpenid(String openid);

    /**
     * 获取query过滤参数后的用户列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return UserDOList
     */
    List<UserDO> listUsers(UserQuery query);

    /**
     * 更新用户信息
     * @param userDO0 要更新的用户信息
     * @return 成功更新的条数
     */
    int updateUser(UserDO userDO0);
}