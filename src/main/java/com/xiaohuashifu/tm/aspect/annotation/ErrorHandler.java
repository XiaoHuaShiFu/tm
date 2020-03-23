package com.xiaohuashifu.tm.aspect.annotation;

import java.lang.annotation.*;

/**
 * 描述: 对返回结果的错误进行解析
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-13 22:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorHandler {
}
