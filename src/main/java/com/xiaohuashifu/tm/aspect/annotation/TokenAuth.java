package com.xiaohuashifu.tm.aspect.annotation;

import com.xiaohuashifu.tm.constant.TokenType;

import java.lang.annotation.*;

/**
 * 描述: 进行token身份认证
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-13 22:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAuth {

    TokenType[] tokenType() default {};

}
