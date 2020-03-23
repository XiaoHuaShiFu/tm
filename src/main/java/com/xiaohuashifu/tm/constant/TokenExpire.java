package com.xiaohuashifu.tm.constant;

/**
 * 描述: token的有效时间
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 11:30
 */
public enum TokenExpire {

    /**
     * 正常token过期时间
     */
    NORMAL(600);

    private final int expire;

    TokenExpire(int expire) {
        this.expire = expire;
    }

    public int getExpire() {
        return expire;
    }
}
