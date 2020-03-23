package com.xiaohuashifu.tm.service.impl;

import com.xiaohuashifu.tm.service.WeChatService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 描述: 微信开发平台相关服务
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-09-01 14:13
 */
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {

    /**
     * 微信服务认证TOKEN
     */
    private final static String TOKEN = "scauaie";

    /**
     * 检查各项参数并返回echo
     * 此接口用于微信公众平台的认证
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return echostr
     */
    public String checkAndGetEcho(String signature, String timestamp, String nonce, String echostr) {
        String[] tmpArr = {TOKEN, timestamp, nonce};
        Arrays.sort(tmpArr);
        String tmpStr = tmpArr[0] + tmpArr[1] + tmpArr[2];
        tmpStr = DigestUtils.sha1Hex(tmpStr);

        if (tmpStr.equals(signature)) {
            return echostr;
        }
        return StringUtils.EMPTY;
    }

}
