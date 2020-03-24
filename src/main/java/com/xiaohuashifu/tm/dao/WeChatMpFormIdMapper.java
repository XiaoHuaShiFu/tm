package com.xiaohuashifu.tm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WeChatMpFormIdMapper {

    /**
     * 保存formId
     * @param mpFormId 微信小程序<form></form>表单触发时获得的formId
     * @param userFormId 用户报名表的编号
     * @return 保存的数量
     */
    int saveFormId(@Param("formId") String mpFormId, @Param("fid") Integer userFormId);

    /**
     * 获取formId
     * @param userFormId 用户报名表的编号
     * @return 微信小程序<form></form>表单触发时获得的formId
     */
    String getFormId(@Param("fid") Integer userFormId);

    int deleteFormId(String formId);
}