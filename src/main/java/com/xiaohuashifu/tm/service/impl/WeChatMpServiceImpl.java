package com.xiaohuashifu.tm.service.impl;

import com.xiaohuashifu.tm.dao.WeChatMpFormIdMapper;
import com.xiaohuashifu.tm.manager.WeChatMpManager;
import com.xiaohuashifu.tm.pojo.dto.MessageTemplateDTO;
import com.xiaohuashifu.tm.pojo.dto.MessageTemplateDataDTO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.UserService;
import com.xiaohuashifu.tm.service.WeChatMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 描述: 微信小程序相关服务
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-09-01 14:13
 */
@Service("weChatMpService")
public class WeChatMpServiceImpl implements WeChatMpService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatMpServiceImpl.class);

    private final WeChatMpManager weChatMpManager;

    private final UserService userService;

    private final WeChatMpFormIdMapper weChatMpFormIdMapper;

    @Autowired
    public WeChatMpServiceImpl(UserService userService, WeChatMpManager weChatMpManager, WeChatMpFormIdMapper weChatMpFormIdMapper) {
        this.userService = userService;
        this.weChatMpManager = weChatMpManager;
        this.weChatMpFormIdMapper = weChatMpFormIdMapper;
    }

    /**
     * 发送模板消息
     *
     * @param userFormId 用户报名表编号
     * @param templateId 模板id
     * @param page 消息点击之后跳转到的微信小程序页面
     * @param data 模板消息的数据
     * @param emphasisKeyword 模板需要放大的关键词，不填则默认无放大
     * @return MessageTemplateDTO
     */
    public Result sendTemplateMessage(Integer userFormId, String templateId, String page,
                                      Map<String, MessageTemplateDataDTO> data, String emphasisKeyword) {
        Result<String> getOpenidResult = userService.getOpenid(userFormId);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Openid not exists.");
        }

        //获取formId
        String formId = weChatMpFormIdMapper.getFormId(userFormId);
        if (formId == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "FormId not exists.");
        }

        MessageTemplateDTO messageTemplateDTO = new MessageTemplateDTO();
        messageTemplateDTO.setTouser(getOpenidResult.getData());
        messageTemplateDTO.setTemplate_id(templateId);
        messageTemplateDTO.setPage(page);
        messageTemplateDTO.setForm_id(formId);
        messageTemplateDTO.setData(data);
        messageTemplateDTO.setEmphasis_keyword(emphasisKeyword);

        //发送模板消息
        if (!weChatMpManager.sendTemplateMessage(messageTemplateDTO)) {
            logger.error("Send template message fail. userFormId: {}, templateId: {}, data: {}, emphasisKeyword: {}",
                    userFormId, templateId, data, emphasisKeyword);
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Send template message fail.");
        }

        //删除已经被使用过的formId
        int count = weChatMpFormIdMapper.deleteFormId(formId);
        if (count < 1) {
            logger.error("Delete formId fail. formId: {}", formId);
        }

        return Result.success();
    }

    /**
     * 保存formId
     *
     * @param mpFormId 微信小程序<form></form>表单触发时获得的formId
     * @param userFormId 用户报名表编号
     * @return 微信小程序<form></form>表单触发时获得的formId
     */
    public Result<String> saveFormId(String mpFormId, Integer userFormId) {
        int count = weChatMpFormIdMapper.saveFormId(mpFormId, userFormId);
        if (count < 1) {
            logger.error("Insert formId fail. mpFormId: {}, userFormId: {}", mpFormId, userFormId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert formId fail.");
        }
        return Result.success(mpFormId);
    }

}
