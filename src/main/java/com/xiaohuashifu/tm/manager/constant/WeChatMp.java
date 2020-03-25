package com.xiaohuashifu.tm.manager.constant;

/**
 * 描述: 微信小程序名字常量
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-13 19:23
 */
public enum WeChatMp {

    /**
     * 团队管理小程序相关常量
     */
    TM("tm", "wx111d67f7174bded6", "221ad0268eb2b372252c2b114cbc1832");

    /**
     * 微信小程序名字
     */
    private final String name;

    /**
     * 微信小程序appid
     */
    private final String appId;

    /**
     * 微信小程序secret
     */
    private final String secret;

    WeChatMp(String name, String appId, String secret) {
        this.name = name;
        this.appId = appId;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }

}
