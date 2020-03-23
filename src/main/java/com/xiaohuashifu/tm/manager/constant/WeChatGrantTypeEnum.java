package com.xiaohuashifu.tm.manager.constant;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-30 19:58
 */
public enum WeChatGrantTypeEnum {

    /**
     * 微信wx.login()接口返回值转换成openid的grantType
     */
    AUTHORIZATION_CODE("authorization_code"),

    /**
     * 获取access_token的接口的grantType
     */
    CLIENT_CREDENTIAL("client_credential");

    private final String value;

    WeChatGrantTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
