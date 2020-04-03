package com.xiaohuashifu.tm.auth;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 认证能力声明接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-04 2:55
 */
public interface Authable {

    HttpServletRequest getRequest();

}
