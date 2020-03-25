package com.xiaohuashifu.tm.manager;

import com.xiaohuashifu.tm.manager.constant.WeChatMp;
import com.xiaohuashifu.tm.pojo.dto.AccessTokenDTO;
import com.xiaohuashifu.tm.pojo.dto.MessageTemplateDTO;

import java.util.Optional;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-31 1:34
 */
public interface WeChatMpManager {

    Optional<String> getAccessToken();

    Optional<AccessTokenDTO> getNewAccessToken();

    @Deprecated
    String getOpenid(String code, String mpName);

    String getOpenid(String code, WeChatMp weChatMp);

    boolean sendTemplateMessage(MessageTemplateDTO messageTemplate);
}
