package com.xiaohuashifu.tm.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xiaohuashifu.tm.constant.AdminLogType;

/**
 * value的值需要符合spEL表达式规范
 * 
 * @author TAO
 * @date 2020年4月24日 下午1:31:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminLog {
	
	String value() default "";
	
	AdminLogType type();
	
}
