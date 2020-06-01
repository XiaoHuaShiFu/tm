package com.xiaohuashifu.tm.auth;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * 描述: 认证能力切面
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 22:22
 */
@Aspect
@Component
public class AuthableAspect {

    @DeclareParents(value = "com.xiaohuashifu.tm.controller.v1.api.* || "
    		+ "com.xiaohuashifu.tm.controller.v1.page.AdminController", defaultImpl = AuthableImpl.class)
    public Authable authable;

}
