package com.xiaohuashifu.tm.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xiaohuashifu.tm.constant.AdminLogType;

/**
 * value的值需要符合spEL表达式规范</br></br>
 * 
 * 记录更新日志时，返回Result包装的HashMap可以记录更新前后的值
 * HashMap的格式是：key为oldValue，值为旧值；key为newValue，值为新值</br>
 * 若返回Object，则该Object表示的是更新的内容
 * 
 * @author TAO
 * @date 2020/4/24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminLog {
	
	String value() default "";
	
	AdminLogType type();
	
}
